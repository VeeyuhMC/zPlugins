package com.zp4rker.zshops;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class zShops extends JavaPlugin {

    public void onEnable() {}

    public void registerEvents(JavaPlugin plugin, Listener... listeners) {
        for(Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

}
