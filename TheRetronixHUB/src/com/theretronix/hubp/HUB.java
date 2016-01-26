package com.theretronix.hubp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.theretronix.hubp.api.BungeeMessenger;
import com.theretronix.hubp.api.JPData;
import com.theretronix.hubp.listeners.InventoryClick;
import com.theretronix.hubp.listeners.PlayerInteract;
import com.theretronix.hubp.listeners.PlayerJoin;

public class HUB extends JavaPlugin {
	
	public void onEnable() {
		
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord",
				new BungeeMessenger(this));
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		registerEvents(
				new PlayerInteract(),
				new InventoryClick(),
				new PlayerJoin());
		
		setupDatabase();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				if (getServer().getOnlinePlayers().size() > 0) {
					BungeeMessenger.requestCount("Factions");
					BungeeMessenger.requestCount("Prison");
					BungeeMessenger.requestCount("Arcade");
				}
			}
			
		}, 0, 600);
		
	}
	
	public void onDisable() {}
	
	public void registerEvents(Listener... listeners) {
		for (Listener listener : listeners) {
			getServer().getPluginManager().registerEvents(listener, this);
		}
	}
	
	private void setupDatabase() {
		try {
			getDatabase().find(JPData.class).findRowCount();
		} catch(PersistenceException e) {
			System.out.println("Setting up database for TheRetronixHUB due to"
					+ " first time usage");
			installDDL();
		}
	}
	
	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<>();
		list.add(JPData.class);
		return list;
	}

}