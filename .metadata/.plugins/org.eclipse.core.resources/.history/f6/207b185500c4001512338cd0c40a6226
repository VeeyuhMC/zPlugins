package com.theretronix.hubp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.theretronix.hubp.api.GUIPages;

public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getPlayer().getItemInHand().getType() == Material.COMPASS) {

			Inventory gui = Bukkit.createInventory(null, 45, "Servers");
			
			GUIPages page = new GUIPages();
			
			gui.setContents(page.firstPage);

			event.getPlayer().openInventory(gui);

		}

	}

}
