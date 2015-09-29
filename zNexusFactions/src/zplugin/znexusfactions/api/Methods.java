package zplugin.znexusfactions.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zplugin.znexusfactions.zNexusFactions;

public class Methods {

    zNexusFactions plugin;

    public Methods(zNexusFactions plugin) {
        this.plugin = plugin;
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
            if (factionData.getFaction().getBase().getArea().contains(location)) {
                return true;
            }
        }
        return false;
    }

    public Faction getFactionAtLocation(Location location) {
        for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findList()) {
            if (factionData.getFaction().getBase().getArea().contains(location)) {
                return factionData.getFaction();
            }
        }
        return null;
    }

}
