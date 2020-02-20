package com.kuraserver.dummy.dummy;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.transaction.data.UseItemOnEntityData;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.ProtocolInfo;

public class DummyEventHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        DummyManager.getDummies().forEach((key, dummy) -> dummy.spawnTo(event.getPlayer()));
    }

    @EventHandler
    public void onLevelChange(EntityLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            for (Dummy dummy : DummyManager.getDummies().values()){
                if(event.getOrigin() == dummy.getLevel()){
                    dummy.despawnFrom((Player) event.getEntity());
                }else if(event.getTarget() == dummy.getLevel()){
                    dummy.spawnTo((Player) event.getEntity());
                }
            }
        }
    }

    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent event){
        DataPacket pk = event.getPacket();
        if(pk instanceof InventoryTransactionPacket){
            if(((InventoryTransactionPacket) pk).transactionType == InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY){
                long id = ((UseItemOnEntityData) ((InventoryTransactionPacket) pk).transactionData).entityRuntimeId;
                if(DummyManager.getDummies().containsKey(id)){
                    DummyManager.getDummies().get(id).onTouch(event.getPlayer());
                }
            }
        }
    }

}
