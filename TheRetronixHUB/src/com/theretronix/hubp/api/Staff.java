package com.theretronix.hubp.api;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theretronix.hubp.HUB;
import com.theretronix.hubp.api.config.Config;

public class Staff {

    public static Config fStaff = HUB.manager.getNewConfig("staff/factions.yml");
    public static Config pStaff = HUB.manager.getNewConfig("staff/prison.yml");
    public static Config aStaff = HUB.manager.getNewConfig("staff/arcade.yml");
    public static Config gStaff = HUB.manager.getNewConfig("staff/global.yml");

    public static String getPrefix(Config config, String rank) {
        return config.getString(rank + ".Prefix");
    }

    public static List<String> getPlayers(Config config, String rank) {
        return config.getStringList(rank + ".Players");
    }

    public static boolean isOnline(String name) {
        Player player = Bukkit.getPlayer(name);
        return (player != null);
    }

    public static ItemStack[] getGUIPage(String server) {

        ItemStack[] page = new ItemStack[54];

        for (int i = 0; i < 54; i++) {

            if (server == "factions") {
                if (i == 4) {
                    page[i] = GUIItems.getFactions();
                } else if (i == 21) {
                    page[i] = GUIItems.getPlayerHead("UrbanarmyMC", "Mod");
                } else if (i == 22) {
                    page[i] = GUIItems.getPlayerHead("GuyGoneGaming", "Manager");
                } else if (i == 23) {
                    page[i] = GUIItems.getPlayerHead("apk_gts2", "Mod");
                } else if (i == 31) {
                    page[i] = GUIItems.getPlayerHead("MrSonicOSG", "Mod");
                } else {
                    page[i] = GUIItems.getPane();
                }
            } else if (server == "prison") {
                if (i == 4) {
                    page[i] = GUIItems.getPrison();
                } else if (i == 21) {
                    page[i] = GUIItems.getPlayerHead("Gevster", "Mod");
                } else if (i == 22) {
                    page[i] = GUIItems.getPlayerHead("DefaultMode", "Manager");
                } else if (i == 23) {
                    page[i] = GUIItems.getPlayerHead("Emilinax", "Mod");
                } else {
                    page[i] = GUIItems.getPane();
                }
            } else if (server == "arcade") {
                if (i == 4) {
                    page[i] = GUIItems.getArcade();
                } else if (i == 22) {
                    page[i] = GUIItems.getPlayerHead("Kimonas", "Manager");
                } else {
                    page[i] = GUIItems.getPane();
                }
            } else if (server == "global") {
                if (i == 4) {
                    page[i] = GUIItems.getGlobal();
                } else if (i == 20) {
                    page[i] = GUIItems.getPlayerHead("ZP4RKER", "Owner");
                } else if (i == 22) {
                    page[i] = GUIItems.getPlayerHead("noahp_1", "Administrator");
                } else if (i == 24) {
                    page[i] = GUIItems.getPlayerHead("Jx8", "Owner");
                } else {
                    page[i] = GUIItems.getPane();
                }
            }

        }

        return page;

    }

}
