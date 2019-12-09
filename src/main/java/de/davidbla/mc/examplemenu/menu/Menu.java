package de.davidbla.mc.examplemenu.menu;

import de.davidbla.mc.examplemenu.ExampleMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.*;

public class Menu implements InventoryHolder {

    private static final String configFileName = "menu.yml";

    private static File configFile;
    private static FileConfiguration config;

    private Integer menuId;
    private ConfigurationSection menuCfg;

    private Inventory inv;
    private Integer size;
    private String title;
    private String permission;
    private HashMap<Integer, MenuPoint> menuPoints;

    public Menu(Integer menuId){
        menuPoints = new HashMap<>();
        this.menuId = menuId;
        menuCfg = config.getConfigurationSection("menu." + menuId);
        if(menuCfg == null){
            Bukkit.getServer().getConsoleSender().sendMessage("Error menuCfg null!");
        }
        this.size = menuCfg.getInt("size");
        this.title = menuCfg.getString("title");
        this.permission = menuCfg.getString("permission");
        inv = load(menuCfg);
    }

    private Inventory load(ConfigurationSection menuCfg){
        Inventory inv = Bukkit.createInventory(this, 9, "§6~ Menu ~");
        for( String menuPointNr : menuCfg.getConfigurationSection("menupoints").getKeys(false)){

            ConfigurationSection menuPointCfg = menuCfg.getConfigurationSection("menupoints." + menuPointNr);
            MenuPoint menuPoint = new MenuPoint(menuPointCfg);
            menuPoints.put(Integer.parseInt(menuPointNr),menuPoint);
            inv.setItem(Integer.parseInt(menuPointNr),menuPoint.getItem());
        }
        return inv;
    }

    public static List<Integer> getMenuIds(){
        config = config();

        List<Integer> menuIds = new ArrayList<>();
        for ( String menuId : config.getConfigurationSection("menu").getKeys(false)){
            menuIds.add(Integer.parseInt(menuId));
        }
        return menuIds;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    private static FileConfiguration config() {
        configFile = new File(ExampleMenu.getInstance().getDataFolder(), configFileName);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            ExampleMenu.getInstance().saveResource(configFileName, false);
        }
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }

    public void setTitle(String title) {
        this.title = title;
        update();
    }
    public void setSize(Integer size){
        this.size = size;
        update();
    }
    public void setPermission(String permission){
        this.permission = permission;
        update();
    }

    // Öffnet das Inventar
    public void open(Player p) {
        p.openInventory(inv);
    }

    // Gebe den MenuPunkt in Slot slotNr zurück
    public MenuPoint getMenuPoint(Integer slotNr){
        return this.menuPoints.get(slotNr);
    }

    public boolean isSlotFree (Integer slotNr){
        if(slotNr > this.size) return false;
        if(this.menuPoints.containsKey(slotNr)) return false;
        return true;
    }

    public boolean changePosition(Integer oldSlot, Integer newSlot){
        if(!isSlotFree(oldSlot) && isSlotFree(newSlot)){
            MenuPoint menuPoint = getMenuPoint(oldSlot);
            this.menuPoints.remove(oldSlot);
            this.menuPoints.put(newSlot,menuPoint);
            ItemStack item = inv.getItem(oldSlot);
            inv.clear(oldSlot);
            inv.setItem(newSlot,item);
            update();
            return true;
        }
        return false;
    }



    /*
    *   Update zum aktualisieren und speichern der Config verwenden.
    *   save() wird am ende von update() ausgeührt.
     */
    public void update(){
        menuCfg.set("title",this.title);
        menuCfg.set("size",this.size);
        menuCfg.set("permission",this.permission);
        updateMenuPoints();

        inv = load(menuCfg);
        save();
    }
    private void save() {
        config.set("menu." + menuId,menuCfg);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *   Menupunkte aktualisieren
     */
    public void updateMenuPoint(MenuPoint menuPoint, MenuAction menuAction, ItemStack item){
        menuPoint.setAction(menuAction);
        menuPoint.setActionData(menuAction,item);
        update();
    }
    public void updateMenuPoint(MenuPoint menuPoint, MenuAction menuAction, Location location){
        menuPoint.setAction(menuAction);
        menuPoint.setActionData(menuAction,location);
        update();
    }
    public void updateMenuPoint(MenuPoint menuPoint, MenuAction menuAction, String message){
        menuPoint.setAction(menuAction);
        menuPoint.setActionData(menuAction,message);
        update();
    }

    public void updateMenuPoints(){
        for(Integer menuPointNr : menuPoints.keySet()){
            menuCfg.set("menupoints." + menuPointNr,menuPoints.get(menuPointNr).getConfigurationSection());
        }
    }


}