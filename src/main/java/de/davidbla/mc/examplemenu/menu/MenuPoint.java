package de.davidbla.mc.examplemenu.menu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;

public class MenuPoint {

    private ConfigurationSection menuPointCfg;

    private String title;
    private List<String> subtitles;
    private String viewPermission;
    private String usePermission;
    private MenuAction action;
    private ConfigurationSection actionData;
    private Material material;

    public MenuPoint(ConfigurationSection menuPointCfg){
        this.menuPointCfg = menuPointCfg;
        this.title = menuPointCfg.getString("title");
        this.subtitles = menuPointCfg.getStringList("subtitles");
        this.viewPermission = menuPointCfg.getString("viewPermission");
        this.usePermission = menuPointCfg.getString("usePermission");
        this.action = MenuAction.get(menuPointCfg.getString("action"));
        this.material = Material.getMaterial(menuPointCfg.getString("material"));
        this.actionData = menuPointCfg.getConfigurationSection("actionData");
        if(this.actionData == null){
            actionData = menuPointCfg.createSection("actionData");
        }
    }

    public MenuPoint(String title, List<String> subtitles, String viewPermission, String usePermission, MenuAction action, Material material, Integer slotNr, ConfigurationSection actionData){
        this.title = title;
        this.subtitles = subtitles;
        this.viewPermission = viewPermission;
        this.usePermission = usePermission;
        this.action = action;
        this.actionData = actionData;
        this.material = material;
    }

    // Getter Methoden:
    public String               getTitle()          {   return this.title;          }
    public List<String>         getSubtitles()      {   return this.subtitles;      }
    public String               getViewPermission() {   return this.viewPermission; }
    public String               getUsePermission()  {   return usePermission;       }
    public MenuAction           getAction()         {   return action;              }
    public ConfigurationSection getActionData()     {   return actionData;          }
    public Material             getMaterial()       {   return material;            }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(this.material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.title);
        meta.setLore(subtitles);
        item.setItemMeta(meta);
        return item;
    }

    // Setter Methoden:


    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitles(List<String> subtitles) {
        this.subtitles = subtitles;
    }

    public void setViewPermission(String viewPermission) {
        this.viewPermission = viewPermission;
    }

    public void setUsePermission(String usePermission) {
        this.usePermission = usePermission;
    }

    public void setAction(MenuAction action){
        this.action = action;
    }

    public void setActionData(ConfigurationSection actionData) {
        this.actionData = actionData;
    }
    public void setActionData(String key,String value) {
        this.actionData.set(key,value);
    }

    public void setMaterial(Material material){
        this.material = material;
    }
    /*
    *   Setze die Daten für die jeweilige Aktion (MenuAction)
     */
    public void setActionData(MenuAction action, ItemStack item){
        this.actionData.set(action.toString(),item);
    }
    public void setActionData(MenuAction action, Location location){
        this.actionData.set(action.toString(),location);
    }
    public void setActionData(MenuAction action, String message){
        this.actionData.set(action.toString(),message);
    }


    public ConfigurationSection getConfigurationSection(){
        update();
        return this.menuPointCfg;
    }

    private void update(){
        menuPointCfg.set("title",this.title);
        menuPointCfg.set("subtitles",this.subtitles);
        menuPointCfg.set("material",this.material.toString());
        menuPointCfg.set("viewPermission",this.viewPermission);
        menuPointCfg.set("usePermission",this.usePermission);
        menuPointCfg.set("action",this.action.toString());
        menuPointCfg.set("actionData",this.actionData);
    }

    // die Aktion nach MenuAction verlagern?
    public void click(Player player){
        if(player.hasPermission(this.usePermission)){
            MenuAction.run(player, action, actionData);
        }   // else "Du hast keine Berechtigung"
    }
}
