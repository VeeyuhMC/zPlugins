package zplugin.znocrit.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import zplugin.znocrit.zNoCrit;

public class zCommandExecutor implements CommandExecutor {

    zNoCrit plugin;

    public zCommandExecutor(zNoCrit plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("znocrit")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("server")) {
                    return wholeServerToggle(sender);
                } else {
                    World world = Bukkit.getWorld(args[0]);
                    if (world == null) {
                        sender.sendMessage("§4Invalid Arguments!");
                    } else {
                        return worldToggle(sender, world);
                    }
                }
            }
        }

        return false;
    }

    public boolean wholeServerToggle(CommandSender sender) {

        if (sender.hasPermission("znocrit.wholeserver")) {
            boolean enabled = plugin.getConfig().getBoolean("whole-server-enabled");
            enabled = (enabled ? false : true);
            plugin.getConfig().set("whole-server-enabled", enabled);
            plugin.fixConfig();
            sender.sendMessage((enabled ? "§Enabled critical hits for the whole server"
                    : "§6Disabled critical hits for the whole server"));
        } else {
            sender.sendMessage("§4You do not have permission to do that!");
        }

        return true;

    }

    public boolean worldToggle(CommandSender sender, World world) {

        if (sender.hasPermission("znocrit.worlds")) {
            boolean enabled = plugin.getConfig().getBoolean("worlds." + world.getName());
            enabled = (enabled ? false : true);
            plugin.getConfig().set("worlds." + world.getName(), enabled);
            if (plugin.getConfig().getBoolean("whole-server-enabled")) {
                plugin.getConfig().set("whole-server-enabled", false);
            }
            plugin.fixConfig();
        }

        return true;

    }

}
