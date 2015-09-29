package com.zp4rker.zgamemode.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeChangeListener implements Listener {

    @EventHandler
    public void onGMChange(PlayerGameModeChangeEvent event) {

        Player player = event.getPlayer();

        player.sendMessage("§2You are now in " + event.getNewGameMode().toString());

    }

}
