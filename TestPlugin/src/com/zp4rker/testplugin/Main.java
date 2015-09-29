package com.zp4rker.testplugin;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public void onEnable() {

        getLogger().info("TestPlugin enabled!");

        getCommand("test").setExecutor(new TheCommandClass());

        getServer().getPluginManager().registerEvents(new TheEventClass(), this);
        getServer().getPluginManager().registerEvents(this, this);

    }

    public void onDisable() {

        getLogger().info("TestPlugin disabled!");

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 16);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName("§5The Infinity Blade");

        itemStack.setItemMeta(itemMeta);

        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setItem(4, itemStack);

    }

}
