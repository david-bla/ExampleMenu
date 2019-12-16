package de.davidbla.mc.examplemenu.menu;

import de.davidbla.mc.examplemenu.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class MenuPoint {

    private ConfigurationSection menuPointCfg;

    private String title;
    private List<String> subtitles;
    private String permission;
    private MenuAction action;
    private ConfigurationSection actionData;
    private Material material;

    public MenuPoint(ConfigurationSection menuPointCfg){
        this.menuPointCfg = menuPointCfg;
        this.title = menuPointCfg.getString("title");
        this.subtitles = menuPointCfg.getStringList("subtitles");
        this.permission = menuPointCfg.getString("permission");
        this.action = MenuAction.get(menuPointCfg.getString("action"));
        this.material = Material.getMaterial(menuPointCfg.getString("material"));
        this.actionData = menuPointCfg.getConfigurationSection("actionData");
        if(this.actionData == null){
            this.actionData = menuPointCfg.createSection("actionData");
        }
    }

    public MenuPoint(Integer menuId, Integer slotId, String title, List<String> subtitles, String permission, MenuAction action, Material material){
        String cfgPath = "menu." + menuId + ".menupoints";
        Menu.config().createSection(cfgPath + "." + slotId,new HashMap<Integer, MenuPoint>());
        this.menuPointCfg = Menu.config().getConfigurationSection(cfgPath + "." + slotId);
        if(this.menuPointCfg == null) System.out.println("-.-");
        this.title = title;
        this.subtitles = subtitles;
        this.permission = permission;
        this.action = action;
        this.material = material;
    }

    // Getter Methoden:
    public String               getTitle()          {   return this.title;          }
    public List<String>         getSubtitles()      {   return this.subtitles;      }
    public String               getPermission()     {   return permission;          }
    public MenuAction           getAction()         {   return action;              }
    public ConfigurationSection getActionData()     {   return actionData;          }
    public Material             getMaterial()       {   return material;            }

    public ItemStack getItem() {
        if( this.material == null ) this.material = Material.BARRIER;
        ItemStack item = new  ItemStack(this.material);
        ItemMeta meta = item.getItemMeta();
        if( meta != null) {
            meta.setDisplayName(ChatUtil.colorize(this.title));
            meta.setLore(ChatUtil.colorize(this.subtitles));
            item.setItemMeta(meta);
        }
        return item;
    }

    // Setter Methoden:


    public void setTitle(String title) {
        this.title = title;
        update();
    }

    public void setSubtitles(List<String> subtitles) {
        this.subtitles = subtitles;
        update();
    }

    public void setSubtitles(Integer subtitleIndex, String subtitle) {
        if(subtitleIndex < 0 || 2 < subtitleIndex) return;
        this.subtitles.set(subtitleIndex,subtitle);
        update();
    }

    public void setPermission(String permission) {
        this.permission = permission;
        update();
    }

    public void setAction(MenuAction action){
        this.action = action;
        update();
    }

    public void setActionData(ConfigurationSection actionData) {
        this.actionData = actionData;
        update();
    }
    public void setActionData(String key,String value) {
        this.actionData.set(key,value);
        update();
    }

    public void setMaterial(Material material){
        this.material = material;
        update();
    }
    /*
    *   Setze die Daten für die jeweilige Aktion (MenuAction)
     */
    public void setActionData(MenuAction action, ItemStack item){
        this.actionData.set(action.toString(),item);
        update();
    }
    public void setActionData(MenuAction action, Location location){
        this.actionData.set(action.toString(),location);
        update();
    }
    public void setActionData(MenuAction action, String message){
        this.actionData.set(action.toString(),message);
        update();
    }


    public ConfigurationSection getConfigurationSection(){
        update();
        return this.menuPointCfg;
    }

    private void update(){
        menuPointCfg.set("title",this.title);
        menuPointCfg.set("subtitles",this.subtitles);
        menuPointCfg.set("material",this.material.toString());
        menuPointCfg.set("permission",this.permission);
        menuPointCfg.set("action",this.action.toString());
        menuPointCfg.set("actionData",this.actionData);
    }

    // die Aktion nach MenuAction verlagern?
    public void click(Player player){
        if(player.hasPermission(this.permission)){
            MenuAction.run(player, action, actionData);
        }   else {
            ChatUtil.noPermissionMsg(player);
        }
    }
}
