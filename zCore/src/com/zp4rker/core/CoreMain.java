package com.zp4rker.core;

import org.bukkit.plugin.java.JavaPlugin;

public class CoreMain extends JavaPlugin {

    private static CoreMain plugin;

    public void onEnable() {

        CoreMain.plugin = this;

    }

    public static CoreMain getPlugin() {

        return CoreMain.plugin;

    }

}