package com.zp4rker.testplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class TheEventClass implements Listener {

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {

        event.getPlayer().sendMessage("You just placed a block of " + event.getBlock().getType().toString());

    }

}
