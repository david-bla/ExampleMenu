package de.davidbla.mc.examplemenu.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    private static final String ERROR = "&6[&4Error&6] &r: ";
    private static final String SUCCESS = "&6[&2Success&6] &r: ";

    public static void playerMsg(Player player,
                            String message){
        player.sendMessage(colorize(message));

    }
    public static void playerMsg(Player player,
                                 List<String> messages){
        for(String message : messages) {
            player.sendMessage(colorize(message));
        }
    }

    public static void noPermissionMsg(Player player){
        playerMsg(player,ERROR + "Dazu fehlen Dir die Berechtigungen!");
    }
    public static void noPermissionMsg(Player player, String permission){
        playerMsg(player,ERROR + "Dir fehlt die Berechtigung: '" + permission + "'!");
    }

    public static void menuCreated(Player player, Integer menuId){
        playerMsg(player, SUCCESS + "Menu mit der ID: '" + menuId + "' erstellt.");
    }

    public static void successMsg(Player player){
        playerMsg(player, SUCCESS + "Aktion erfolgreich ausgeführt.");
    }
    public static void successMsg(Player player, String message){
        playerMsg(player, SUCCESS + message);
    }
    public static void errorMsg(Player player){
        playerMsg(player, ERROR + "Aktion konnte nicht ausgeführt werden.");
    }
    public static void errorMsg(Player player, String message){
        playerMsg(player, ERROR + message);
    }
    public static String colorize(String message){
        if(message == null) return "Colorize ohne Text aufgerufen!";
        return ChatColor.translateAlternateColorCodes('&',message);
    }
    public static List<String> colorize(List<String> list){
        List<String> newList = new ArrayList<>();
        for(String msg : list){
            newList.add(colorize(msg));
        }
        return newList;
    }

    public static void errorInvSize(Player player) {
        playerMsg(player, ERROR + "Inventargröße kann nur ein vielfaches von 9 sein. Mindestens 9 maximal 54.");
    }
}
