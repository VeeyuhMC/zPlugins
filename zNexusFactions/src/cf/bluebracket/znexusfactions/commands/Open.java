package cf.bluebracket.znexusfactions.commands;

import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.Faction;
import cf.bluebracket.znexusfactions.api.FactionData;

public class Open {
	
	public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 0) {
        	return invalidArgs(player);
        }
        
        if (player.hasPermission("znexusfactions.open")) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (!plugin.m.isInFaction(player)) {
        	player.sendMessage("§4You must be in a faction!");
        	return true;
        }
        
        if (!plugin.m.isOwner(player)) {
        	player.sendMessage("§4You must be the owner of the faction!");
        	return true;
        }
        
        FactionData factionData = plugin.m.getFaction(player);
        Faction faction = factionData.getFaction();
        boolean open = faction.isOpen();
        
        faction.setOpen(open ? false : true);
        
        factionData.setFaction(faction);
        
        plugin.getDatabase().save(factionData);
        
        player.sendMessage("§6Your faction is now §1" + (open ? "closed" : "open") + "§6!");
		
		return true;
		
	}
	
	private static boolean invalidArgs(Player player) {
		player.sendMessage("§4Invalid Arguments!");
		player.sendMessage("§2/zfactions open");
		return true;
	}

}
