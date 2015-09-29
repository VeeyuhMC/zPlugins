package com.zp4rker.zgamemode.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Creative implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("creative")) {

            if (args.length == 0) {

                if (sender instanceof Player) {

                    Player player = (Player) sender;

                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage("§2You are now in CREATIVE!");
                    return true;

                } else {
                    sender.sendMessage("§4You must be a player!");
                    return true;
                }

            } else if (args.length == 1) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {

                    sender.sendMessage("§4That Player is not online!");
                    return true;

                } else {

                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage("§2You are now in CREATIVE!");

                    sender.sendMessage("§2You set " + target.getName() + "'s gamemode to CREATIVE!");
                    return true;

                }

            }

        }

        return false;
    }

}
