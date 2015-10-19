package zplugin.znexusfactions.commands;

import org.bukkit.entity.Player;
import zplugin.znexusfactions.api.Faction;
import zplugin.znexusfactions.api.FactionData;
import zplugin.znexusfactions.zNexusFactions;

public class Leave {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 0) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("znexusfactions.leave")) {
            return plugin.m.invalidPerms(player);
        }

        if (plugin.m.isInFaction(player)) {
            FactionData factionData = plugin.m.getFaction(player);
            Faction faction = factionData.getFaction();
            faction.removePlayer(player);
            factionData.setFaction(faction);
            plugin.getDatabase().save(factionData);
            return true;
        } else {
            player.sendMessage("§4You are not in a faction!");
            return true;
        }

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions leave");
        return true;
    }

}