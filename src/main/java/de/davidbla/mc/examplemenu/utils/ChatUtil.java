package de.davidbla.mc.examplemenu.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static void playerMsg(Player player,
                            String message){
        player.sendMessage(colorize(message));

    }

    public static String colorize(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}
