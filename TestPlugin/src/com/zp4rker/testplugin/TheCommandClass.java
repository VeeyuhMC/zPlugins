package com.zp4rker.testplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TheCommandClass implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("test")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage("You are a player!");
                return true;
            }

            if (sender instanceof ConsoleCommandSender) {

                sender.sendMessage("You are the console!");

                return true;

            }

        }

        return false;
    }
}
