package com.zp4rker.zranks.events;

import com.zp4rker.zranks.zRanks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@SuppressWarnings("unused")
public class PlayerChatListener implements Listener {

    zRanks plugin;

    public PlayerChatListener(zRanks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String displayName = plugin.m.getFullDisplayName(player);
        event.setFormat(displayName + " §7> §r" + event.getMessage());
    }

}
