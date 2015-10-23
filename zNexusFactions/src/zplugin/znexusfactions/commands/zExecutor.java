package zplugin.znexusfactions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zplugin.znexusfactions.zNexusFactions;

public class zExecutor implements CommandExecutor {

    private zNexusFactions plugin;

    public zExecutor(zNexusFactions plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("zfactions")) {

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("create")) {

                    if (sender instanceof Player) {

                        return Create.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("join")) {

                    if (sender instanceof Player) {

                        return Join.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("leave")) {

                    if (sender instanceof Player) {

                        return Leave.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("near")) {

                    if (sender instanceof Player) {

                        return Near.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("disband")) {

                    if (sender instanceof Player) {

                        return Disband.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("chat")) {

                    if (sender instanceof Player) {

                        return Chat.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("broadcast")) {

                    if (sender instanceof Player) {

                        return Broadcast.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("info")) {

                    return Info.command(sender, args, plugin);

                } else if (args[0].equalsIgnoreCase("reload")) {

                    return Reload.command(sender, args, plugin);

                }

            }

        }

        return false;

    }

}