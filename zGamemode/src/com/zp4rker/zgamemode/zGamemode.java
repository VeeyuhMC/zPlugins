package com.zp4rker.zgamemode;

import com.zp4rker.zgamemode.commands.Adventure;
import com.zp4rker.zgamemode.commands.Creative;
import com.zp4rker.zgamemode.commands.Survival;
import com.zp4rker.zgamemode.listeners.GameModeChangeListener;
import org.bukkit.plugin.java.JavaPlugin;

public class zGamemode extends JavaPlugin {

    public void onEnable() {

        getCommand("creative").setExecutor(new Creative());
        getCommand("survival").setExecutor(new Survival());
        getCommand("adventure").setExecutor(new Adventure());

        getServer().getPluginManager().registerEvents(new GameModeChangeListener(), this);

    }

}
