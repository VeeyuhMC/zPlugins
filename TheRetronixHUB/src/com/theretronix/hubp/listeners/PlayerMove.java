package com.theretronix.hubp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerMove implements Listener {

    public static List<Player> inAir = new ArrayList<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (event.getPlayer().getVelocity().getY() > 0) {

            if (!inAir.contains(event.getPlayer())) {

                inAir.add(event.getPlayer());

                event.getPlayer().setAllowFlight(true);

            }

        } else if (event.getPlayer().getVelocity().getY() == 0) {

            if (inAir.contains(event.getPlayer())) {

                inAir.add(event.getPlayer());

                event.getPlayer().setAllowFlight(true);

            }
        }

    }

}
