package com.zp4rker.znexusfactions.commands;

import com.zp4rker.znexusfactions.zNexusFactions;
import org.bukkit.entity.Player;

public class Near {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 0) {
            return invalidArgs(player);
        }

        if (!(player.hasPermission("znexusfactions.near"))) {
            return plugin.m.invalidPerms(player);
        }

        double range = plugin.getConfig().getDouble("near-range");

        if (plugin.m.isNearFaction(player, range)) {

            double nearestAmount = plugin.m.getNearestAmount(plugin.m.getNearestFactionData(player, range), player);

            player.sendMessage("§6The nearest faction is §1" + nearestAmount + " §6blocks away!");

            return true;

        } else {

            player.sendMessage("§6You are not near any factions!");

            return true;

        }

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions near");
        return true;
    }

}
