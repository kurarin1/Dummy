package com.kuraserver.dummy.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.DefaultPermissions;
import cn.nukkit.permission.Permission;
import com.kuraserver.dummy.dummy.Dummy;
import com.kuraserver.dummy.dummy.DummyManager;

import java.util.UUID;

public class DummyCommand extends Command {

    public DummyCommand() {
        super("dummy", "ダミーコマンド", "/dummy");

        Permission permission = new Permission("com.kuraserver.dummy.command", "", Permission.DEFAULT_OP);
        DefaultPermissions.registerPermission(permission);
        this.setPermission(permission.getName());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!this.testPermission(sender)){
            return false;
        }

        if(!(sender instanceof Player)){
            sender.sendMessage("ゲーム内で実行してください");
            return false;
        }

        if(args.length == 0){
            sender.sendMessage("/dummy <create>");
            return false;
        };

        switch(args[0]){

            case "create":
                DummyManager.registerDummy( new Dummy(UUID.randomUUID(), (Player) sender, ((Player) sender).getSkin(), ((Player) sender).getInventory().getItemInHand()) );
                sender.sendMessage("ダミーを召喚しました");
                break;

        }

        return true;
    }
}
