package com.theretronix.hubp.api;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "jumpPads")
public class JPData {

    @Id
    private int id;

    @NotEmpty
    private String world;

    @NotNull
    private double x, y, z;

    @NotNull
    private int strength;

    public String getWorld() {
        return world;
    }

    public World getBukkitWorld() {
        return Bukkit.getWorld(this.world);
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Location getLocation() {
        return new Location(this.getBukkitWorld(),
                this.x, this.y, this.z);
    }

    public void setLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public JP getJumpPad() {
        return new JP(this.getLocation(), this.getStrength());
    }

    public void setJumpPad(JP jumpPad) {
        this.world = jumpPad.getLocation().getWorld().getName();
        this.x = jumpPad.getLocation().getX();
        this.y = jumpPad.getLocation().getY();
        this.z = jumpPad.getLocation().getZ();
        this.strength = jumpPad.getStrength();
    }

}
