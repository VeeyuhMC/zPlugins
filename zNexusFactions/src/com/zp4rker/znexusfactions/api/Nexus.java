package com.zp4rker.znexusfactions.api;

import com.zp4rker.core.cfg.Config;
import com.zp4rker.core.hg.Hologram;
import com.zp4rker.znexusfactions.zNexusFactions;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Nexus {

    private Location location;
    private EnderCrystal nexus;
    public boolean hasVault = false;
    private double health;
    private Hologram healthHolo;

    public Nexus(Location location, boolean created, Object... args) {
        this.location = location;
        if (!created) {
            Config config = zNexusFactions.getPlugin().getConfig(null);
            nexus = (EnderCrystal) location.getWorld().spawnEntity(location.clone().add(.5, .75, .5), EntityType.ENDER_CRYSTAL);
            health = config.getDouble("defaults.nexus-health");
        } else {
            for (Entity entity : location.getWorld().getEntities()) {
                if (entity instanceof EnderCrystal && entity.getLocation().clone().subtract(.5, .75, .5) == location) {
                    nexus = (EnderCrystal) entity;
                }
            }
            if (args[0] instanceof Double) {
                health = (double) args[0];
            } else {
                try {
                    throw new Exception("Invalid arguments!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public Location getLocation() {
        return location;
    }

    public Vault createVault(Player player) {
        return new Vault(this, player);
    }

    public EnderCrystal getNexus() {
        return nexus;
    }

    public void showHealth(Collection<Player> players) {
        // Create the hologram
        healthHolo = zNexusFactions.getHologramAPI().newHologram("§6Health: §2" + health);
        for (Player player : players) {
            zNexusFactions.getHologramAPI().showToPlayer(healthHolo, player, 0);
        }
    }

    public void hideHealth() {
        // Remove the hologram
        healthHolo.remove();
        // Clear field
        healthHolo = null;
    }

}