package com.zp4rker.znexusfactions.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.zp4rker.znexusfactions.zNexusFactions;
import com.zp4rker.znexusfactions.api.Base;
import com.zp4rker.znexusfactions.api.Faction;
import com.zp4rker.znexusfactions.api.FactionData;
import com.zp4rker.znexusfactions.api.Nexus;
import com.zp4rker.znexusfactions.api.Vault;
import com.zp4rker.znexusfactions.events.CreateFactionEvent;

public class BlockPlaceListener implements Listener {

    private zNexusFactions plugin;

    public BlockPlaceListener(zNexusFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
    	//Check if the player is holding a nexus
        if (isNexus(e.getBlock(), e.getPlayer())) {
        	// Get the location of the placed block
            Location block = e.getBlock().getLocation();
            // Get the player
            Player player = e.getPlayer();
            // Get the name of the faction to create
            String name = plugin.v.makingFaction.get(player).keySet().toArray()[0].toString();
            // Get the tag of the faction to create
            String tag = plugin.v.makingFaction.get(player).get(name);

            // Make Event
            CreateFactionEvent event = new CreateFactionEvent(name, tag, player, block);
            // Call Event
            plugin.getServer().getPluginManager().callEvent(event);

            // Run Default Code
            if (!event.isCancelled()) {
            	// Check if there is a faction within 9 blocks
                try {
                    plugin.m.getNearestFactionData(player, 9).getName();
                } catch (NullPointerException ex) {
                	// Send the player an error message
                    player.sendMessage("§4You cannot create a faction here!");
                    return;
                }

                // Remove Block
                block.getBlock().setType(Material.AIR);
                // Make Nexus
                Nexus nexus = new Nexus(block, false);
                // Make Vault
                Vault vault = nexus.createVault(e.getPlayer());
                // Make Base
                Base base = vault.createBase(e.getPlayer());
                List<UUID> players = new ArrayList<>();
                players.add(player.getUniqueId());
                // Make Faction
                Faction faction = new Faction(event.getName(), event.getTag(), players, base, players, true);
                // Make new Database Record
                FactionData factionData = new FactionData();
                // Set the Faction
                factionData.setFaction(faction);
                // Save the record to the Database
                plugin.getDatabase().save(factionData);
                // Send Message to console
                plugin.getLogger().info("Added Faction " + faction.getName() + " to database!");
                // Send Message to player
                player.sendMessage(event.getMessage());
                // Remove player from ArrayList
                plugin.v.makingFaction.remove(player);

            }

        }

    }

    public boolean isNexus(Block block, Player player) {
        return (block.getType() == Material.EMERALD_BLOCK &&
                player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§5Nexus"));
    }

}