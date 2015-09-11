package com.zp4rker.zshops.events;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

@SuppressWarnings("unused")
public class SignChangeListener implements Listener {

    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();

        Sign sign = (Sign) e.getBlock().getState();

        if(sign.getLine(0).equalsIgnoreCase("[zShop]")) {

        }

    }

    public void makeError(Sign sign, Player player) {
    }

}
