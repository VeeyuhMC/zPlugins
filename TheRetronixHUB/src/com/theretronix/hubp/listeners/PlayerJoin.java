package com.theretronix.hubp.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.theretronix.hubp.api.GUIItems;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		event.getPlayer().teleport(event.getPlayer().getLocation().getWorld().getSpawnLocation().add(.5, .5, .5));

		PlayerInventory playerInv = event.getPlayer().getInventory();
		boolean hasCompass = false;

		for (ItemStack itemStack : playerInv.getContents()) {
			if (itemStack != null) {
				if (itemStack.getType() == Material.COMPASS) {
					hasCompass = true;
				}
			}
		}

		if (!hasCompass) {

			event.getPlayer().getInventory().setItem(0, GUIItems.getCompass());
			event.getPlayer().getInventory().setItem(4, GUIItems.getStaff());

		}

	}

}
