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
public class GiveMoneyCommand implements CommandExecutor {

    zConomy plugin;

    public GiveMoneyCommand(zConomy plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("givemoney")) {
            if (args.length == 2) {
                if (sender instanceof ConsoleCommandSender) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                        long amount;
                        try {
                            amount = Long.parseLong(args[1]);
                        } catch (Exception e) {
                            sender.sendMessage("§4Invalid Arguments!");
                            return false;
                        }
                        playerData.setMoney(playerData.getMoney() + amount);
                        plugin.getDatabase().save(playerData);
                        sender.sendMessage("§6You gave §2$" + amount + " §6to §2" + target.getName());
                        target.sendMessage("§6You were given §2$" + amount);
                        return true;
                    } else if (Bukkit.getOfflinePlayer(args[0]) != null) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                        long amount;
                        try {
                            amount = Long.parseLong(args[1]);
                        } catch (Exception e) {
                            sender.sendMessage("§4Invalid Arguments!");
                            return false;
                        }
                        playerData.setMoney(playerData.getMoney() + amount);
                        plugin.getDatabase().save(playerData);
                        sender.sendMessage("§6You gave §2$" + amount + " §6to §2" + target.getName());
                        return true;
                    }
                } else if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("zconomy.give")) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player target = Bukkit.getPlayer(args[0]);
                            PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                    .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                            long amount;
                            try {
                                amount = Long.parseLong(args[1]);
                            } catch (Exception e) {
                                sender.sendMessage("§4Invalid Arguments!");
                                return false;
                            }
                            playerData.setMoney(playerData.getMoney() + amount);
                            plugin.getDatabase().save(playerData);
                            if (player == target) {
                                player.sendMessage("§6You gave yourself §2$" + amount);
                            } else {
                                sender.sendMessage("§6You gave §2$" + amount + " §6to §2" + target.getName());
                                target.sendMessage("§6You were given §2$" + amount);
                            }
                            return true;
                        } else if (Bukkit.getOfflinePlayer(args[0]) != null) {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                            PlayerData playerData = plugin.getDatabase().find(PlayerData.class)
                                    .where().ieq("playerUUID", target.getUniqueId().toString()).findUnique();
                            long amount;
                            try {
                                amount = Long.parseLong(args[1]);
                            } catch (Exception e) {
                                sender.sendMessage("§4Invalid Arguments!");
                                return false;
                            }
                            playerData.setMoney(playerData.getMoney() + amount);
                            plugin.getDatabase().save(playerData);
                            sender.sendMessage("§6You gave §2$" + amount + " §6to §2" + target.getName());
                            return true;
                        }
                    } else {
                        player.sendMessage("§4You do not have permission to do that!");
                        return true;
                    }
                }
            } else {
                sender.sendMessage("§4Invalid Arguments!");
            }
        }
        return false;
    }

}
