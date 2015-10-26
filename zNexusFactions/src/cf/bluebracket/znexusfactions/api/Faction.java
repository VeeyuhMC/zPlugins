package cf.bluebracket.znexusfactions.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cf.bluebracket.znexusfactions.events.DisbandFactionEvent;
import cf.bluebracket.znexusfactions.events.JoinFactionEvent;
import cf.bluebracket.znexusfactions.events.LeaveFactionEvent;

public class Faction {

    private String name;
    private String tag;
    private List<OfflinePlayer> players = new ArrayList<>();
    private List<OfflinePlayer> staff = new ArrayList<>();
    private Base base;
    private boolean open;

    public Faction(String name, String tag, List<OfflinePlayer> players, Base base, List<OfflinePlayer> staff, boolean open) {

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

    public List<OfflinePlayer> getPlayers() {
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
            players.add(player);

            // Send Message to Player
            player.sendMessage(event.getPlayerMessage());

            // Send Message to Faction
            for (OfflinePlayer op : this.players) {
                Player p = Bukkit.getPlayer(op.getUniqueId());
                if (p != null) {
                    p.sendMessage(event.getFactionMessage());
                }
            }

            // Add New Outline
            for (int i = 0; i < this.base.getOutline(players.size()).size(); i++) {
                final int I = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Methods.getPlugin(), new Runnable() {
                    public void run() {
                        base.getOutline(players.size()).get(I).getBlock().setType(Material.STONE);
                    }
                }, i * 1);
            }

            // Save to database
            FactionData factionData = Methods.getPlugin().getDatabase().find(FactionData.class)
                    .where().ieq("name", this.name).findUnique();
            factionData.addPlayer(player);

        }
    }

    public void removePlayer(Player player) {
        // Create Event
        LeaveFactionEvent event = new LeaveFactionEvent(player, this);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {

            // Remove player
            players.remove(player);

            // Send Message to Player
            player.sendMessage(event.getPlayerMessage());

            // Send Message to Faction
            for (OfflinePlayer op : this.players) {
                Player p = Bukkit.getPlayer(op.getUniqueId());
                if (p != null) {
                    p.sendMessage(event.getFactionMessage());
                }
            }

            // Add New Outline
            for (int i = 0; i < this.base.getOutline(this.players.size()).size(); i++) {
                final int I = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Methods.getPlugin(), new Runnable() {
                    public void run() {
                        base.getOutline(players.size()).get(I).getBlock().setType(Material.STONE);
                    }
                }, i * 1);
            }

            // Save to database
            FactionData factionData = Methods.getPlugin().getDatabase().find(FactionData.class)
                    .where().ieq("name", this.name).findUnique();
            factionData.removePlayer(player);

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
        return players.get(0);
    }

    public List<OfflinePlayer> getStaff() {
        return staff;
    }

    public void addStaff(Player player) {
        staff.add(player);
    }

    public void removeStaff(Player player) {
        staff.remove(player);
    }

    public void disband(CommandSender sender) {
        // Make Event
        DisbandFactionEvent event = new DisbandFactionEvent(sender, this);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {
            // Get Database Entry
            FactionData factionData = Methods.getPlugin().getDatabase().find(FactionData.class)
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