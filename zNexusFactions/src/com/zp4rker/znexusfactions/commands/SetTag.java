package com.zp4rker.znexusfactions.commands;

import com.zp4rker.znexusfactions.api.Faction;
import com.zp4rker.znexusfactions.api.FactionData;
import com.zp4rker.znexusfactions.zNexusFactions;
import org.bukkit.entity.Player;

public class SetTag {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length == 0) {
            return invalidArgs(player);
        }

        FactionData factionData = plugin.m.getFaction(player);
        Faction faction = factionData.getFaction();

        if (faction.getOwner().getUniqueId() != player.getUniqueId()) {
            player.sendMessage("§4You have to be the owner of the faction!");
            return true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(args[0]);

        for (int i = 1; i < args.length; i++) {
            sb.append(" " + args[i]);
        }

        String tag = sb.toString();

        faction.setTag(tag);
        factionData.setFaction(faction);
        plugin.getDatabase().save(factionData);

        player.sendMessage("§6Set your factions tag to §2\"" + tag + "\"§6.");

        return true;

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions tag <TAG>");
        return true;
    }

}
