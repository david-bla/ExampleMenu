package de.davidbla.mc.examplemenu;

import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.menu.commands.MenuCommand;
import de.davidbla.mc.examplemenu.menu.listener.OnInventoryClick;
import de.davidbla.mc.examplemenu.menu.listener.OnMenuClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

/**
 * Die Klasse kann einfach in die "main" eines anderen Plugins integriert werden.
 *
 * Wichtig für das Menu ist:
 *  - die HashMap menu
 *  - Registrieren der Events
 *  - Command Executor setzen
 *  - loadMenus() in der onEnable Funktion laden.
 */

public final class ExampleMenu extends JavaPlugin {

    private static ExampleMenu instance;

    /**
     * Wir nutzen eine statische HashMap um die Menus zu speichern und später darauf zuzugreifen.
     * Damit nicht bei jedem Zugriff auf ein Menu die Konfig neu geladen werden muss.
     */
    public static HashMap<Integer, Menu> menu;

    /**
     * In der onEnable() Funktion registeieren wir unsere Eventlistener.
     * - für das "OnInventoryClick" Event.
     * - für unser eigenes "OnMenuClick" Event.
     * Außerdem setzen wir einen "Command Executor" für den "menu" Command auf die MenuCommand Klasse.
     *
     * Um die HashMap "menu" zu füllen, rufen wir die "loadMenus()" Funktion auf.
     */
    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new OnInventoryClick(),this);
        Bukkit.getPluginManager().registerEvents(new OnMenuClick(),this);

        this.getCommand("menu").setExecutor(new MenuCommand());

        loadMenus();
    }

    /**
     * Diese Funktion erzeugt für jedes Menu eine neue Instanz und speichert diese
     * in der "menu" HashMap
     */
    public void loadMenus(){
        menu = new HashMap<>();
        List<Integer> menuIds = Menu.getMenuIds();
        for(Integer menuId : menuIds){
            menu.put(menuId,new Menu(menuId));
        }
    }

    @Override
    public void onDisable() {
        menu = null;
        instance = null;
    }

    public static ExampleMenu getInstance() {
        return instance;
    }
}
