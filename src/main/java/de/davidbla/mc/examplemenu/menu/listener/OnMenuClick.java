package de.davidbla.mc.examplemenu.menu.listener;

import de.davidbla.mc.examplemenu.menu.MenuPoint;
import de.davidbla.mc.examplemenu.menu.event.MenuClickEvent;
import de.davidbla.mc.examplemenu.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnMenuClick implements Listener {

    @EventHandler
    public void onMenuClick(MenuClickEvent event){
        Player p = event.getPlayer();
        MenuPoint menuPoint = event.getMenuPoint();
        if(p.hasPermission(menuPoint.getPermission())){
            menuPoint.click(p);
            p.closeInventory();
            return;
        }
        ChatUtil.noPermissionMsg(p);
    }
}
