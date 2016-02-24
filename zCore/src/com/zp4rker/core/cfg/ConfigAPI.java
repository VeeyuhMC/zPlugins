package com.zp4rker.core.cfg;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAPI {

    private JavaPlugin plugin;
    private ConfigManager manager;

    public ConfigAPI(JavaPlugin plugin) {

        this.plugin = plugin;
        this.manager = new ConfigManager(plugin);

    }

    /**
     *
     * @param file The location of the file, relative to /plugins/plugin_name/ as a string
     * @return Config
     *
     */
    public Config getConfig(String file) {

        return manager.getNewConfig(file);

    }

}
