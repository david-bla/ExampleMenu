package de.davidbla.mc.examplemenu.menu.listener;

import de.davidbla.mc.examplemenu.menu.MenuPoint;
import de.davidbla.mc.examplemenu.menu.event.MenuClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnMenuClick implements Listener {

    @EventHandler
    public void onMenuClick(MenuClickEvent event){
        Player p = event.getPlayer();
        MenuPoint menuPoint = event.getMenuPoint();
        menuPoint.click(p);
    }
}
