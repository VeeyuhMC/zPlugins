package zplugin.zheightboosts;

import org.bukkit.plugin.java.JavaPlugin;
import zplugin.zheightboosts.commands.Reload;
import zplugin.zheightboosts.listeners.PlayerMoveListener;
import zplugin.zheightboosts.util.Methods;

public class zHeightBoosts extends JavaPlugin {

    public Methods m = new Methods(this);

    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);

        getCommand("zhb").setExecutor(new Reload(this));

    }

}
