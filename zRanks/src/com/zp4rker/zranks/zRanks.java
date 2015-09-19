package com.zp4rker.zranks;

import com.zp4rker.zranks.commands.*;
import com.zp4rker.zranks.config.Config;
import com.zp4rker.zranks.config.ConfigManager;
import com.zp4rker.zranks.events.PlayerChatListener;
import com.zp4rker.zranks.events.PlayerJoinListener;
import com.zp4rker.zranks.util.Methods;
import com.zp4rker.zranks.util.Perm;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class zRanks extends JavaPlugin {

    public Methods m;

    public void onEnable() {

        ConfigManager manager = new ConfigManager(this);
        Config ranks;

        m = new Methods(this);

        getLogger().info("zRanks enabled!");
        new Perm(this).reloadPerms();

        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        getCommand("setrank").setExecutor(new SetRankCommand(this));
        getCommand("who").setExecutor(new WhoCommand(this));
        getCommand("staff").setExecutor(new StaffCommand(this));
        getCommand("promote").setExecutor(new PromoteCommand(this));
        getCommand("demote").setExecutor(new DemoteCommand(this));

        for (World world : getServer().getWorlds()) {
            ranks = manager.getNewConfig(world.getName() + "/ranks.yml");
            if (ranks.get("ranks") == null) {
                ranks.saveDefaultConfig();
            }
        }

        saveDefaultConfig();

    }

    public void onDisable() {

        getLogger().info("zRanks disabled!");
        Perm.resetPerms();

    }

    public void saveResource(String resourcePathIn, String resourcePathOut, boolean replace) {
        if (resourcePathIn == null || resourcePathIn.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePathIn = resourcePathIn.replace('\\', '/');
        InputStream in = getResource(resourcePathIn);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePathIn + "' cannot be found in " + getFile());
        }

        File outFile = new File(getDataFolder(), resourcePathOut);
        int lastIndex = resourcePathIn.lastIndexOf('/');
        File outDir = new File(getDataFolder(), resourcePathIn.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

}
