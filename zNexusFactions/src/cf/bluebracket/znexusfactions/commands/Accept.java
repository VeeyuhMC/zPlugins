package cf.bluebracket.znexusfactions.commands;

import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.FactionData;

public class Accept {
	
	public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (!player.hasPermission("znexusfactions.accept")) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (plugin.m.isInFaction(player)) {
        	player.sendMessage("§4You are already in a faction!");
        	return true;
        }
        
        FactionData factionData = plugin.getDatabase().find(FactionData.class).where()
        		.ieq("name", args[0]).findUnique();
        
        if (factionData != null) {
        	
        	if (factionData.getInvited().contains(player)) {
        		
        		factionData.getFaction().addPlayer(player);
        		factionData.addPlayer(player);
        		
        		return true;
        		
        	} else {
        		
        		player.sendMessage("§4You weren't invited to that faction!");
        		
        		return true;
        		
        	}
        	
        } else {
        	
        	player.sendMessage("§4That faction does not exist!");
        	
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions accept <FACTION>");
        return true;
    }

}
