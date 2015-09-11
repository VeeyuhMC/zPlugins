package zplugin.zguns.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Arena {

    private String name;
    private Selection selection;
    private List<Location> spawnPoints = new ArrayList<>();

    public Arena(String name, Selection selection) {
        this.name = name;
        this.selection = selection;
    }

    public boolean teleport(Player player, int id) {
        if (!spawnPoints.isEmpty()) {
            Location location = spawnPoints.get(id - 1);
            location.getChunk().load();
            player.teleport(location);
            return true;
        } else {
            return false;
        }
    }

    public boolean teleportRandom(Player player) {
        if (spawnPoints.size() > 1) {
            int random = 0 + (int) (Math.random() * ((spawnPoints.size() - 0) + 1));
            teleport(player, random);
            return true;
        } else {
            return false;
        }
    }

    public void addSpawnPoint(Location location) {
        spawnPoints.add(location);
    }

    public void removeSpawnPoint(Location location) {
        spawnPoints.remove(location);
    }

    public boolean isValid() {
        return spawnPoints.isEmpty();
    }

    public boolean isInRange(Player player) {
        if (selection.getWholeRange().contains(player.getLocation())) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public List<Location> getSpawnPoints() {
        return spawnPoints;
    }

    public Selection getSelection() {
        return selection;
    }

}
