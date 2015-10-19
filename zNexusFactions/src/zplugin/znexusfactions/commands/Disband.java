package zplugin.znexusfactions.commands;

import org.bukkit.entity.Player;
import zplugin.znexusfactions.api.FactionData;
import zplugin.znexusfactions.zNexusFactions;

public class Disband {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 2) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("znexusfactions.disband")) {
            player.sendMessage("§4You do not have permission to do that!");
            return true;
        }

        if (plugin.m.isInFaction(player)) {
            FactionData factionData = plugin.m.getFaction(player);
            return true;
        } else {
            player.sendMessage("§4You are not in a faction!");
            return true;
        }

    }

    public static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions disband");
        return true;
    }

}
