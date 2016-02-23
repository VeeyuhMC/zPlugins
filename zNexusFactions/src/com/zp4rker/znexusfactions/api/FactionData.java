package com.zp4rker.znexusfactions.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zp4rker.znexusfactions.zNexusFactions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "FactionsDB")
public class FactionData {

    @Id
    private int id;

    @NotEmpty
    private String name, tag;

    @NotNull
    private boolean open = false;

    @NotNull
    private List<UUID> players = new ArrayList<>(), staff = new ArrayList<>(), invited = new ArrayList<>();

    @NotEmpty
    private String world;

    @NotNull
    private int nexusX, nexusY, nexusZ;

    @NotNull
    private int xOne, yOne, zOne;

    @NotNull
    private int xTwo, yTwo, zTwo;

    @NotNull
    private double nexusHealth = zNexusFactions.getPlugin().getConfig(null).getDouble("nexus-health");

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public String getWorld() {
        return world;
    }

    public int getNexusX() {
        return nexusX;
    }

    public int getNexusY() {
        return nexusY;
    }

    public int getNexusZ() {
        return nexusZ;
    }

    public int getXOne() {
        return xOne;
    }

    public int getYOne() {
        return yOne;
    }

    public int getZOne() {
        return zOne;
    }

    public int getXTwo() {
        return xTwo;
    }

    public int getYTwo() {
        return yTwo;
    }

    public int getZTwo() {
        return zTwo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setPlayers(List<UUID> players) {
        this.players = players;
    }

    public List<UUID> getStaff() {
        return staff;
    }

    public void setStaff(List<UUID> staff) {
        this.staff = staff;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setNexusX(int nexusX) {
        this.nexusX = nexusX;
    }

    public void setNexusY(int nexusY) {
        this.nexusY = nexusY;
    }

    public void setNexusZ(int nexusZ) {
        this.nexusZ = nexusZ;
    }

    public void setXOne(int xOne) {
        this.xOne = xOne;
    }

    public void setYOne(int yOne) {
        this.yOne = yOne;
    }

    public void setZOne(int zOne) {
        this.zOne = zOne;
    }

    public void setXTwo(int xTwo) {
        this.xTwo = xTwo;
    }

    public void setYTwo(int yTwo) {
        this.yTwo = yTwo;
    }

    public void setZTwo(int zTwo) {
        this.zTwo = zTwo;
    }

    public double getNexusHealth() {
        return nexusHealth;
    }

    public void setNexusHealth(double nexusHealth) {
        this.nexusHealth = nexusHealth;
    }

    public void setFaction(Faction faction) {
        name = faction.getName();
        tag = faction.getTag();
        open = faction.isOpen();
        players = faction.getPlayers();
        staff = faction.getPlayers();
        world = faction.getNexus().getLocation().getWorld().getName();
        nexusX = faction.getNexus().getLocation().getBlockX();
        nexusY = faction.getNexus().getLocation().getBlockY();
        nexusZ = faction.getNexus().getLocation().getBlockZ();
        xOne = faction.getBase().getArea().get(0).getBlockX();
        yOne = faction.getBase().getArea().get(0).getBlockY();
        zOne = faction.getBase().getArea().get(0).getBlockZ();
        xTwo = faction.getBase().getArea().get(faction.getBase().getArea().size() - 1).getBlockX();
        yTwo = faction.getBase().getArea().get(faction.getBase().getArea().size() - 1).getBlockY();
        zTwo = faction.getBase().getArea().get(faction.getBase().getArea().size() - 1).getBlockZ();
        nexusHealth = faction.getNexus().getHealth();
    }
    
    

    public List<OfflinePlayer> getBukkitPlayers() {
        List<OfflinePlayer> list = new ArrayList<>();
        for (UUID uniqueID : players) {
            list.add(Bukkit.getOfflinePlayer(uniqueID));
        }
        return list;
    }

    public List<OfflinePlayer> getBukkitStaff() {
        List<OfflinePlayer> list = new ArrayList<>();
        for (UUID uuid : staff) {
            list.add(Bukkit.getOfflinePlayer(uuid));
        }
        return list;
    }

    public Faction getFaction() {
        Nexus nexus = new Nexus(new Location(Bukkit.getWorld(world), nexusX, nexusY, nexusZ), true);
        Vault vault = new Vault(nexus);
        Base base = new Base(vault, players.size());
        Faction faction = new Faction(name, tag, getPlayers(), base, getStaff(), open);
        faction.setOpen(open);
        return faction;
    }

    public void addPlayer(OfflinePlayer player) {
        players.add(player.getUniqueId());
    }

    public void removePlayer(OfflinePlayer player) {
        players.remove(player.getUniqueId());
    }

	public List<UUID> getInvited() {
		return invited;
	}

	public void setInvited(List<UUID> invited) {
		this.invited = invited;
	}
	
	public void resetInvited() {
		invited = new ArrayList<>();
	}
	
	public void addInvited(OfflinePlayer player) {
		invited.add(player.getUniqueId());
	}
	
	public void removeInvited(OfflinePlayer player) {
		invited.remove(player.getUniqueId());
	}

}
