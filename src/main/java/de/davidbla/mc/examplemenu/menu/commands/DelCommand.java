package de.davidbla.mc.examplemenu.menu.commands;

import de.davidbla.mc.examplemenu.ExampleMenu;
import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.utils.ChatUtil;
import de.davidbla.mc.examplemenu.utils.ValidatorUtil;
import org.bukkit.entity.Player;

public class DelCommand {
    public static void run(Player player, String[] args){
        // /menu <menuId> del | /menu <menuId> del <slotId>
        if(!ValidatorUtil.isInteger(args[0])){
            ChatUtil.errorMsg(player);
            return;
        }

        Integer menuId = Integer.parseInt(args[0]);
        if(!ExampleMenu.menu.containsKey(menuId)){
            ChatUtil.errorMsg(player);
            return;
        }

        if( args.length == 2 ){
            if (Menu.delete(menuId)) {
                ChatUtil.successMsg(player);
                return;
            }
            ChatUtil.errorMsg(player);
            return;
        }
        if( args.length == 3 ){
            if(ValidatorUtil.isInteger(args[2])){
                Integer slotId = Integer.parseInt(args[2]);
                if( Menu.delete(menuId,slotId) ){
                    ChatUtil.successMsg(player);
                    return;
                }
                ChatUtil.errorMsg(player);
                return;
            }
            ChatUtil.errorMsg(player);
        }
    }
}
