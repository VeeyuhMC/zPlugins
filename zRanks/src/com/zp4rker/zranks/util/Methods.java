package com.zp4rker.zranks.util;

import com.zp4rker.zranks.config.Config;
import com.zp4rker.zranks.config.ConfigManager;
import com.zp4rker.zranks.zRanks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Methods {

    zRanks plugin;
    ConfigManager manager;

    public Methods(zRanks plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    public List<String> getStaffList() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Config ranks = manager.getNewConfig("ranks.yml");
            String rank = ranks.getString("players." + player.getUniqueId() + ".rank");
            boolean staff = ranks.getBoolean("ranks." + rank + ".staff");
            if (staff) {
                players.add(getFullDisplayName(player));
            }
        }
        return players;
    }

    public List<String> getPlayerList() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(getFullDisplayName(player));
        }
        return players;
    }

    public String getFullPrefix(Player player) {
        Config ranks = manager.getNewConfig("ranks.yml");
        String rank = ranks.getString("players." + player.getUniqueId() + ".rank");
        String prefix;
        String fullPrefix = "§7[";
        if (rank != null) {
            prefix = ranks.getString("ranks." + rank + ".prefix");
            fullPrefix += prefix + "§7]";
        } else {
            if (ranks.getString("players." + player.getUniqueId() + ".prefix") != null) {
                prefix = ranks.getString("players." + player.getUniqueId() + ".prefix");
                fullPrefix += prefix + "§7]";
            } else {
                prefix = "";
                fullPrefix = prefix;
            }
        }
        return fullPrefix;
    }

    public String getFullDisplayName(Player player) {
        String playerName = player.getDisplayName();
        Config ranks = manager.getNewConfig("ranks.yml");
        String rank = ranks.getString("players." + player.getUniqueId() + ".rank");
        String prefix;
        String fullPrefix;
        if (rank != null) {
            prefix = ranks.getString("ranks." + rank + ".prefix");
            fullPrefix = "§7[" + prefix + "§7]";
        } else {
            if (ranks.getString("players." + player.getUniqueId() + ".prefix") != null) {
                prefix = ranks.getString("players." + player.getUniqueId() + ".prefix");
                fullPrefix = "§7[" + prefix + "§7]";
            } else {
                prefix = "";
                fullPrefix = prefix;
            }
        }
        return fullPrefix + " §r" + playerName;
    }

    public String getPrefix(Player player) {
        Config ranks = manager.getNewConfig("ranks.yml");
        String rank = ranks.getString("players." + player.getUniqueId() + ".rank");
        String prefix;
        if (rank != null) {
            prefix = ranks.getString("ranks." + rank + ".prefix");
        } else {
            if (ranks.getString("players." + player.getUniqueId() + ".prefix") != null) {
                prefix = ranks.getString("players." + player.getUniqueId() + ".prefix");
            } else {
                prefix = "";
            }
        }
        return prefix;
    }

    public String getDefaultRank() {
        Config ranks = manager.getNewConfig("ranks.yml");
        String rank = null;
        for (Object Rank : ranks.getConfigurationSection("ranks").getKeys(false)) {
            boolean isDefault = ranks.getBoolean("ranks." + Rank.toString() + ".default");
            if (isDefault) {
                rank = Rank.toString();
            }
        }
        return rank;
    }

}