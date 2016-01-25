package com.theretronix.hubp.api;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class GUIItems {
	
	public static ItemStack getFactions() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("�4Factions");
		int playerCount = BungeeMessenger.factionCount;
		String[] lore = {"�2" + playerCount + " players online."};
		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack getPrison() {
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("�6Prison");
		int playerCount = BungeeMessenger.prisonCount;
		String[] lore = {"�2" + playerCount + " players online."};
		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack getArcade() {
		ItemStack item = new ItemStack(Material.FIREWORK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("�4Arcade");
		int playerCount = BungeeMessenger.arcadeCount;
		String[] lore = {"�2" + playerCount + " players online."};
		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack getWebsite() {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("�1�lThe �4�lRetronix");
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack getStaff() {
		ItemStack item = new ItemStack(Material.SKULL, 1, (byte) 3);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("�2�lStaff Team");
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack getPlayerHead(String name, String displayName) {
		ItemStack skull = new ItemStack(Material.SKULL);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(name);
		meta.setDisplayName(displayName);
		skull.setItemMeta(meta);
		return skull;
	}

}
