package com.zp4rker.znexusfactions;

import com.zp4rker.znexusfactions.api.FactionData;
import com.zp4rker.znexusfactions.listeners.BlockPlaceListener;
import com.zp4rker.znexusfactions.listeners.DamageByEntityListener;
import com.zp4rker.znexusfactions.listeners.PlayerChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.zp4rker.znexusfactions.api.Methods;
import com.zp4rker.znexusfactions.api.Variables;
import com.zp4rker.znexusfactions.commands.zExecutor;
import com.zp4rker.znexusfactions.listeners.PlayerMoveListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class zNexusFactions extends JavaPlugin {

    public Methods m = new Methods(this);
    public Variables v = new Variables();
    private static zNexusFactions plugin;
    private static HashMap<String, Object> apis = new HashMap<>();

    // Code to be run when the plugin is enabled
    public void onEnable() {

        this.plugin = this;

        // Check if zCore is installed
        if (!getServer().getPluginManager().isPluginEnabled("zCore")) {
            // Disable the plugin
            getLogger().warning("zCore is not installed! zNexusFactions cannot run!");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            // Register the events
            getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
            getServer().getPluginManager().registerEvents(new DamageByEntityListener(), this);
            getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
            getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
            // Register the command
            getCommand("zfactions").setExecutor(new zExecutor(this));
            // Load the default config
            saveDefaultConfig();
            // Setup the database
            setupDatabase();
            // Load the factions
            loadFactions();

            getLogger().info("zNexusFactions v" + getDescription().getVersion() + " enabled!");

            // Add APIs to the map
            apis.put("ConfigAPI", new com.zp4rker.core.cfg.ConfigAPI(this));
            apis.put("HologramAPI", new com.zp4rker.core.hg.HologramAPI());
        }

    }

    public void onDisable() {

        getLogger().info("Disabling zNexusFactions v" + getDescription().getVersion() + "!");

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

    public static zNexusFactions getPlugin() {

        return zNexusFactions.plugin;

    }

    public static com.zp4rker.core.cfg.ConfigAPI getConfigAPI() {
        return (com.zp4rker.core.cfg.ConfigAPI) apis.get("ConfigAPI");
    }

    public static com.zp4rker.core.hg.HologramAPI getHologramAPI() {
        return (com.zp4rker.core.hg.HologramAPI) apis.get("HologramAPI");
    }

}
