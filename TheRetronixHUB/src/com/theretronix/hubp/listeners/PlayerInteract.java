package com.theretronix.hubp.listeners;

import com.theretronix.hubp.api.JP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.theretronix.hubp.api.GUIPages;

public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getAction() != Action.PHYSICAL) {
			if (event.getPlayer().getItemInHand().getType() == Material.COMPASS) {

				Inventory gui = Bukkit.createInventory(null, 54, "Servers");

				GUIPages page = new GUIPages();

				gui.setContents(page.firstPage);

				event.getPlayer().openInventory(gui);

			} else if (event.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM) {

				Inventory gui = Bukkit.createInventory(null, 54, "Staff");

				GUIPages page = new GUIPages();

				gui.setContents(page.staffSelect);

				event.getPlayer().openInventory(gui);

			}
		} else {

            Location location = event.getPlayer().getLocation();

            if (JP.getJumpPadAt(location) != null) {
                JP jumpPad = JP.getJumpPadAt(location);
                jumpPad.jump(event.getPlayer());
            }

		}

	}

}
