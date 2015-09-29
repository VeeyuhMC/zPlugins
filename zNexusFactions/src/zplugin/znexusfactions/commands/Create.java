package zplugin.znexusfactions.commands;


import org.bukkit.entity.Player;
import zplugin.znexusfactions.api.Variables;
import zplugin.znexusfactions.zNexusFactions;

import java.util.HashMap;
import java.util.Map;

public class Create {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 2) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("znexusfactions.create")) {
            return plugin.m.invalidPerms(player);
        }

        int nameMax = plugin.getConfig().getInt("maximum-faction-name-length", 0);
        int tagMax = plugin.getConfig().getInt("maximum-faction-tag-length", 0);

        if (nameMax != 0 && args[0].length() < nameMax) {
            if (tagMax != 0 && args[1].length() < tagMax) {
                if (plugin.m.factionExists(args[0])) {
                    player.sendMessage("§4That Faction already exists!");
                    return true;
                } else {
                    Map<String, String> factionData = new HashMap<>();
                    factionData.put(args[0], args[1]);
                    Variables.makingFaction.put(player, factionData);
                    plugin.m.giveNexus(player);
                    return true;
                }
            }
        }

        return false;

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions create <NAME> <TAG>");
        return true;
    }

}