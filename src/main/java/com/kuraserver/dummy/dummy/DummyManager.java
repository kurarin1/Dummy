package com.kuraserver.dummy.dummy;

import cn.nukkit.Server;
import cn.nukkit.scheduler.NukkitRunnable;
import com.kuraserver.dummy.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DummyManager {

    private static Map<Long, Dummy> dummies;

    public static void init(){
        DummyManager.dummies = new HashMap<>();

        //new File(Main.getInstance().getDataFolder(), "dummies").mkdirs();

        new NukkitRunnable() {
            @Override
            public void run() {
                DummyManager.dummies.values().forEach(dummy -> dummy.onUpdate(Server.getInstance().getTick()));
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);

        Server.getInstance().getPluginManager().registerEvents(new DummyEventHandler(), Main.getInstance());
    }

    public static void fin(){
        //DummyManager.save();
    }

    public static void registerDummy(Dummy dummy){
        DummyManager.dummies.put(dummy.getEid(), dummy);
        dummy.spawn();
    }

    public static Map<Long, Dummy> getDummies() {
        return dummies;
    }

    /*public static void save(){
        for(Dummy dummy : DummyManager.dummies.values()){

        }
    }*/
}
