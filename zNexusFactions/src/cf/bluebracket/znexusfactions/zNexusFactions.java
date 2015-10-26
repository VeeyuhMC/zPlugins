package cf.bluebracket.znexusfactions;

import org.bukkit.plugin.java.JavaPlugin;

import cf.bluebracket.znexusfactions.api.FactionData;
import cf.bluebracket.znexusfactions.api.Methods;
import cf.bluebracket.znexusfactions.api.Variables;
import cf.bluebracket.znexusfactions.commands.zExecutor;
import cf.bluebracket.znexusfactions.listeners.BlockPlaceListener;
import cf.bluebracket.znexusfactions.listeners.DamageByEntityListener;
import cf.bluebracket.znexusfactions.listeners.PlayerChatListener;
import cf.bluebracket.znexusfactions.listeners.PlayerMoveListener;

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