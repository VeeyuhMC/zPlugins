package com.theretronix.hubp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		event.getPlayer().getInventory().clear();
	}

}
