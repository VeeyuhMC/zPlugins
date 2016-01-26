package cf.bluebracket.znexusfactions.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.Faction;
import cf.bluebracket.znexusfactions.api.FactionData;

public class Demote {
	
	public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (!(player.hasPermission("znexusfactions.demote"))) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (!(plugin.m.isOwner(player))) {
        	player.sendMessage("§4You need to be the owner of the faction!");
        	return true;
        }
        
        @SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        
        if (op != null) {
        	
        	FactionData factionData = plugin.m.getFaction(player);
        	
        	if (factionData.getPlayers().contains(op.getUniqueId())) {
        		
        		if (factionData.getStaff().contains(op.getUniqueId())) {
        			
        			Faction faction = factionData.getFaction();
        			faction.removeStaff(op);
        			factionData.setFaction(faction);
        			plugin.getDatabase().save(factionData);
        			
        			player.sendMessage("§6You demoted §1" + op.getName() + "§6!");
        			
        			if (op.getPlayer().isOnline()) {
        				
        				op.getPlayer().sendMessage("§6You were demoted by §1" + player.getName() + "§!");
        				
        			}
        			
        			return true;
        			
        		} else {
        			
        			player.sendMessage("§4That player is not a staff member!");
        			
        			return true;
        			
        		}
        		
        	} else {
        		
        		player.sendMessage("§4That player is not in the faction!");
        		
        		return true;
        		
        	}
        	
        } else {
        	
        	player.sendMessage("§4That player does not exist!");
        	
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions demote <PLAYER>");
        return true;
    }

}