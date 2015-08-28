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
public class PromoteCommand implements CommandExecutor {

    zRanks plugin;
    ConfigManager manager;

    public PromoteCommand(zRanks plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("promote")) {
            if (args.length == 1) {
                if (sender instanceof ConsoleCommandSender) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage("§6That player is not online!");
                        return true;
                    } else {
                        Config ranks = manager.getNewConfig("ranks.yml");
                        String rank = ranks.getString("players." + target.getUniqueId() + ".rank");
                        String promotion = ranks.getString("ranks." + rank + ".promotion");
                        ranks.set("players." + target.getUniqueId() + ".rank", promotion);
                        ranks.saveConfig();
                        return true;
                    }
                } else {
                    Player player = (Player) sender;
                    if (player.hasPermission("zranks.promote")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage("§4That player is not online!");
                            return true;
                        } else {
                            Config ranks = manager.getNewConfig("ranks.yml");
                            String rank = ranks.getString("players." + target.getUniqueId() + ".rank");
                            String promotion = ranks.getString("ranks." + rank + ".promotion");
                            ranks.set("players." + target.getUniqueId() + ".rank", promotion);
                            ranks.saveConfig();
                            return true;
                        }
                    } else {
                        player.sendMessage("§4You do not have permission to do that!");
                    }
                }
            } else {
                sender.sendMessage("§4Invalid Arguments!");
            }
        }
        return false;
    }

}

