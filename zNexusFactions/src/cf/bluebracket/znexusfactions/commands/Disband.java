package cf.bluebracket.znexusfactions.commands;

import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.zNexusFactions;
import cf.bluebracket.znexusfactions.api.Faction;
import cf.bluebracket.znexusfactions.api.FactionData;

public class Disband {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 0) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("znexusfactions.disband")) {
            player.sendMessage("§4You do not have permission to do that!");
            return true;
        }

        if (plugin.m.isInFaction(player)) {
            if (plugin.m.isOwner(player)) {
                FactionData factionData = plugin.m.getFaction(player);
                Faction faction = factionData.getFaction();
                faction.disband(player);
                plugin.getDatabase().delete(factionData);
            }
            return true;
        } else {
            player.sendMessage("§4You are not in a faction!");
            return true;
        }

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions disband");
        return true;
    }

}
