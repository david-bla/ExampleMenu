package de.davidbla.mc.examplemenu.menu.commands;

import de.davidbla.mc.examplemenu.ExampleMenu;
import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.menu.MenuAction;
import de.davidbla.mc.examplemenu.menu.MenuPoint;
import de.davidbla.mc.examplemenu.utils.ChatUtil;
import de.davidbla.mc.examplemenu.utils.ValidatorUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Diese Klasse beinhaltet Funktionen um Einstellungen für ein Menu direkt zu setzen.
 */
public class SetCommand {
    /**
     * Die Funktion ist dafür da den Code übersichtlicher zu gestalten.
     * Sie wird wir folgt aufgerufen : /menu <menuid> set
     *
     * @param player Der Spieler
     * @param args Die Parameter hinter dem eingegebenen Command
     */
    public static void run(Player player, String[] args){

        if(ValidatorUtil.isInteger(args[0])){
            Menu menu = ExampleMenu.menu.get(Integer.parseInt(args[0]));
            if( menu != null){

                switch(args[2]){
                    case "title":
                        StringBuilder title = new StringBuilder(args[3]);
                        int i = 4;
                        while(args.length > i){
                            title.append(" ").append(args[i]);
                            i++;
                        }
                        menu.setTitle(title.toString());
                        ChatUtil.successMsg(player);
                        return;

                    case "size":
                        if(ValidatorUtil.isInteger(args[3])) {
                            int size = Integer.parseInt(args[3]);
                            if(size % 9 != 0 || size > 54){
                                ChatUtil.errorInvSize(player);
                                return;
                            }
                            menu.setSize(size);
                            ChatUtil.successMsg(player);
                            return;
                        }
                        break;

                    case "viewPermission":
                        menu.setViewPermission(args[3]);
                        ChatUtil.successMsg(player,"Berechtigungen aktualisiert.");
                        return;

                    case "editPermission":
                        menu.setEditPermission(args[3]);
                        ChatUtil.successMsg(player,"Berechtigungen aktualisiert.");
                        return;

                    default:
                        if(ValidatorUtil.isInteger(args[2])) {
                            Integer slotNr = Integer.parseInt(args[2]);
                            MenuPoint menuPoint = menu.getMenuPoint(slotNr);
                            if(menuPoint == null) {
                                ChatUtil.errorMsg(player);
                                return;
                            }

                            switch(args[3]){
                                case "title":
                                    title = new StringBuilder(args[4]);
                                    i = 5;
                                    while(args.length > i){
                                        title.append(" ").append(args[i]);
                                        i++;
                                    }
                                    menuPoint.setTitle(title.toString());
                                    menu.update();
                                    ChatUtil.successMsg(player);
                                    break;

                                case "subtitle":
                                    if(!ValidatorUtil.isInteger(args[4])){
                                        ChatUtil.errorMsg(player);
                                        return;
                                    }
                                    StringBuilder subtitle = new StringBuilder(args[5]);
                                    i = 6;
                                    while(args.length > i){
                                        subtitle.append(" ").append(args[i]);
                                        i++;
                                    }
                                    menuPoint.setSubtitles(Integer.parseInt(args[4]),subtitle.toString());
                                    menu.update();
                                    ChatUtil.successMsg(player);
                                    break;

                                case "permission":
                                    menuPoint.setPermission(args[4]);
                                    ChatUtil.successMsg(player);
                                    menu.update();
                                    break;

                                case "action":
                                    // evtl in MenuAction verlegen?
                                    MenuAction action = MenuAction.get(args[4]);
                                    if(action != null){

                                            switch(action){

                                            case Log:
                                                menu.updateMenuPoint(menuPoint,action);
                                                return;

                                            case Teleport:
                                                menu.updateMenuPoint(menuPoint,action,player.getLocation());
                                                ChatUtil.successMsg(player);
                                                return;

                                            case Item:
                                                menu.updateMenuPoint(menuPoint,action,player.getInventory().getItemInMainHand());
                                                ChatUtil.successMsg(player);
                                                return;

                                            case SendPlayerMessage:
                                            case BroadcastMessage:
                                            case PlayerCommand:
                                            case ConsoleCommand:
                                                StringBuilder msg = new StringBuilder(args[5]);
                                                i = 6;
                                                while(args.length > i){
                                                    msg.append(" ").append(args[i]);
                                                    i++;
                                                }
                                                menu.updateMenuPoint(menuPoint,action,msg.toString());
                                                ChatUtil.successMsg(player);
                                                return;
                                            default:
                                                ChatUtil.errorMsg(player);
                                        }
                                        return;
                                    }
                                    ChatUtil.errorMsg(player);
                                    break;

                                case "material":
                                    Material material = Material.getMaterial(args[4].toUpperCase());
                                    if( material != null ){
                                        menuPoint.setMaterial(material);
                                        ChatUtil.successMsg(player);
                                        menu.update();
                                        return;
                                    }
                                    ChatUtil.errorMsg(player,"Material '" +args[4]+ "' nicht gefunden.");
                                    break;

                                case "slot":
                                    if(ValidatorUtil.isInteger(args[4])){
                                        if( menu.changePosition(slotNr,Integer.parseInt(args[4]))){
                                            ChatUtil.successMsg(player);
                                            return;
                                        }
                                        ChatUtil.errorMsg(player);
                                        return;
                                    }
                                    ChatUtil.errorMsg(player);
                                    break;
                            }
                        }
                        break;
                }
            }
        }
    }
}
