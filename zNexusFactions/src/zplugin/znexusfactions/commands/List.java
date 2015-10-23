package zplugin.znexusfactions.commands;

import org.bukkit.command.CommandSender;
import zplugin.znexusfactions.zNexusFactions;

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

        return true;

    }

    private static boolean invalidArgs(CommandSender sender) {
        sender.sendMessage("§4Invalid Arguments!");
        sender.sendMessage("§2/zfactions list <PAGE>");
        return true;
    }

}
