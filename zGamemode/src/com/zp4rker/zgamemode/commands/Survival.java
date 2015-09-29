package com.zp4rker.zgamemode.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Survival implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("survival")) {

            if (args.length == 0) {

                if (sender instanceof Player) {

                    Player player = (Player) sender;

                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage("§2You are now in SURVIVAL!");
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

                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage("§2You are now in SURVIVAL!");

                    sender.sendMessage("§2You set " + target.getName() + "'s gamemode to SURVIVAL!");
                    return true;

                }

            }

        }

        return false;
    }

}
