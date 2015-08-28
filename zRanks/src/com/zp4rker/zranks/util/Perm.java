package com.zp4rker.zranks.util;

import com.zp4rker.zranks.config.Config;
import com.zp4rker.zranks.config.ConfigManager;
import com.zp4rker.zranks.zRanks;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;

@SuppressWarnings("unused")
public class Perm {

    private static zRanks plugin;
    private static ConfigManager manager;
    private static Config ranks;

    public Perm(zRanks plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    public static HashMap<Player, PermissionAttachment> attachments = new HashMap<>();

    public static void resetPerms() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PermissionAttachment attachment = attachments.get(player);
            if (attachment != null) {
                player.removeAttachment(attachment);
                attachments.remove(player);
            }
        }
    }

    public static void reloadPerms() {

        resetPerms();

        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();
            ranks = manager.getNewConfig(world.getName() + "/ranks.yml");
            String rank = ranks.getString("players." + player.getUniqueId() + ".rank");
            PermissionAttachment attachment = player.addAttachment(plugin);
            if (ranks.getList("ranks." + rank + ".perms") != null) {
                for (Object Perm : ranks.getList("ranks." + rank + ".perms")) {
                    String perm = Perm.toString();
                    attachment.setPermission(perm, true);
                }
            }
        }

    }

}