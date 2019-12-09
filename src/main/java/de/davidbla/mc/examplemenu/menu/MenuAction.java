package de.davidbla.mc.examplemenu.menu;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum MenuAction {
    Teleport, Item, PlayerCommand, ConsoleCommand, SendPlayerMessage, BroadcastMessage;

    public static MenuAction get(String string){
        for( MenuAction menuAction : MenuAction.values() ){
            if(menuAction.toString().equalsIgnoreCase(string)){
                return menuAction;
            }
        }
        return null;
    }

    public static void run(Player player,
                           MenuAction action,
                           ConfigurationSection actionData){
        switch(action) {
            case Teleport:
                player.teleport(actionData.getLocation(action.toString()));
                break;

            case Item:
                ItemStack item = actionData.getItemStack(action.toString());
                player.getInventory().addItem(item);
                break;

            case SendPlayerMessage:
                player.sendMessage(actionData.getString(action.toString()));
                break;

            case BroadcastMessage:
                Bukkit.broadcastMessage(actionData.getString(action.toString()));
                break;

            case PlayerCommand:
                player.performCommand(actionData.getString(action.toString()));
                break;

            case ConsoleCommand:
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),actionData.getString(action.toString()).replace("%p",player.getName()));
                break;

            default:
                System.out.println("Test");
                // Error
        }
    }
}
