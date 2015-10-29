package cf.bluebracket.znexusfactions.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.Faction;
import cf.bluebracket.znexusfactions.api.FactionData;

public class Kick {
	
	public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (player.hasPermission("znexusfactions.kick")) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (!plugin.m.isInFaction(player)) {
        	player.sendMessage("§4You must be in a faction!");
        	return true;
        }
        
        if (!plugin.m.isStaff(player)) {
        	player.sendMessage("§4You must be staff in the faction!");
        	return true;
        }
        
        @SuppressWarnings("deprecation")
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        
        if (target != null) {
        	
        	FactionData factionData = plugin.m.getFaction(player);
        	Faction faction = factionData.getFaction();
        	
        	if (!factionData.getPlayers().contains(target.getUniqueId())) {
        		
        		player.sendMessage("§4That player is not in the faction!");
        		
        	}
        	
        	factionData.removePlayer(target);
        	faction.removePlayer(target, true);
        	plugin.getDatabase().save(factionData);
        	
        	Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
        	
        	if (targetPlayer != null) {
        		
        		targetPlayer.sendMessage("§1You were kicked from your faction!");
        		
        	}
        	
        	player.sendMessage("§1You kicked §6" + target.getName() + " §1 from the faction!");
        	
        	for (UUID uuid : factionData.getPlayers()) {
        		
        		Player theTarget = Bukkit.getPlayer(uuid);
        		
        		if (theTarget != null) {
        			
        			theTarget.sendMessage("§6" + target.getName() + " §1was kicked from the faction!");
        			
        		}
        		
        	}
        	
        	return true;
        	
        } else {
        	
        	player.sendMessage("§4That player doesn't exist!");
        	
        }
		
		return true;
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions kick <NAME>");
        return true;
    }

}
