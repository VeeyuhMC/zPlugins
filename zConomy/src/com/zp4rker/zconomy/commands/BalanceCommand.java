package com.zp4rker.zconomy.commands;

import com.zp4rker.zconomy.PlayerData;
import com.zp4rker.zconomy.zConomy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class BalanceCommand implements CommandExecutor {

    zConomy plugin;

    public BalanceCommand(zConomy plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balance")) {
            if (args.length == 1) {
                if (sender instanceof ConsoleCommandSender) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (target != null) {
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                        long money;
                        if (playerData == null) {
                            sender.sendMessage("§4That player has never joined the server!");
                            return true;
                        } else {
                            money = playerData.getMoney();
                            sender.sendMessage("§2" + target.getName() + "§6's balance is §2$" + money);
                            return true;
                        }
                    } else {
                        sender.sendMessage("§4That player has never joined the server!");
                        return true;
                    }
                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (target != null) {
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                        long money;
                        if (playerData == null) {
                            sender.sendMessage("§4That player has never joined the server!");
                            return true;
                        } else {
                            money = playerData.getMoney();
                            sender.sendMessage("§2" + target.getName() + "§6's balance is §2$" + money);
                            return true;
                        }
                    } else {
                        sender.sendMessage("§4That player has never joined the server!");
                        return true;
                    }
                }
            } else if (args.length == 0) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                            .where().ieq("playerUUID", player.getUniqueId().toString()).findUnique();
                    long money = playerData.getMoney();
                    player.sendMessage("§6Your balance is §2$" + money);
                    return true;
                } else {
                    sender.sendMessage("§4Invalid Arguments!");
                }
            }
        }
        return false;
    }

}
