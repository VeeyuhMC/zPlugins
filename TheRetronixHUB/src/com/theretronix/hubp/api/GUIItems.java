package com.theretronix.hubp.api;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class GUIItems {

    public static ItemStack getFactions() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("ß4Factions");
        int playerCount = BungeeMessenger.factionCount;
        String[] lore = {"ß2" + playerCount + " players online."};
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getPrison() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("ß6Prison");
        int playerCount = BungeeMessenger.prisonCount;
        String[] lore = {"ß2" + playerCount + " players online."};
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getArcade() {
        ItemStack item = new ItemStack(Material.FIREWORK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("ßbArcade");
        int playerCount = BungeeMessenger.arcadeCount;
        String[] lore = {"ß2" + playerCount + " players online."};
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWebsite() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("ß9ßlThe ß4ßlRetronix");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getStaff() {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("ß2ßlStaff Team");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getPlayerHead(String name, String prefix) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(name);
        meta.setDisplayName(name);
        String online = "ß2Online";
        String offline = "ß4Offline";
        String[] lore = {prefix,
                (Bukkit.getPlayer(name) != null ? online : offline)};
        meta.setLore(Arrays.asList(lore));
        skull.setItemMeta(meta);
        return skull;
    }

    public static ItemStack getPane() {
        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta itemMeta = pane.getItemMeta();
        itemMeta.setDisplayName("ßr");
        pane.setItemMeta(itemMeta);
        return pane;
    }

    public static ItemStack getGlobal() {
        ItemStack global = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta itemMeta = global.getItemMeta();
        itemMeta.setDisplayName("ß5ßlGlobal Staff");
        global.setItemMeta(itemMeta);
        return global;
    }

    public static ItemStack getCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = compass.getItemMeta();
        itemMeta.setDisplayName("ß6ßlServer Selector");
        String[] lore = {
                "ß7Right click to",
                "ß7select a server."};
        itemMeta.setLore(Arrays.asList(lore));
        compass.setItemMeta(itemMeta);
        return compass;
    }

}
