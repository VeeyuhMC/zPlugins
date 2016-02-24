package com.zp4rker.core.inv;

import com.zp4rker.core.inv.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InvAPI {

    private static Map<String, GUI> guis = new HashMap<>();

    public void addGUI(String name, Collection<ItemStack> contents, int size) {

        guis.put(name, new GUI(contents, size, name));

    }

    public void addGUI(String name, Collection<ItemStack> contents, int size, String title) {

        guis.put(name, new GUI(contents, size, title));

    }

    public void removeGUI(String name) {

        guis.remove(name);

    }

    public GUI getGUI(String name) {

        return guis.get(name);

    }

    public void openGUI(Player player, GUI gui) {

        gui.openGUI(player);

    }

    public void showInv(Player player, Inventory inventory) {

        player.openInventory(inventory);

    }

    public void showPlayerInv(Player toShow, PlayerInventory playerInventory) {

        toShow.openInventory(playerInventory);

    }

    public Inventory createInventory(String title, int size) {

        return Bukkit.createInventory(null, size, title);

    }

    public Inventory createInventory(String title, int size, Collection<ItemStack> contents) {

        Inventory inventory = Bukkit.createInventory(null, size, title);
        inventory.setContents((ItemStack[]) contents.toArray());

        return inventory;

    }

    public void showAsInv(Player player, Collection<ItemStack> itemStacks) {

        player.openInventory(createInventory(null, itemStacks.size(), itemStacks));

    }

    public void showAsInv(Player player, String title, Collection<ItemStack> itemStacks) {

        player.openInventory(createInventory(title, itemStacks.size(), itemStacks));

    }

}