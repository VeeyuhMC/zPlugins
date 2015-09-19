package com.zp4rker.zranks.events;

import com.zp4rker.zranks.config.Config;
import com.zp4rker.zranks.config.ConfigManager;
import com.zp4rker.zranks.zRanks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


@SuppressWarnings("unused")
public class PlayerJoinListener implements Listener {

    zRanks plugin;
    ConfigManager manager;

    public PlayerJoinListener(zRanks plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config ranks = manager.getNewConfig(player.getWorld().getName() + "/ranks.yml");
        String rank = ranks.getString("players." + player.getUniqueId() + ".rank");
        if (rank == null) {
            ranks.set("players." + player.getUniqueId() + ".rank", plugin.m.getDefaultRank());
        }
    }

}
