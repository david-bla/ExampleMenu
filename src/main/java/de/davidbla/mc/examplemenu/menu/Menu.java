package de.davidbla.mc.examplemenu.menu;

import de.davidbla.mc.examplemenu.ExampleMenu;
import de.davidbla.mc.examplemenu.utils.ChatUtil;
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
    private String viewPermission;
    private String editPermission;
    private HashMap<Integer, MenuPoint> menuPoints;

    public Menu(Integer menuId){
        menuPoints = new HashMap<>();
        this.menuId = menuId;
        menuCfg = config().getConfigurationSection("menu." + menuId);
        this.size = menuCfg.getInt("size");
        this.title = menuCfg.getString("title");
        this.viewPermission = menuCfg.getString("viewPermission");
        this.editPermission = menuCfg.getString("editPermission");
        inv = load(menuCfg);
    }

    public Menu(){
        List<Integer> menuIds = getMenuIds();
        Integer newMenuId = 1;
        while(menuIds.contains(newMenuId)){
            newMenuId++;
        }
        this.menuId = newMenuId;
        config().createSection("menu." + this.menuId, new HashMap<>());
        ConfigurationSection menuCfg = config().getConfigurationSection("menu." + this.menuId);

        this.title = "New Menu";
        this.size = 9;
        this.viewPermission = "menu." + this.menuId;
        this.editPermission = "menu.edit." + this.menuId;

        menuCfg.set("title",this.title);
        menuCfg.set("size",this.size);
        menuCfg.set("viewPermission",this.viewPermission);
        menuCfg.set("editPermission",this.editPermission);
        // Leere "menupoint" Punkt in die Config eintragen.
        menuCfg.createSection("menupoints",new HashMap<>());

        save(this.menuId,menuCfg);
        menuCfg = menuCfg.getConfigurationSection(this.menuId.toString()); //config.getConfigurationSection("menu." + this.menuId);
        inv = load(menuCfg);
    }

    private Inventory load(ConfigurationSection menuCfg) {
        Inventory inv = Bukkit.createInventory(this, this.size, this.title);
        if (menuCfg != null){
            if (menuCfg.getConfigurationSection("menupoints") != null) {
                for (String menuPointNr : menuCfg.getConfigurationSection("menupoints").getKeys(false)) {
                    if(this.size <= Integer.parseInt(menuPointNr)) break;
                    ConfigurationSection menuPointCfg = menuCfg.getConfigurationSection("menupoints." + menuPointNr);
                    MenuPoint menuPoint = new MenuPoint(menuPointCfg);
                    menuPoints.put(Integer.parseInt(menuPointNr), menuPoint);
                    inv.setItem(Integer.parseInt(menuPointNr), menuPoint.getItem());
                }
            }
        }
        return inv;
    }

    public static List<Integer> getMenuIds(){
        List<Integer> menuIds = new ArrayList<>();
        for ( String menuId : config().getConfigurationSection("menu").getKeys(false)){
            menuIds.add(Integer.parseInt(menuId));
        }
        return menuIds;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public static FileConfiguration config() {
        if(config != null) return config;
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
        this.title = ChatUtil.colorize(title);
        update();
    }
    public void setSize(Integer size){
        this.size = size;
        update();
    }
    public String getViewPermission(){
        return this.viewPermission;
    }
    public void setViewPermission(String viewPermission){
        this.viewPermission = viewPermission;
        update();
    }
    public String getEditPermission(){
        if(this.editPermission == null) return "";
        return this.editPermission;
    }
    public void setEditPermission(String editPermission){
        this.editPermission = editPermission;
        update();
    }

    public Integer getId(){
        return this.menuId;
    }

    // Öffnet das Inventar
    public void open(Player p) {
        if(!p.hasPermission(this.viewPermission)){
            ChatUtil.noPermissionMsg(p,this.viewPermission);
            return;
        }
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

    public boolean addMenuPoint(MenuPoint menuPoint, Integer slotNr){
        if(isSlotFree(slotNr)){
            this.menuPoints.put(slotNr,menuPoint);
            update();
            return true;
        }
        return false;
    }

    public boolean changePosition(Integer oldSlot, Integer newSlot){
        if(!isSlotFree(oldSlot) && isSlotFree(newSlot)){
            MenuPoint menuPoint = getMenuPoint(oldSlot);
            Menu.delete(menuId,oldSlot);
            addMenuPoint(menuPoint,newSlot);
            update();
            return true;
        }else if(!isSlotFree(oldSlot) && !isSlotFree(newSlot)){
            MenuPoint tempMenuPoint = getMenuPoint(newSlot);
            MenuPoint menuPoint = getMenuPoint(oldSlot);
            Menu.delete(menuId,newSlot);
            Menu.delete(menuId,oldSlot);
            addMenuPoint(menuPoint,newSlot);
            addMenuPoint(tempMenuPoint,oldSlot);
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
        menuCfg.set("viewPermission",this.viewPermission);
        menuCfg.set("editPermission",this.editPermission);
        updateMenuPoints();

        inv = load(menuCfg);
        save(this.menuId,menuCfg);
    }

    public static boolean delete(Integer menuId){
        if( ExampleMenu.menu.containsKey(menuId) ){
            config().set("menu."+menuId,null);
            ExampleMenu.getInstance().loadMenus();
            save();
            return true;
        }
        return false;
    }


    public static boolean delete(Integer menuId, Integer slotId){
        if( ExampleMenu.menu.containsKey(menuId) ) {
            if (ExampleMenu.menu.get(menuId).isSlotFree(slotId)) return false;
            config().set("menu." + menuId + ".menupoints." + slotId, null);
            ExampleMenu.getInstance().loadMenus();
            save();
            return true;
        }
        return false;
    }
    private static void save(){
        try {
            config().save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExampleMenu.getInstance().loadMenus();
    }

    private static void save(Integer menuId, ConfigurationSection menuCfg) {
        config().set("menu." + menuId,menuCfg);
        save();
    }
    /*
    *   Menupunkte aktualisieren
     */
    public void updateMenuPoint(MenuPoint menuPoint, MenuAction menuAction){
        menuPoint.setAction(menuAction);
        update();
    }
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