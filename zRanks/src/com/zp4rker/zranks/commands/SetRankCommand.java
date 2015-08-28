package com.zp4rker.zranks.commands;

import com.zp4rker.zranks.config.Config;
import com.zp4rker.zranks.config.ConfigManager;
import com.zp4rker.zranks.zRanks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class SetRankCommand implements CommandExecutor {

    zRanks plugin;
    ConfigManager manager;
    Config ranks;

    public SetRankCommand(zRanks plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("setrank")) {
            if (sender instanceof ConsoleCommandSender) {
                if (args.length == 2) {
                    String playerName = args[0];
                    String rank = args[1];
                    Player player = Bukkit.getPlayer(playerName);
                    if (player != null) {
                        ranks = manager.getNewConfig("ranks.yml");
                        if (ranks.get("ranks." + rank) != null) {
                            ranks.set("players." + player.getUniqueId() + ".rank", rank);
                            ranks.saveConfig();
                            sender.sendMessage("§6Added §r" + player.getDisplayName() + " §6to the rank §2" + rank);
                            player.sendMessage("§6You were added to the rank §2" + rank);
                            return true;
                        } else {
                            sender.sendMessage("§4That rank does not exist!");
                            return true;
                        }
                    } else {
                        if (ranks.get("players." + playerName) != null) {
                            ranks.set("players." + playerName + ".rank", rank);
                            ranks.saveConfig();
                            sender.sendMessage("§6Added §r" + playerName + " §6to the rank §2" + rank);
                            return true;
                        } else {
                            sender.sendMessage("§4That player has never joined the server!");
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage("§4Invalid Arguments!");
                    return false;
                }
            } else if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("zranks.setrank")) {
                    if (args.length == 2) {
                        String playerName = args[0];
                        String rank = args[1];
                        Player target = Bukkit.getPlayer(playerName);
                        if (target != null) {
                            ranks = manager.getNewConfig("ranks.yml");
                            if (ranks.get("ranks." + rank) != null) {
                                ranks.set("players." + target.getName() + ".rank", rank);
                                ranks.saveConfig();
                                if (player == target) {
                                    target.sendMessage("§6You were added to the rank §2" + rank);
                                } else {
                                    sender.sendMessage("§6Added §r" + target.getDisplayName() + " §6to the rank §2" + rank);
                                    target.sendMessage("§6You were added to the rank §2" + rank);
                                }
                                return true;
                            } else {
                                sender.sendMessage("§4That rank does not exist!");
                                return true;
                            }
                        } else {
                            if (ranks.get("players." + playerName) != null) {
                                ranks.set("players." + playerName + ".rank", rank);
                                ranks.saveConfig();
                                sender.sendMessage("§6Added §r" + playerName + " §6to the rank §2" + rank);
                                return true;
                            } else {
                                sender.sendMessage("§4That player has never joined the server!");
                                return true;
                            }
                        }
                    } else {
                        player.sendMessage("§4Invalid Arguments!");
                        return false;
                    }
                } else {
                    player.sendMessage("§4You don't have permission to do that!");
                    return true;
                }
            }
        }
        return false;
    }

}
