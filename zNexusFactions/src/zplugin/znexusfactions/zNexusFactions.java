package zplugin.znexusfactions;

import org.bukkit.plugin.java.JavaPlugin;
import zplugin.znexusfactions.api.FactionData;
import zplugin.znexusfactions.api.Methods;
import zplugin.znexusfactions.api.Variables;
import zplugin.znexusfactions.commands.zExecutor;
import zplugin.znexusfactions.listeners.BlockPlaceListener;
import zplugin.znexusfactions.listeners.DamageByEntityListener;
import zplugin.znexusfactions.listeners.PlayerChatListener;
import zplugin.znexusfactions.listeners.PlayerMoveListener;

import java.util.ArrayList;
import java.util.List;

public class zNexusFactions extends JavaPlugin {

    public Methods m = new Methods(this);
    public Variables v = new Variables();

    public void onEnable() {

        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);

        getCommand("zfactions").setExecutor(new zExecutor(this));

        saveDefaultConfig();

        setupDatabase();

        loadFactions();

        getLogger().info("zNexusFactions v" + getDescription().getVersion() + " enabled!");

    }

    private void loadFactions() {
        int i = 0;
        for (FactionData factionData : getDatabase().find(FactionData.class).findSet()) {
        	factionData.getName();
            i++;
        }
        getLogger().info("Loaded " + i + (i != 1 ? " factions!" : " faction!"));
    }

    public void setupDatabase() {
        try {
            getDatabase().find(FactionData.class).findRowCount();
        } catch (Exception e) {
            getLogger().info("Installing database due to first time usage...");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<>();
        list.add(FactionData.class);
        return list;
    }

}
