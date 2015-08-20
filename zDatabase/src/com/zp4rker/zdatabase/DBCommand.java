package com.zp4rker.zdatabase;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class DBCommand implements CommandExecutor {

    private final zDatabase plugin;

    public DBCommand(zDatabase plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("db")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("set")) {
                        String name = args[1];
                        String value = args[2];
                        DB DBClass = plugin.getDatabase().find(DB.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();
                        if(DBClass == null) {
                            DBClass = new DB();
                            DBClass.setName(name);
                            DBClass.setPlayerName(player.getName());
                        }
                        DBClass.setValue(value);
                        player.sendMessage("Process Done!");
                        plugin.getDatabase().save(DBClass);
                        return true;
                    } else {
                        player.sendMessage("Error!");
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("get")) {
                        String name = args[1];
                        DB DBClass;
                        DBClass = plugin.getDatabase().find(DB.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();
                        if(DBClass == null) {
                            player.sendMessage("Error!");
                            return true;
                        }
                        player.sendMessage("Name: " + DBClass.getName());
                        player.sendMessage("PlayerName: " + DBClass.getPlayerName());
                        player.sendMessage("Value: " + DBClass);
                        return true;
                    } else {
                        player.sendMessage("Error!");
                    }
                } else {
                    player.sendMessage("Error!");
                }
            } else if(sender instanceof ConsoleCommandSender) {
                if(args.length == 4) {
                    if(args[0].equalsIgnoreCase("set")) {
                        String playerName = args[1];
                        String name = args[2];
                        String value = args[3];
                        DB DBClass = plugin.getDatabase().find(DB.class).where().ieq("name", name).ieq("playerName", playerName).findUnique();
                        if(DBClass == null) {
                            DBClass = new DB();
                            DBClass.setPlayerName(playerName);
                            DBClass.setName(name);
                        }
                        DBClass.setValue(value);
                        plugin.getDatabase().save(DBClass);
                        sender.sendMessage("Process Done!");
                    }
                } else if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("get")) {
                        String playerName = args[1];
                        String name = args[2];
                        DB DBClass = plugin.getDatabase().find(DB.class).where().ieq("playerName", playerName).ieq("name", name).findUnique();
                        if(DBClass == null) {
                            sender.sendMessage("Error!");
                            return true;
                        }
                        sender.sendMessage("PlayerName: " + playerName);
                        sender.sendMessage("Name: " + name);
                        sender.sendMessage("Value: " + DBClass.getValue());
                    }
                }
            }
        }
        return false;
    }

}
