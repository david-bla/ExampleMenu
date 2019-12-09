package de.davidbla.mc.examplemenu;

import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.menu.commands.MenuCommand;
import de.davidbla.mc.examplemenu.menu.listener.OnInventoryClick;
import de.davidbla.mc.examplemenu.menu.listener.OnMenuClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ExampleMenu extends JavaPlugin {

    private static ExampleMenu instance;

    public static HashMap<Integer, Menu> menu;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new OnInventoryClick(),this);
        Bukkit.getPluginManager().registerEvents(new OnMenuClick(),this);

        instance.getCommand("menu").setExecutor(new MenuCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ExampleMenu getInstance() {
        return instance;
    }
}
