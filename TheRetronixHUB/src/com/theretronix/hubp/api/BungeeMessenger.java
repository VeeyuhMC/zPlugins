package com.theretronix.hubp.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeMessenger implements PluginMessageListener {
	
	private static JavaPlugin plugin;
	public static int factionCount = 0;
	public static int prisonCount = 0;
	public static int arcadeCount = 0;
	
	public BungeeMessenger(JavaPlugin plugin) {
		BungeeMessenger.plugin = plugin;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		
		if (channel.equals("BungeeCord")) {
			
			ByteArrayDataInput in = ByteStreams.newDataInput(message);
			String subChannel = in.readUTF();
			
			if (subChannel.equals("PlayerCount")) {
				
				String server = in.readUTF();
				int playerCount = in.readInt();
				
				switch(server) {
				case "Factions":
					factionCount = playerCount;
					break;
				case "Prison":
					prisonCount = playerCount;
					break;
				case "Arcade":
					arcadeCount = playerCount;
					break;
				}
				
			}
			
		}
		
	}
	
	public static void requestCount(String server) {
		
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("PlayerCount");
		out.writeUTF(server);
		
		Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		
		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		
	}
	
	public static void sendPlayerTo(Player player, String server) {
		
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("Connect");
		out.writeUTF(server);
		
		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		
	}

}
