package cf.bluebracket.znexusfactions.commands;

import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.zNexusFactions;

public class Chat {

    public static boolean command(Player player, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 0) {
            return invalidArgs(player);
        }

        if (!(player.hasPermission("znexusfactions.chat"))) {
            return plugin.m.invalidPerms(player);
        }

        if (!plugin.m.isInFaction(player)) {

            player.sendMessage("§4You are not in a faction!");

            return true;

        }

        if (plugin.v.factionChat.contains(player)) {

            plugin.v.factionChat.remove(player);

            player.sendMessage("§2Toggled faction chat off");

            return true;

        } else {

            plugin.v.factionChat.add(player);

            player.sendMessage("§2Toggled faction chat on");

            return true;

        }

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/zfactions chat");
        return true;
    }

}
