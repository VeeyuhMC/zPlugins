package com.theretronix.hubp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.theretronix.hubp.api.BungeeMessenger;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (event.getClickedInventory().getName() == "Servers") {
			
			Material serverItem = event.getCurrentItem().getType();
			
			event.setCancelled(true);
			
			Player player = (Player) event.getWhoClicked();
			
			if (serverItem == Material.DIAMOND_SWORD) {
				BungeeMessenger.sendPlayerTo(player, "Factions");
			} else if (serverItem == Material.DIAMOND_PICKAXE) {
				BungeeMessenger.sendPlayerTo(player, "Prison");
			} else if (serverItem == Material.FIREWORK) {
				BungeeMessenger.sendPlayerTo(player, "Arcade");
			} else if (serverItem == Material.NETHER_STAR) {
				player.sendMessage("�6�lWebsite: �7http://www.theretronix.com");
			}
			
		}
		
	}

}
