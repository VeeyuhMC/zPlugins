package com.theretronix.hubp;

import com.theretronix.hubp.api.BungeeMessenger;
import com.theretronix.hubp.api.config.ConfigManager;
import com.theretronix.hubp.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HUB extends JavaPlugin {

    public static ConfigManager manager;
    public static boolean coreInstalled = false;

    public void onEnable() {

        if (getServer().getPluginManager().getPlugin("zCore").isEnabled()) {
            coreInstalled = true;
        }

        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord",
                new BungeeMessenger(this));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        registerEvents(
                new PlayerInteract(),
                new InventoryClick(),
                new PlayerJoin(),
                new PlayerLeave(),
                new PlayerMove(),
                new ToggleFlight(),
                new PlayerDamage());

        manager =  new ConfigManager(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                if (getServer().getOnlinePlayers().size() > 0) {
                    BungeeMessenger.requestCount("Factions");
                    BungeeMessenger.requestCount("Prison");
                    BungeeMessenger.requestCount("Arcade");
                    BungeeMessenger.requestCount("Rust");
                }
            }

        }, 0, 600);

    }

    public void onDisable() {
        //Do Nothing
    }

    public void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

}
