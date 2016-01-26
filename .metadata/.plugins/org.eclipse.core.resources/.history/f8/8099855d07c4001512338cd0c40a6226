package com.theretronix.hubp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theretronix.hubp.api.BungeeMessenger;

public class Server implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("server")) {
			
			if (sender instanceof Player) {
				
				if (args.length == 1) {
					
					sender.sendMessage("§6Sending you to " + args[0] + "...");
					BungeeMessenger.sendPlayerTo((Player) sender, args[0]);
					return true;
					
				}
				
			} else {
				
				sender.sendMessage("§4You must be a player!");
				return true;
				
			}
			
		}
		
		return false;
		
	}

}
