package zplugin.znexusfactions.api;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "FactionsDB")
public class FactionData {

    @Id
    private int id;

    @NotEmpty
    private String name, tag;

    @NotNull
    private boolean open;

    @NotNull
    private List<UUID> players = new ArrayList<>();

    @NotEmpty
    private String world;

    @NotNull
    private int nexusX, nexusY, nexusZ;

    @NotNull
    private int xOne, yOne, zOne;

    @NotNull
    private int xTwo, yTwo, zTwo;

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

    public void setFaction(Faction faction) {
        this.name = faction.getName();
        this.tag = faction.getTag();
        List<UUID> players = new ArrayList<>();
        for (OfflinePlayer player : faction.getPlayers()) {
            players.add(player.getUniqueId());
        }
        this.players = players;
        this.world = faction.getNexus().getLocation().getWorld().getName();
        this.nexusX = faction.getNexus().getLocation().getBlockX();
        this.nexusY = faction.getNexus().getLocation().getBlockY();
        this.nexusZ = faction.getNexus().getLocation().getBlockZ();
        this.xOne = faction.getBase().getArea().get(0).getBlockX();
        this.yOne = faction.getBase().getArea().get(0).getBlockY();
        this.zOne = faction.getBase().getArea().get(0).getBlockZ();
        this.xTwo = faction.getBase().getArea().get(faction.getBase().getArea().size() - 1).getBlockX();
        this.yTwo = faction.getBase().getArea().get(faction.getBase().getArea().size() - 1).getBlockY();
        this.zTwo = faction.getBase().getArea().get(faction.getBase().getArea().size() - 1).getBlockZ();
    }

    public List<OfflinePlayer> getBukkitPlayers() {
        List<OfflinePlayer> list = new ArrayList<>();
        for (UUID uniqueID : players) {
            list.add(Bukkit.getOfflinePlayer(uniqueID));
        }
        return list;
    }

    public Faction getFaction() {
        Nexus nexus = new Nexus(new Location(Bukkit.getWorld(world), nexusX, nexusY, nexusZ), false);
        Vault vault = new Vault(nexus);
        Base base = new Base(vault, players.size());
        Faction faction = new Faction(name, tag, getBukkitPlayers(), base);
        faction.setOpen(this.open);
        return faction;
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

}
