package com.theretronix.hubp.listeners;

import com.theretronix.hubp.api.BungeeMessenger;
import com.theretronix.hubp.api.Staff;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory().getName() == "Servers") {

            Material serverItem = event.getCurrentItem().getType();

            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);

            if (serverItem == Material.DIAMOND_SWORD) {
                BungeeMessenger.sendPlayerTo(player, "Factions");
            } else if (serverItem == Material.DIAMOND_PICKAXE) {
                BungeeMessenger.sendPlayerTo(player, "Prison");
            } else if (serverItem == Material.FIREWORK) {
                BungeeMessenger.sendPlayerTo(player, "Arcade");
            } else if (serverItem == Material.WORKBENCH) {
                BungeeMessenger.sendPlayerTo(player, "Rust");
            } else if (serverItem == Material.NETHER_STAR) {
                player.sendMessage("§6§lWebsite: §7http://www.theretronix.com");
            }

            player.closeInventory();

        } else if (event.getClickedInventory().getName() == "Staff") {

            Material item = event.getCurrentItem().getType();
            Inventory inv;

            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);

            if (item == Material.DIAMOND_SWORD) {
                inv = Bukkit.createInventory(null, 54, "Factions");
                inv.setContents(Staff.getGUIPage("factions"));
                player.closeInventory();
                player.openInventory(inv);
            } else if (item == Material.DIAMOND_PICKAXE) {
                inv = Bukkit.createInventory(null, 54, "Prison");
                inv.setContents(Staff.getGUIPage("prison"));
                player.closeInventory();
                player.openInventory(inv);
            } else if (item == Material.FIREWORK) {
                inv = Bukkit.createInventory(null, 54, "Arcade");
                inv.setContents(Staff.getGUIPage("arcade"));
                player.closeInventory();
                player.openInventory(inv);
            } else if (item == Material.DIAMOND_BLOCK) {
                inv = Bukkit.createInventory(null, 54, "Global");
                inv.setContents(Staff.getGUIPage("global"));
                player.closeInventory();
                player.openInventory(inv);
            } else if (item == Material.WORKBENCH) {
                inv = Bukkit.createInventory(null, 54, "Rust");
                inv.setContents(Staff.getGUIPage("rust"));
                player.closeInventory();
                player.openInventory(inv);
            }

        } else if (event.getClickedInventory().getName() == "Factions" ||
                event.getClickedInventory().getName() == "Prison" ||
                event.getClickedInventory().getName() == "Arcade" ||
                event.getClickedInventory().getName() == "Global") {
            event.setCancelled(true);
        }

    }

}
