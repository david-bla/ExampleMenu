package de.davidbla.mc.examplemenu.menu.commands;

import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.utils.ChatUtil;
import org.bukkit.entity.Player;

/**
 * Diese Klasse erstellt neue Menus
 */
public class NewCommand {
    public static void run(Player player, String[] args){
        Menu newMenu = new Menu();
        ChatUtil.menuCreated(player,newMenu.getId());
    }
}
