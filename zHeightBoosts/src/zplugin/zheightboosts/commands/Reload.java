package zplugin.zheightboosts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import zplugin.zheightboosts.zHeightBoosts;

public class Reload implements CommandExecutor {

    private zHeightBoosts plugin;

    public Reload(zHeightBoosts plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("zhb")) {
            if (sender.hasPermission("zheightboosts.admin")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        plugin.getPluginLoader().disablePlugin(plugin);
                        plugin.getPluginLoader().enablePlugin(plugin);
                        sender.sendMessage("§2Reloaded zHeightBoosts!");
                        return true;
                    } else {
                        sender.sendMessage("§4Invalid Arguments!");
                    }
                } else {
                    sender.sendMessage("§4Invalid Arguments!");
                }
            } else {
                sender.sendMessage("§4You do not have permission to do that!");
                return true;
            }
        }

        return false;
    }

}
