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
public class SetMoneyCommand implements CommandExecutor {

    zConomy plugin;

    public SetMoneyCommand(zConomy plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("setmoney")) {
            if (args.length == 2) {
                if(sender instanceof ConsoleCommandSender) {
                    if(Bukkit.getOfflinePlayer(args[0]) == null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target == null) {
                            sender.sendMessage("§4That player has never joined the server!");
                        } else {
                            PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                    .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                            long amount = 0;
                            try {
                                amount = Long.parseLong(args[1]);
                            } catch (Exception e) {
                                sender.sendMessage("§4Invalid Arguments!");
                            }
                            playerData.setMoney(amount + playerData.getMoney());
                            plugin.getDatabase().save(playerData);
                            sender.sendMessage("§6Set §2" + target.getName() + "§6's money to §2$" + args[1]);
                            target.sendMessage("§6Your money was set to §2$" + amount);
                            return true;
                        }
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                        long amount = 0;
                        try {
                            amount = Long.parseLong(args[1]);
                        } catch (Exception e) {
                            sender.sendMessage("§4Invalid Arguments!");
                        }
                        playerData.setMoney(amount + playerData.getMoney());
                        plugin.getDatabase().save(playerData);
                        sender.sendMessage("§6Set §2" + target.getName() + "§6's money to §2$" + args[1]);
                        return true;
                    }
                } else if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if(player.hasPermission("zconomy.set")) {
                        if(Bukkit.getOfflinePlayer(args[0]) == null) {
                            Player target = Bukkit.getPlayer(args[0]);
                            if(target == null) {
                                sender.sendMessage("§4That player has never joined the server!");
                            } else {
                                PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                        .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                                long amount = 0;
                                try {
                                    amount = Long.parseLong(args[1]);
                                } catch (Exception e) {
                                    sender.sendMessage("§4Invalid Arguments!");
                                }
                                playerData.setMoney(amount + playerData.getMoney());
                                plugin.getDatabase().save(playerData);
                                sender.sendMessage("§6Set §2" + target.getName() + "§6's money to §2$" + args[1]);
                                target.sendMessage("§6Your money was set to §2$" + amount);
                                return true;
                            }
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                            PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                    .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                            long amount = 0;
                            try {
                                amount = Long.parseLong(args[1]);
                            } catch (Exception e) {
                                sender.sendMessage("§4Invalid Arguments!");
                            }
                            playerData.setMoney(amount + playerData.getMoney());
                            plugin.getDatabase().save(playerData);
                            sender.sendMessage("§6Set §2" + target.getName() + "§6's money to §2$" + args[1]);
                            return true;
                        }
                    } else {
                        player.sendMessage("§4You do not have permission to do that!");
                        return false;
                    }
                }
            } else {
                sender.sendMessage("§4Invalid Arguments!");
            }
        }
        return false;
    }
}
