package com.theretronix.hubp.api;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class JP {

    private static Plugin plugin;
    private Location location;
    private int strength;
    private JPData entry;

    public JP(Plugin plugin) {
        JP.plugin = plugin;
    }

    public JP(Location location, int strength) {
        this.location = location;
        this.strength = strength;
        this.entry = getEntryDB();
    }

    public static JP getJumpPadAt(Location location) {
        for (JPData jumpPad : plugin.getDatabase().find(JPData.class).findSet()) {
            if (jumpPad.getLocation() == location) {
                return jumpPad.getJumpPad();
            }
        }
        return null;
    }

    public static boolean isJumpPad(Location location) {
        for (JPData jumpPad : plugin.getDatabase().find(JPData.class).findSet()) {
            if (jumpPad.getLocation() == location) {
                return true;
            }
        }
        return false;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public JPData getEntry() {
        return entry;
    }

    public void setEntry(JPData entry) {
        this.entry = entry;
    }

    @SuppressWarnings("deprecation")
    public void jump(Player player) {
        player.playSound(player.getLocation(), Sound.WOOD_CLICK, 3F, 3F);
        player.playEffect(player.getLocation(), Effect.SMOKE, 3);
        player.setVelocity(player.getLocation().getDirection().multiply(strength).normalize().setY(strength / 2));
    }

    public void openGUI(Player player) {

        Inventory gui = Bukkit.createInventory(null, 9, "ß6JumpPad ß3Config");
        addConfigItems(gui);
        player.openInventory(gui);

    }

    private void addConfigItems(Inventory gui) {

        ItemStack count = new ItemStack(Material.STONE_PLATE, 1);
        ItemMeta itemMeta = count.getItemMeta();
        itemMeta.setDisplayName("ß3Strength");
        String[] countLore = { "ß6" + this.strength };
        itemMeta.setLore(Arrays.asList(countLore));
        count.setItemMeta(itemMeta);

        ItemStack add = new ItemStack(Material.EMERALD_BLOCK, 1);
        itemMeta = add.getItemMeta();
        itemMeta.setDisplayName("ß2Add One");
        String[] addLore = { "ß7+1 Strength" };
        itemMeta.setLore(Arrays.asList(addLore));
        add.setItemMeta(itemMeta);

        ItemStack minus = new ItemStack(Material.REDSTONE_BLOCK, 1);
        itemMeta = minus.getItemMeta();
        itemMeta.setDisplayName("ß4Subtract One");
        String[] subLore = { "ß7+1 Strength" };
        itemMeta.setLore(Arrays.asList(subLore));
        minus.setItemMeta(itemMeta);

        ItemStack change = new ItemStack(Material.WOOL, 1);
        itemMeta = change.getItemMeta();
        itemMeta.setDisplayName("ß6Changes");
        String[] changeLore = { "ß7No Changes" };
        itemMeta.setLore(Arrays.asList(changeLore));
        change.setItemMeta(itemMeta);

        ItemStack confirm = new ItemStack(Material.WOOL, 1, (byte) 9);
        itemMeta = confirm.getItemMeta();
        itemMeta.setDisplayName("ß2Confirm");
        String[] confirmLore = { "ß7Confirm your", "ßchanges." };
        itemMeta.setLore(Arrays.asList(confirmLore));
        confirm.setItemMeta(itemMeta);

        ItemStack cancel = new ItemStack(Material.BEDROCK, 1);
        itemMeta = cancel.getItemMeta();
        itemMeta.setDisplayName("ß4Cancel");
        String[] cancelLore = { "ß7Cancel your", "ß7changes." };
        itemMeta.setLore(Arrays.asList(cancelLore));
        cancel.setItemMeta(itemMeta);

        gui.setItem(0, count);
        gui.setItem(2, add);
        gui.setItem(3, minus);
        gui.setItem(5, change);
        gui.setItem(8, confirm);
        gui.setItem(9, cancel);

    }

    private JPData getEntryDB() {

        JPData entry = null;

        for(JPData jpData : plugin.getDatabase().find(JPData.class).findSet()) {

            if (jpData.getLocation() == this.location) {
                entry = jpData;
            }

        }

        if (entry == null) {

            entry = new JPData();
            entry.setLocation(this.location);
            entry.setStrength(this.strength);

            saveToDatabase();

        }

        return entry;

    }

    public void saveToDatabase() {
        this.entry.setJumpPad(this);
        plugin.getDatabase().save(this.entry);
    }

}
