package zplugin.znexusfactions.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zplugin.znexusfactions.api.FactionData;
import zplugin.znexusfactions.zNexusFactions;

public class Info {

    public static boolean command(CommandSender sender, String[] arguments, zNexusFactions plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 1) {
            return invalidArgs(sender);
        }

        if (!sender.hasPermission("znexusfactions.info")) {
            return plugin.m.invalidPerms(sender);
        }

        FactionData factionData = plugin.getDatabase().find(FactionData.class).where()
                .ieq("name", args[0]).findUnique();

        if (factionData != null) {

            sender.sendMessage("§1Faction Info");
            sender.sendMessage(String.format("§6Name: §2%n", factionData.getName()));
            sender.sendMessage(String.format("§6Tag: §2%t", factionData.getTag()));
            sender.sendMessage(String.format("§6Owner: §2%o", factionData.getFaction().getOwner().getName()));
            sender.sendMessage(String.format("§6Players: §2%p", factionData.getPlayers().size()));
            return true;

        } else {

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

            if (offlinePlayer != null) {

                sender.sendMessage(String.format("§6Name: §2%p", offlinePlayer.getName()));

                Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());

                if (player != null) {

                    sender.sendMessage(String.format("§6DisplayName: §2%d", player.getDisplayName()));
                    sender.sendMessage(String.format("§6Last Login: §2%l", "Online"));

                } else {

                    sender.sendMessage(String.format("§6Last Login: §2l", offlinePlayer.getLastPlayed()));

                }

                sender.sendMessage(String.format("§6Faction: §2%f", plugin.m.getFaction(offlinePlayer) == null ?
                "None" : plugin.m.getFaction(player).getName()));

                return true;

            } else {

                sender.sendMessage("§4No player or faction exists with that name!");

                return true;

            }

        }

    }

    private static boolean invalidArgs(CommandSender sender) {
        sender.sendMessage("§4Invalid Arguments!");
        sender.sendMessage("§2/zfactions info <FACTION>");
        return true;
    }

}
