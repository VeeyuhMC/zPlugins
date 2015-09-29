package zplugin.znexusfactions.api;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Faction {

    private String name;
    private String tag;
    private List<Player> players = new ArrayList<>();
    private Base base;

    public Faction(String name, String tag, List<Player> players, Base base) {

        this.name = name;
        this.tag = tag;
        this.players = players;
        this.base = base;

        for (Player player : players) {
            player.sendMessage("§6Faction §2" + name + " §6created!");
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Base getBase() {
        return base;
    }

    public Vault getVault() {
        return base.getVault();
    }

    public Nexus getNexus() {
        return base.getVault().getNexus();
    }

}
