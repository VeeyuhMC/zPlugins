package zplugin.znexusfactions.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zplugin.znexusfactions.api.*;
import zplugin.znexusfactions.events.CreateFactionEvent;
import zplugin.znexusfactions.zNexusFactions;

import java.util.ArrayList;
import java.util.List;

public class BlockPlaceListener implements Listener {

    private zNexusFactions plugin;

    public BlockPlaceListener(zNexusFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (isNexus(e.getBlock(), e.getPlayer())) {

            Location block = e.getBlock().getLocation();

            Player player = (Player) plugin.v.makingFaction.keySet().toArray()[0];
            String name = plugin.v.makingFaction.get(player).keySet().toArray()[0].toString();
            String tag = plugin.v.makingFaction.get(player).get(name);

            // Make Event
            CreateFactionEvent event = new CreateFactionEvent(name, tag, player, block);
            // Call Event
            plugin.getServer().getPluginManager().callEvent(event);

            // Run Default Code
            if (!event.isCancelled()) {

                /*try {
                    plugin.m.getNearestFactionData(player, 9).getName();
                } catch (NullPointerException ex) {
                    player.sendMessage("§4You cannot create a faction here!");
                    return;
                }*/

                // Remove Block
                block.getBlock().setType(Material.AIR);
                // Make Nexus
                Nexus nexus = new Nexus(block, false);
                // Make Vault
                Vault vault = nexus.createVault(e.getPlayer());
                // Make Base
                Base base = vault.createBase(e.getPlayer());
                List<OfflinePlayer> players = new ArrayList<>();
                players.add(player);
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

/*

// Remove Block
block.getBlock().setType(Material.AIR);
// Make Nexus
Nexus nexus = new Nexus(block, false);
// Make Vault
Vault vault = nexus.createVault(e.getPlayer());
// Make Base
Base base = vault.createBase(e.getPlayer());
List<OfflinePlayer> players = new ArrayList<>();
players.add(player);
// Make Faction
Faction faction = new Faction(event.getName(), event.getTag(), players, base, players, true);
// Make new Database Record
FactionData factionData = new FactionData();
// Set the Faction
factionData.setFaction(faction);
// Save the record to the Database
plugin.getDatabase().save(factionData);
plugin.getLogger().info("Added Faction " + faction.getName() + " to database!");
player.sendMessage(event.getMessage());
// Remove player from ArrayList
plugin.v.makingFaction.remove(player);

 */
