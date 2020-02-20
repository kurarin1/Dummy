package com.kuraserver.dummy.dummy;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.*;
import cn.nukkit.utils.SerializedImage;

import java.io.Serializable;
import java.util.UUID;

public class Dummy extends Location implements Serializable {

    private static final Skin EMPTY_SKIN = new Skin();

    static {
        EMPTY_SKIN.setSkinData(SerializedImage.fromLegacy(new byte[8192]));
        EMPTY_SKIN.generateSkinId("empty");
    }

    private UUID uuid;
    private long eid;
    private String name;
    private Skin skin;
    private Item item;

    public Dummy(UUID uuid, Location location, Skin skin, Item item){
        this(uuid, location, skin);
        this.item = item;
    }

    public Dummy(UUID uuid, Location location, Skin skin){
        this(uuid, location);
        this.skin = skin;
    }

    public Dummy(UUID uuid, Location location){
        super(location.x, location.y, location.z, location.yaw, location.pitch, location.level);

        this.eid = Entity.entityCount++;
        this.uuid = uuid;
        this.name = "ダミー";
        this.skin = Dummy.EMPTY_SKIN;
        this.item = Item.get(0);
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getEid() {
        return eid;
    }

    public void spawn(){
        this.level.getPlayers().forEach((key, player) -> this.spawnTo(player));
    }

    public void spawnTo(Player player){
        PlayerListPacket.Entry[] entry = {new PlayerListPacket.Entry(uuid, this.eid, this.name, this.skin)};
        PlayerListPacket playerAdd = new PlayerListPacket();
        playerAdd.entries = entry;
        playerAdd.type = PlayerListPacket.TYPE_ADD;

        player.dataPacket(playerAdd);


        AddPlayerPacket pk = new AddPlayerPacket();
        pk.uuid = this.uuid;
        pk.username = this.name;
        pk.entityUniqueId = this.eid;
        pk.entityRuntimeId = this.eid;
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.pitch = (float) this.pitch;
        pk.yaw = (float) this.yaw;
        pk.item = item;

        player.dataPacket(pk);


        PlayerListPacket playerRemove = new PlayerListPacket();
        playerRemove.entries = entry;
        playerRemove.type = PlayerListPacket.TYPE_REMOVE;

        player.dataPacket(playerRemove);
    }

    public void despawn(){
        this.level.getPlayers().forEach((key, player) -> this.despawnFrom(player));
    }

    public void despawnFrom(Player player){
        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = this.getEid();

        player.dataPacket(pk);
    }

    //タッチされたときに呼び出される
    public void onTouch(Player player){

    }

    /*public void sendSkinTo(Player player){
        PlayerSkinPacket pk = new PlayerSkinPacket();
        pk.uuid = this.uuid;
        pk.skin = this.skin;
        pk.newSkinName =
    }*/

}