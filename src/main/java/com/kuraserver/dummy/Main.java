package com.kuraserver.dummy;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import com.kuraserver.dummy.command.DummyCommand;
import com.kuraserver.dummy.dummy.DummyManager;

public class Main extends PluginBase {

    private static Main instance;

    public void onEnable(){
        Main.instance = this;

        DummyManager.init();

        //Server.getInstance().getCommandMap().register("dummy", new DummyCommand());

        Server.getInstance().getPluginManager().registerEvents(new MainEventHandler(), this);
    }

    public void onDisable(){
        DummyManager.fin();
    }

    public static Main getInstance() {
        return instance;
    }
}
