package com.zp4rker.znexusfactions.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.zp4rker.znexusfactions.events.DisbandFactionEvent;
import com.zp4rker.znexusfactions.events.JoinFactionEvent;
import com.zp4rker.znexusfactions.events.LeaveFactionEvent;
import com.zp4rker.znexusfactions.zNexusFactions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Faction {

    private String name;
    private String tag;
    private List<UUID> players = new ArrayList<>();
    private List<UUID> staff = new ArrayList<>();
    private Base base;
    private boolean open;

    public Faction(String name, String tag, List<UUID> players, Base base, List<UUID> staff, boolean open) {

        this.name = name;
        this.tag = tag;
        this.players = players;
        this.base = base;
        this.open = open;
        this.staff = staff;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(Player player) {
        // Make Event
        JoinFactionEvent event = new JoinFactionEvent(player, this);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {

            // Add Player
            players.add(player.getUniqueId());

            // Send Message to Player
            player.sendMessage(event.getPlayerMessage());

            // Send Message to Faction
            for (UUID uuid : this.players) {
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    p.sendMessage(event.getFactionMessage());
                }
            }

            // Add New Outline
            for (int i = 0; i < this.base.getOutline(players.size()).size(); i++) {
                final int I = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(zNexusFactions.getPlugin(), new Runnable() {
                    public void run() {
                        base.getOutline(players.size()).get(I).getBlock().setType(Material.STONE);
                    }
                }, i * 1);
            }

            // Save to database
            FactionData factionData = zNexusFactions.getPlugin().getDatabase().find(FactionData.class)
                    .where().ieq("name", this.name).findUnique();
            factionData.addPlayer(player);

        }
    }

    public void removePlayer(OfflinePlayer offlinePlayer, boolean kicked) {
        // Create Event
        LeaveFactionEvent event = new LeaveFactionEvent(offlinePlayer, this, kicked);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {

            // Remove player
            players.remove(offlinePlayer);

            if (event.playerIsOnline()) {
            	// Send Message to Player
            	Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
                player.sendMessage(event.getPlayerMessage());
            }

            // Send Message to Faction
            for (UUID uuid : this.players) {
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    p.sendMessage(event.getFactionMessage());
                }
            }

            // Add New Outline
            for (int i = 0; i < this.base.getOutline(this.players.size()).size(); i++) {
                final int I = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(zNexusFactions.getPlugin(), new Runnable() {
                    public void run() {
                        base.getOutline(players.size()).get(I).getBlock().setType(Material.STONE);
                    }
                }, i * 1);
            }

            // Save to database
            FactionData factionData = zNexusFactions.getPlugin().getDatabase().find(FactionData.class)
                    .where().ieq("name", this.name).findUnique();
            factionData.removePlayer(offlinePlayer);

        }
    }

    public Base getBase() {
        return base;
    }

    public Vault getVault() {
        return base.getVault();
    }

    public Nexus getNexus() {
        return base.getVault().getNexus();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public OfflinePlayer getOwner() {
        return Bukkit.getPlayer(players.get(0));
    }

    public List<UUID> getStaff() {
        return staff;
    }

    public void addStaff(OfflinePlayer player) {
        staff.add(player.getUniqueId());
    }

    public void removeStaff(OfflinePlayer player) {
        staff.remove(player.getUniqueId());
    }

    public void disband(CommandSender sender) {
        // Make Event
        DisbandFactionEvent event = new DisbandFactionEvent(sender, this);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {
            // Get Database Entry
            FactionData factionData = zNexusFactions.getPlugin().getDatabase().find(FactionData.class)
                    .where().ieq("name", this.name).findUnique();
            // Send the player a message
            sender.sendMessage(event.getSenderMessage());
            for (OfflinePlayer offlinePlayer : factionData.getBukkitPlayers()) {
                // Send all other online faction members a message
                Player factionPlayer = Bukkit.getPlayer(offlinePlayer.getUniqueId());
                if (factionPlayer != null) {
                    factionPlayer.sendMessage(event.getFactionMessage());
                }
            }
            // Send the server a message
            Bukkit.broadcastMessage(event.getServerMessage());
        }
    }

}