package zplugin.znexusfactions.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zplugin.znexusfactions.api.*;
import zplugin.znexusfactions.zNexusFactions;

import java.util.ArrayList;
import java.util.List;

public class BlockPlaceListener implements Listener {

    private zNexusFactions plugin;

    public BlockPlaceListener(zNexusFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if (isNexus(event.getBlock(), event.getPlayer())) {

            Location block = event.getBlock().getLocation();

            // Nexus Generation
            block.getBlock().setType(Material.AIR);

            Nexus nexus = new Nexus(block);
            Vault vault = nexus.createVault(event.getPlayer());
            Base base = vault.createBase(event.getPlayer());
            Player player = (Player) Variables.makingFaction.keySet().toArray()[0];
            List<Player> players = new ArrayList<>();
            players.add(player);
            Faction faction = new Faction(Variables.makingFaction.get(player).keySet().toArray()[0].toString(),
                    Variables.makingFaction.get(player).get(Variables.makingFaction.get(player).keySet().toArray()[0].toString()),
                    players, base);

            FactionData factionData = new FactionData();
            factionData.setFaction(faction);
            factionData.setLocation(faction.getNexus().getLocation());
            plugin.getDatabase().save(factionData);
            plugin.getLogger().info("Added Faction " + faction.getName() + " to database!");
            Variables.makingFaction.remove(player);

        } else {
            ItemStack stack = new ItemStack(Material.EMERALD_BLOCK);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName("§5Nexus");
            stack.setItemMeta(meta);
            event.getPlayer().getInventory().addItem(stack);
        }

    }

    public boolean isNexus(Block block, Player player) {
        return (block.getType() == Material.EMERALD_BLOCK &&
                player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§5Nexus"));
    }

}
