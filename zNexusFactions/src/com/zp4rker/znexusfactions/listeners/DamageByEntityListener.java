package com.zp4rker.znexusfactions.listeners;

import com.zp4rker.znexusfactions.api.FactionData;
import com.zp4rker.znexusfactions.api.Nexus;
import com.zp4rker.znexusfactions.zNexusFactions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class DamageByEntityListener implements Listener {

    private zNexusFactions plugin;

    public DamageByEntityListener() {
        this.plugin = zNexusFactions.getPlugin();
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {

        // Check if hit entity is an ender crystal
        if (event.getEntity().getType() == EntityType.ENDER_CRYSTAL) {

            // Disable explosion
            event.setCancelled(true);

            // Get the nexus' faction
            FactionData dFaction = plugin.m.getFactionAtLocation(event.getEntity().getLocation());

            // Make sure the attacker is a player
            if (event.getEntity() instanceof Player) {

                // Get the attacker
                Player attacker = (Player) event.getEntity();

                // Get faction of attacker
                FactionData aFaction = plugin.m.getFaction(attacker);

                // Make sure the attacker is in a faction
                if (aFaction != null) {

                    // Check if the attacker is in the defending faction
                    if (aFaction == dFaction) {

                        // Check if faction owner
                        if (aFaction.getFaction().getOwner().getUniqueId() == attacker.getUniqueId()) {

                            // Add them to the list
                            plugin.v.hitNexus.add(attacker);

                            // Ask for confirmation
                            attacker.sendMessage("§6Hit the nexus again to confirm");

                            // Get confirmation countdown
                            int countDown = plugin.getConfig().getInt("nexus-hit-confirm-countdown");

                            // Schedule a delayed task
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {

                                    // If attacker hasn't confirmed yet
                                    if (plugin.v.hitNexus.contains(attacker)) {

                                        // Remove attacker from list
                                        plugin.v.hitNexus.remove(attacker);

                                    }

                                }
                            }, 20 * countDown);

                        } else {

                            // Tell attacker need to be owner
                            attacker.sendMessage("§4You must be the owner of the faction!");

                        }

                    } else {

                        // Get nexus
                        Nexus nexus = dFaction.getFaction().getNexus();
                        // Get nexus' health
                        double health = nexus.getHealth();
                        // Get attack damage
                        double damage = event.getDamage();
                        // Minus damage from health
                        health -= damage;
                        // Check if the nexus is dead
                        if (health <= 0) {

                            // Start grace period

                        } else { // Show the health
                            // Create a list of all players from both factions
                            List<Player> players = new ArrayList<>();
                            // Loop through all online players of factions
                            for (OfflinePlayer oPlayer : aFaction.getBukkitPlayers()) { // Attacking faction players
                                if (oPlayer.isOnline()) {
                                    Player player = oPlayer.getPlayer();
                                    players.add(player);
                                }
                            }
                            for (OfflinePlayer oPlayer : dFaction.getBukkitPlayers()) { // Defending faction players
                                if (oPlayer.isOnline()) {
                                    Player player = oPlayer.getPlayer();
                                    players.add(player);
                                }
                            }
                            // Show the health to the online players
                            nexus.showHealth(players);
                        }

                    }

                }

            }

        }

    }

}