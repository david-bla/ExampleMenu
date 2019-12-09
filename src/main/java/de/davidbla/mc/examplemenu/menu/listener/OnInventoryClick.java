package de.davidbla.mc.examplemenu.menu.listener;

import de.davidbla.mc.examplemenu.menu.Menu;
import de.davidbla.mc.examplemenu.menu.event.MenuClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class OnInventoryClick implements Listener {
    // Füge einen Eventlistener für Klicks in Inventare hinzu.
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        // Wenn das Inventar, in das geklickt wurde keine Instanz unserer "Menu" Klasse ist, return.
        if (!(e.getInventory().getHolder() instanceof Menu)) { return; }

        // Breche das eigendliche Event ab (Item aus Inventar nehmen).
        e.setCancelled(true);

        // Ermittle den Spieler welcher geklickt hat.
        Player p = (Player) e.getWhoClicked();
        // Ermittle das Item auf das geklickt wurde.
        ItemStack clickedItem = e.getCurrentItem();

        // Falls kein Item angeklickt oder Air angeklickt wurde, return.
        //  Um das Item in einem Inventar zu entfernen oder um "leere" Felder mit
        //  mouseover Effekt zu erhalten, nutzen wir Air.
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        // Caste das aufgerufene Menu zu unserer "Menu" Klasse
        Bukkit.getPluginManager().callEvent(new MenuClickEvent(p,(Menu) e.getInventory().getHolder(),e.getRawSlot()));
    }
}
