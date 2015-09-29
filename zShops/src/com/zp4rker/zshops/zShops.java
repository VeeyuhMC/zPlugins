package com.zp4rker.zshops;

import com.zp4rker.zshops.events.PlayerInteractListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class zShops extends JavaPlugin {

    public void onEnable() {

        registerEvents(this, new PlayerInteractListener());

    }

    public void registerEvents(JavaPlugin plugin, Listener... listeners) {
        for(Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

}
