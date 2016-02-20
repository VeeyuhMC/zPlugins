package com.theretronix.hubp.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class ToggleFlight implements Listener {

    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent event) {

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {

            event.setCancelled(true);

            event.getPlayer().setAllowFlight(false);

            event.getPlayer().setVelocity(
                    event.getPlayer().getLocation().getDirection().setY(.5).multiply(2)
            );

        }

    }

}
