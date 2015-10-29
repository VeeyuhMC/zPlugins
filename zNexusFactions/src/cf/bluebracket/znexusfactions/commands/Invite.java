package cf.bluebracket.znexusfactions.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.FactionData;

public class Invite {
	
	public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (!player.hasPermission("znexusfactions.invite")) {
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
        
        FactionData factionData = plugin.m.getFaction(player);
        Player target = Bukkit.getPlayer(args[0]);
        
        if (target != null) {
        	
        	if (!plugin.m.isInFaction(player)) {
        		
        		if (plugin.m.getFaction(target) != factionData) {
            		
            		factionData.addInvited(target);
            		plugin.getDatabase().save(factionData);
            		
            		player.sendMessage("§6You invited §1" + target.getName() + " §6to your faction!");
            		target.sendMessage("§6You were invited to join §1" + factionData.getName() + "§6!");
            		
            		return true;
            		
            	} else {
            		
            		player.sendMessage("§4That player is already in the faction!");
            		
            		return true;
            		
            	}
        		
        	} else {
        		
        		player.sendMessage("§4That player is already in a faction!");
        		
        		return true;
        		
        	}
        	
        } else {
        	
        	player.sendMessage("§4That player is not online!");
        	
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions invite <NAME>");
        return true;
    }

}
