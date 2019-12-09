package de.davidbla.mc.examplemenu.menu.commands;

import de.davidbla.mc.examplemenu.ExampleMenu;
import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.menu.MenuAction;
import de.davidbla.mc.examplemenu.menu.MenuPoint;
import de.davidbla.mc.examplemenu.utils.ValidatorUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0) {
                ExampleMenu.menu.get(0).open((Player) sender);
                return true;
            }else{
                if(args[0].equalsIgnoreCase("set")){
                    if(ValidatorUtil.isInteger(args[1])){
                        Menu menu = new Menu(Integer.valueOf(args[1]));
                        if( menu != null){

                            switch(args[2]){
                                case "title":
                                    menu.setTitle(args[3]);
                                    return true;
                                case "size":
                                    if(ValidatorUtil.isInteger(args[3])) {
                                        int size = Integer.parseInt(args[3]);
                                        if(size % 9 != 0) return false;
                                        menu.setSize(size);
                                        return true;
                                    }
                                    break;
                                case "permission":
                                    menu.setPermission(args[3]);
                                    return true;
                                case "point":
                                    if(ValidatorUtil.isInteger(args[3])) {
                                        Integer slotNr = Integer.valueOf(args[3]);
                                        MenuPoint menuPoint = menu.getMenuPoint(slotNr);
                                        if(menuPoint == null) return false;

                                        switch(args[4]){
                                            case "title":
                                                menuPoint.setTitle(args[5]);
                                                break;
                                            case "subtitle":
                                                //menuPoint.setSubtitles();
                                                break;
                                            case "viewPermission":
                                                menuPoint.setViewPermission(args[5]);
                                                break;
                                            case "usePermission":
                                                menuPoint.setUsePermission(args[5]);
                                                break;
                                            case "action":
                                                MenuAction action = MenuAction.get(args[5]);
                                                if(action != null){
                                                    menuPoint.setAction(action);
                                                    switch(action){
                                                        case Teleport:
                                                            menu.updateMenuPoint(menuPoint,action,player.getLocation());
                                                            return true;
                                                        case Item:
                                                            menu.updateMenuPoint(menuPoint,action,player.getInventory().getItemInMainHand());
                                                            return true;
                                                        case SendPlayerMessage:
                                                        case BroadcastMessage:
                                                        case PlayerCommand:
                                                        case ConsoleCommand:
                                                            menu.updateMenuPoint(menuPoint,action,args[6]);
                                                            return true;
                                                    }
                                                    return false;
                                                }
                                                break;
                                            case "material":
                                                Material material = Material.getMaterial(args[5]);
                                                if( material != null ){
                                                    menuPoint.setMaterial(material);
                                                    return true;
                                                }
                                                break;
                                            case "slot":
                                                if(ValidatorUtil.isInteger(args[5]) && ValidatorUtil.isInteger(args[6])){
                                                    return menu.changePosition(Integer.parseInt(args[5]),Integer.parseInt(args[6]));
                                                }

                                                break;
                                        }

                                    }
                                    break;
                            }

                        }
                    }
                }
            }
        }
        return false;
    }
}
