package com.zp4rker.core.hg;

import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hologram {

    private static final double distance = 0.23;
    private List<String> lines = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    private boolean showing = false;
    private Location location;
    private Plugin plugin;

    public Hologram(Plugin plugin, String... lines) {

        this.plugin = plugin;

        this.lines.addAll(Arrays.asList(lines));

    }

    public void change(String... lines) {
        // Send the destroy packet
        destroy();
        // Set the hologram text
        this.lines = Arrays.asList(lines);
        // Show the new text to all the players
        show(this.location);
    }

    public void show(Location loc) {
        if (showing == true) {
            try {
                throw new Exception("Is already showing!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Location first = loc.clone().add(0, (this.lines.size() / 2) * distance, 0);
        for (int i = 0; i < this.lines.size(); i++) {
            ids.addAll(showLine(first.clone(), this.lines.get(i)));
            first.subtract(0, distance, 0);
        }
        showing = true;
        this.location = loc;
    }

    public void show(Location loc, Player player) {
        if (showing == true) {
            try {
                throw new Exception("Is already showing!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Location first = loc.clone().add(0, (this.lines.size() /2) * distance, 0);
        for (int i = 0; i < this.lines.size(); i++) {
            ids.addAll(showLine(player, first.clone(), this.lines.get(i)));
            first.subtract(0, distance, 0);
        }
        showing = true;
        this.location = loc;
    }

    // Shows to all players in the world
    public void show(Location loc, long ticks) {
        show(loc);
        new BukkitRunnable() {
            @Override
            public void run() {
                destroy();
            }
        }.runTaskLater(plugin, ticks);
    }

    // Shows to a specific player
    public void show(Player player, Location loc, long ticks) {
        show(loc, player);
        new BukkitRunnable() {
            @Override
            public void run() {
                destroy();
            }
        }.runTaskLater(plugin, ticks);
    }

    public void destroy() {
        if (showing == false) {
            try {
                throw new Exception("Isn't showing!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int[] ints = new int[this.ids.size()];
        for (int j = 0; j < ints.length; j++) {
            ints[j] = ids.get(j);
        }
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(ints);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
        showing = false;
        this.location = null;
    }

    private static List<Integer> showLine(Location loc, String text) {
        // Get NMS World
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        // Create skull
        EntityWitherSkull skull = new EntityWitherSkull(world);
        // Set the location
        skull.setLocation(loc.getX(), loc.getY() + 1 + 55, loc.getZ(), 0, 0);
        // Create the spawn packet
        PacketPlayOutSpawnEntity skullPacket = new PacketPlayOutSpawnEntity(skull, 66);
        // Create horse
        EntityHorse horse = new EntityHorse(world);
        // Set the location
        horse.setLocation(loc.getX(), loc.getY() + 55, loc.getZ(), 0, 0);
        // Set the age
        horse.setAge(-1700000);
        // Set the nametag
        horse.setCustomName(text);
        // Makes the nametag visible
        horse.setCustomNameVisible(true);
        // Create the spawn packet
        PacketPlayOutSpawnEntityLiving packedt = new PacketPlayOutSpawnEntityLiving(horse);
        // Gets all the players in the world
        for (Player player : loc.getWorld().getPlayers()) {
            // Get the NMS Player
            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            // Send the packets to the player
            nmsPlayer.playerConnection.sendPacket(packedt);
            nmsPlayer.playerConnection.sendPacket(skullPacket);
            // Create the attachment packet
            PacketPlayOutAttachEntity attachPacket = new PacketPlayOutAttachEntity(0, horse, skull);
            // Send it to the player
            nmsPlayer.playerConnection.sendPacket(attachPacket);
        }
        // Return IDs
        return Arrays.asList(skull.getId(), horse.getId());
    }

    private static List<Integer> showLine(Player player, Location loc, String text) {
        // Get NMS World
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        // Create skull
        EntityWitherSkull skull = new EntityWitherSkull(world);
        // Set the location
        skull.setLocation(loc.getX(), loc.getY() + 56, loc.getZ(), 0, 0);
        // Create the spawn packet
        PacketPlayOutSpawnEntity skullPacket = new PacketPlayOutSpawnEntity(skull, 66);
        // Create horse
        EntityHorse horse = new EntityHorse(world);
        // Set the location
        horse.setLocation(loc.getX(), loc.getY() + 55, loc.getZ(), 0, 0);
        // Set the age
        horse.setAge(-1700000);
        // Set the nametag
        horse.setCustomName(text);
        // Make it visible
        horse.setCustomNameVisible(true);
        // Create spawn packet
        PacketPlayOutSpawnEntityLiving horsePacket = new PacketPlayOutSpawnEntityLiving(horse);
        // Get the NMS Player
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        // Send the packets to the player
        nmsPlayer.playerConnection.sendPacket(skullPacket);
        nmsPlayer.playerConnection.sendPacket(horsePacket);
        // Create the attachment packet
        PacketPlayOutAttachEntity attachPacket = new PacketPlayOutAttachEntity(0, horse, skull);
        // Send it to the player
        nmsPlayer.playerConnection.sendPacket(attachPacket);
        // Return IDs
        return Arrays.asList(skull.getId(), horse.getId());
    }

    // Remove the hologram
    public void remove() {
        // Loop through all entities in the world
        for (Entity entity : this.location.getWorld().getEntities()) {
            // Check if the entity is part of the hologram
            if (ids.contains(entity.getEntityId())) {
                // Remove the entity
                entity.remove();
            }
        }
        // Clear ids
        ids.clear();
        // Clear lines
        lines.clear();
    }

    public Location getLocation() {
        return location;
    }

}