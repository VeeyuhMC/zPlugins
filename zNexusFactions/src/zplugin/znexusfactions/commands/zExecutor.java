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

                }

            }
            
        }

        return false;

    }

}
