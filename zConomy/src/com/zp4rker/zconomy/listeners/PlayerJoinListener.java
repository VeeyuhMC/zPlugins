package com.zp4rker.zconomy.listeners;

import com.zp4rker.zconomy.PlayerData;
import com.zp4rker.zconomy.zConomy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    zConomy plugin;

    public PlayerJoinListener(zConomy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where()
                .ieq("playerUUID", player.getUniqueId().toString()).findUnique();

        if (playerData == null) {

            playerData = new PlayerData();
            playerData.setPlayerUUID(player.getUniqueId().toString());
            playerData.setMoney(0);

            plugin.getDatabase().save(playerData);
            plugin.getLogger().info("Added player " + player.getName() + " to database.");

        }

    }

}
