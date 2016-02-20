package com.zp4rker.znexusfactions.commands;

import java.util.UUID;

import com.zp4rker.znexusfactions.api.FactionData;
import com.zp4rker.znexusfactions.zNexusFactions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.zp4rker.znexusfactions.api.Faction;

public class Broadcast {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (!(args.length > 1)) {
            return invalidPerms(player);
        }

        if (!player.hasPermission("znexusfactions.broadcast")) {
            return plugin.m.invalidPerms(player);
        }

        if (!plugin.m.isInFaction(player)) {
            player.sendMessage("§4You are not in a faction!");
            return true;
        }

        FactionData factionData = plugin.m.getFaction(player);
        Faction faction = factionData.getFaction();

        for (UUID uuid : faction.getPlayers()) {

            Player target = Bukkit.getPlayer(uuid);

            if (target != null) {

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < args.length; i++) {

                    sb.append(args[i]);

                }

                target.sendMessage("§2Faction Broadcast: §r" + sb.toString());

            }

        }

        return true;

    }

    private static boolean invalidPerms(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions broadcast <MESSAGE>");
        return true;
    }

}