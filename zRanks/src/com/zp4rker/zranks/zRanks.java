package com.zp4rker.zranks;

import com.zp4rker.zranks.commands.SetRankCommand;
import com.zp4rker.zranks.commands.StaffCommand;
import com.zp4rker.zranks.commands.WhoCommand;
import com.zp4rker.zranks.events.PlayerChatListener;
import com.zp4rker.zranks.events.PlayerJoinListener;
import com.zp4rker.zranks.util.Methods;
import com.zp4rker.zranks.util.Perm;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class zRanks extends JavaPlugin {

    public Methods m;

    public void onEnable() {

        m = new Methods(this);

        getLogger().info("zRanks enabled!");
        new Perm(this).reloadPerms();

        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        getCommand("setrank").setExecutor(new SetRankCommand(this));
        getCommand("who").setExecutor(new WhoCommand(this));
        getCommand("staff").setExecutor(new StaffCommand(this));

    }

    public void onDisable() {

        getLogger().info("zRanks disabled!");
        Perm.resetPerms();

    }

}
