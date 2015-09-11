package zplugin.znocrit;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import zplugin.znocrit.command.zCommandExecutor;
import zplugin.znocrit.listeners.PlayerAttackListener;
import zplugin.znocrit.listeners.PlayerFallListener;

public class zNoCrit extends JavaPlugin {

    public void onEnable() {

        fixConfig();

        getCommand("znocrit").setExecutor(new zCommandExecutor(this));

        getServer().getPluginManager().registerEvents(new PlayerFallListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerAttackListener(), this);

    }

    public void fixConfig() {

        if (getConfig().get("whole-server-enabled") == null) {

            getConfig().set("whole-server-enabled", true);

            for (World world : getServer().getWorlds()) {
                getConfig().set("worlds." + world.getName(), true);
            }

            saveConfig();

        }

        if (getConfig().getBoolean("whole-server-enabled")) {

            for (World world : getServer().getWorlds()) {
                getConfig().set("worlds." + world.getName(), true);
            }

            saveConfig();

        }

        if (!getConfig().getBoolean("whole-server-enabled")) {

            saveConfig();

        }

        reloadConfig();

    }

    public void onDisable() {

        fixConfig();

    }

}
