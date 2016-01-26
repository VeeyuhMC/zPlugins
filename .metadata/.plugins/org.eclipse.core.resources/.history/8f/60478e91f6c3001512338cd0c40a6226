package com.theretronix.hubp.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		event.getPlayer().teleport(event.getPlayer().getLocation().getWorld().getSpawnLocation().add(.5, .5, .5));

		PlayerInventory playerInv = event.getPlayer().getInventory();
		boolean hasCompass = false;

		for (ItemStack itemStack : playerInv.getContents()) {
			if (itemStack.getType() == Material.COMPASS) {
				hasCompass = true;
			}
		}

		if (!hasCompass) {

			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemMeta itemMeta = compass.getItemMeta();
			itemMeta.setDisplayName("§6§lServer Selector");
			List<String> lore = new ArrayList<>();
			lore.add("§7Right click to");
			lore.add("§7select a server.");
			itemMeta.setLore(lore);
			compass.setItemMeta(itemMeta);

			event.getPlayer().getInventory().addItem(compass);

		}

	}

}
