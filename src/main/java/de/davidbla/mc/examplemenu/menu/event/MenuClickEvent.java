package de.davidbla.mc.examplemenu.menu.event;

import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.menu.MenuPoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MenuClickEvent extends Event {

    private final Player player;
    private final Menu menu;
    private final MenuPoint menuPoint;
    private static final HandlerList HANDLERS = new HandlerList();

    public MenuClickEvent(Player player, Menu menu, Integer slotNr) {
        this.player = player;
        this.menu = menu;
        this.menuPoint = this.menu.getMenuPoint(slotNr);
    }

    public Player getPlayer(){
        return this.player;
    }

    public Menu getMenu(){
        return this.menu;
    }

    public MenuPoint getMenuPoint(){
        return this.menuPoint;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() { return HANDLERS; }

}
