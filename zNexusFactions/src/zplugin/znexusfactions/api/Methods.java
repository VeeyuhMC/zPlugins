package zplugin.znexusfactions.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zplugin.znexusfactions.zNexusFactions;

public class Methods {

    private zNexusFactions plugin;
    private static zNexusFactions sPlugin;

    public Methods(zNexusFactions plugin) {
        this.plugin = plugin;
        sPlugin = plugin;
    }

    public void giveNexus(Player player) {
        ItemStack itemStack = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§5Nexus");
        itemStack.setItemMeta(itemMeta);

        if (player.getItemInHand().getType() != Material.AIR) {
            player.sendMessage("§4Make sure there is nothing in your hand!");
        } else {
            player.getInventory().addItem(itemStack);
            player.sendMessage("§3Place this block to make your faction!");
        }

    }

    public boolean factionExists(String name) {

        FactionData factionData = plugin.getDatabase().find(FactionData.class)
                .where().ieq("name", name).findUnique();

        if (factionData != null) {
            return true;
        }

        return false;

    }

    public boolean invalidPerms(Player player) {
        player.sendMessage("§4You do not permission to do that!");
        return true;
    }

    public boolean inFactionBase(Location location) {
        for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {
            if (location.getBlockX() > factionData.getXOne() &&
                    location.getBlockX() < factionData.getXTwo()) {
                if (location.getBlockY() > factionData.getYOne() &&
                        location.getBlockY() < factionData.getYTwo()) {
                    if (location.getBlockZ() > factionData.getZOne() &&
                            location.getBlockZ() < factionData.getZTwo()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public FactionData getFactionAtLocation(Location location) {
        for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {
            if (location.getBlockX() > factionData.getXOne() &&
                    location.getBlockX() < factionData.getXTwo()) {
                if (location.getBlockY() > factionData.getYOne() &&
                        location.getBlockY() < factionData.getYTwo()) {
                    if (location.getBlockZ() > factionData.getZOne() &&
                            location.getBlockZ() < factionData.getZTwo()) {
                        return factionData;
                    }
                }
            }
        }
        return null;
    }

    public boolean isInFaction(Player player) {

        for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {
            if (factionData.getBukkitPlayers().contains(player)) {
                return true;
            }
        }

        return false;

    }

    public FactionData getFaction(Player player) {

        for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {

            if (factionData.getBukkitPlayers().contains(player)) {
                return factionData;
            }

        }

        return null;

    }

    public boolean isOwner(Player player) {
        Faction faction = getFaction(player).getFaction();
        if (faction.getOwner().getUniqueId() == player.getUniqueId()) {
            return true;
        } else {
            return false;
        }
    }

    public static zNexusFactions getPlugin() {
        return sPlugin;
    }

}
