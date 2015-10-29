package cf.bluebracket.znexusfactions.commands;

import org.bukkit.command.CommandSender;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.FactionData;

public class Destroy {
	
	public static boolean command(CommandSender sender, String[] arguments, zNexusFactions plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(sender);
        }
        
        if (sender.hasPermission("znexusfactions.admin")) {
        	return plugin.m.invalidPerms(sender);
        }
        
        FactionData factionData = plugin.getDatabase().find(FactionData.class).where()
        		.ieq("name", args[0]).findUnique();
        
        if (factionData == null) {
        	
        	sender.sendMessage("§4That faction does not exist!");
        	return true;
        	
        } else {
        	
        	factionData.getFaction().disband(sender);
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(CommandSender sender) {
        sender.sendMessage("§4Invalid Arguments!");
        sender.sendMessage("§2/zfactions disband");
        return true;
    }

}
