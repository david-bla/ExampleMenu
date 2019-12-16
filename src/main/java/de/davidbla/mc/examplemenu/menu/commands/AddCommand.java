package de.davidbla.mc.examplemenu.menu.commands;

import de.davidbla.mc.examplemenu.ExampleMenu;
import de.davidbla.mc.examplemenu.menu.MenuAction;
import de.davidbla.mc.examplemenu.menu.MenuPoint;
import de.davidbla.mc.examplemenu.utils.ChatUtil;
import de.davidbla.mc.examplemenu.utils.ValidatorUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Adding Items to a Menu
 */
public class AddCommand {
    public static void run(Player player, String[] args){
        // menu 0 add 0

        int menuId = Integer.parseInt(args[0]);
        if(!ValidatorUtil.isInteger(args[2])){
            ChatUtil.errorMsg(player);
            return;
        }
        int slotId = Integer.parseInt(args[2]);
        if(ExampleMenu.menu.get(menuId).isSlotFree(slotId)) {

            String title = "Neuer Menüpunkt";
            List<String> subtitles = new ArrayList<>();
            subtitles.add("Dies ist ein neuer Menüpunkt.");

            String viewPermission = "menu." + menuId + "." + slotId;
            String usePermission = "menu." + menuId + "." + slotId;
            MenuPoint menuPoint = new MenuPoint(
                    menuId,
                    slotId,
                    title,
                    subtitles,
                    usePermission,
                    MenuAction.Log,
                    Material.ANVIL);
            if (ExampleMenu.menu.get(menuId).addMenuPoint(menuPoint, slotId)) {
                ChatUtil.successMsg(player);
                return;
            }
        }
        ChatUtil.errorMsg(player, "Menüpunkt konnte nicht hinzugefügt werden.");
    }
}
