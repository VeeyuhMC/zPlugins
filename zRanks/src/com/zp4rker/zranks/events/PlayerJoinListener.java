package com.zp4rker.zranks.events;

import com.zp4rker.zranks.config.Config;
import com.zp4rker.zranks.config.ConfigManager;
import com.zp4rker.zranks.zRanks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PlayerJoinListener implements Listener {

    zRanks plugin;
    ConfigManager manager;

    public PlayerJoinListener(zRanks plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config ranks = manager.getNewConfig("ranks.yml");
        if (ranks.getString("ranks") == null) {
            makeDefaultPerms(ranks);
        }
        String rank = ranks.getString("players." + player.getUniqueId() + ".rank");
        if (rank == null) {
            ranks.set("players." + player.getUniqueId() + ".rank", plugin.m.getDefaultRank());
        }
    }

    public void makeDefaultPerms(Config ranks) {

        /* Member Section */
        List<String> memberPerms = new ArrayList<>();
        memberPerms.add("permission.node");
        memberPerms.add("permission.node.node");

        ranks.set("ranks.Member.prefix", "§7Member");
        ranks.set("ranks.Member.perms", memberPerms);
        ranks.set("ranks.Member.staff", false);

        /* Moderator Section */
        List<String> modPerms = new ArrayList<>();
        modPerms.add("permission.node");
        modPerms.add("permission.node.node");

        ranks.set("ranks.Moderator.prefix", "§5Mod");
        ranks.set("ranks.Moderator.perms", modPerms);
        ranks.set("ranks.Moderator.staff", true);

        /* Admin Section */
        List<String> adminPerms = new ArrayList<>();
        adminPerms.add("permission.node");
        adminPerms.add("permission.node.node");

        ranks.set("ranks.Admin.prefix", "§4Admin");
        ranks.set("ranks.Admin.perms", adminPerms);
        ranks.set("ranks.Admin.staff", true);

        /* Owner Section */
        List<String> ownerPerms = new ArrayList<>();
        ownerPerms.add("permission.node");
        ownerPerms.add("permission.node.node");

        ranks.set("ranks.Owner.prefix", "§3Owner");
        ranks.set("ranks.Owner.perms", ownerPerms);
        ranks.set("ranks.Owner.staff", true);

        /* Saving of Config */
        ranks.saveConfig();

    }

}
