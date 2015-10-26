package cf.bluebracket.znexusfactions.commands;

import org.bukkit.command.CommandSender;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.FactionData;

public class List {

	public static boolean command(CommandSender sender, String[] arguments, zNexusFactions plugin) {

		String[] args = new String[arguments.length - 1];

		for (int i = 1; i < arguments.length; i++) {
			args[i - 1] = arguments[i];
		}

		if (args.length > 1) {
			return invalidArgs(sender);
		}

		if (sender.hasPermission("znexusfactions.list")) {
			return plugin.m.invalidPerms(sender);
		}
		
		sender.sendMessage("§6Factions: ");
		int pageNumber = 0;

		if (args.length == 0) {
			
			pageNumber = 1;
			
			for (FactionData factionData : plugin.m.getPage(0)) {
				
				sender.sendMessage("§6- §1" + factionData.getName());
				
			}
			
		} else {
			
			try {
				pageNumber = Integer.parseInt(args[0]);
				
			} catch (NumberFormatException e) {
				
				return invalidArgs(sender);
				
			}
			
			for (FactionData factionData : plugin.m.getPage(pageNumber - 1)) {
				
				sender.sendMessage("§6- §1" + factionData.getName());
				
			}
			
		}
		
		sender.sendMessage("§6Page §1" + pageNumber + " §6of " + plugin.m.getPages());

		return true;

	}

	private static boolean invalidArgs(CommandSender sender) {
		sender.sendMessage("§4Invalid Arguments!");
		sender.sendMessage("§2/zfactions list <PAGE>");
		return true;
	}

}
