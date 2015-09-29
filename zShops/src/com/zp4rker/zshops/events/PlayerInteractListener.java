package com.zp4rker.zshops.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

@SuppressWarnings("unused")
public class PlayerInteractListener implements Listener {

    public void onPlayerInteract(PlayerInteractEvent e) {

        Player player = e.getPlayer();

    }

}
