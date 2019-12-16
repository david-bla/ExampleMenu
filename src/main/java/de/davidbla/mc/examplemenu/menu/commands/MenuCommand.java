package de.davidbla.mc.examplemenu.menu.commands;

import de.davidbla.mc.examplemenu.ExampleMenu;
import de.davidbla.mc.examplemenu.utils.ChatUtil;
import de.davidbla.mc.examplemenu.utils.ValidatorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 0) {
                if(ExampleMenu.menu.containsKey(0)) {
                    ExampleMenu.menu.get(0).open(player);
                    return true;
                }
                ChatUtil.errorMsg(player);
            }

            if( ValidatorUtil.isInteger( args[0] ) ){
                Integer menuId = Integer.parseInt( args[0] );

                if(args.length == 1) {
                    if (ExampleMenu.menu.containsKey(menuId)) {
                        ExampleMenu.menu.get(menuId).open(player);
                    } else {
                        ChatUtil.errorMsg(player);
                    }
                    return true;
                }else{
                    if(!player.hasPermission(ExampleMenu.menu.get(menuId).getEditPermission())){
                        ChatUtil.noPermissionMsg(player);
                        return true;
                    }
                    switch(args[1].toLowerCase()){
                        case "set":
                            SetCommand.run(player, args);
                            return true;

                        case "add":
                            AddCommand.run(player,args);
                            return true;

                        case "del":
                            DelCommand.run(player,args);
                            return true;
                    }
                }
            }
            if( args[0].equalsIgnoreCase("help" )) {
                if(args.length > 1){
                    help(player,args[1]);
                }else{
                    help(player);
                }
                return true;
            }

            if( args[0].equalsIgnoreCase("new" )){
                if(!player.hasPermission("menu.new")){
                    ChatUtil.noPermissionMsg(player);
                    return true;
                }
                NewCommand.run(player,args);
                return true;
            }

            ChatUtil.errorMsg(player);
            return true;
        }
        return false;
    }
    public void help(Player player){
        List<String> help = new ArrayList<>();
        help.add("&6[ &9ExampleMenu &6] &r Hilfe");
        help.add("&b/menu help [new|set|add|del]' für Details.");
        help.add("&1'/menu <menuId>' &7zum Öffnen eines Menüs");
        help.add("&1'/menu new' &7zum Erstellen eines neuen Menüs");
        help.add("&1'/menu <menuId> set <title|size|viewPermission|editPermission> <value>' &7ändert Menüeinstellungen.");
        help.add("&1'/menu <menuId> set point <slotId> <title|subtitle|material|viewPermission|usePermission|action|material|slot> <value>' &7ändert Menüpunkteinstellungen.");
        help.add("&1'/menu <menuId> add <slotId>' &7erstellt einen neuen Menüpunkt.");
        help.add("&1'/menu <menuId> del [<slotId>]' &7löscht ein Menü oder einen Menüpunkt.");
        ChatUtil.playerMsg(player,help);
    }
    public void help(Player player, String command){
        switch(command){
            case "set":
                ChatUtil.playerMsg(player,"kommt noch...");
                break;
            case "new":
                ChatUtil.playerMsg(player,"kommt noch...");
                break;
            case "add":
                ChatUtil.playerMsg(player,"kommt noch...");
                break;
            case "del":
                ChatUtil.playerMsg(player,"kommt noch...");
                break;
            default:
                help(player);
        }
    }
}
