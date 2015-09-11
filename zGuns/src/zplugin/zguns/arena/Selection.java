package zplugin.zguns.arena;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Selection {

    private Location pointOne;
    private Location pointTwo;

    public Selection(Location pointOne, Location pointTwo) {
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
    }

    public Location getPointOne() {
        return pointOne;
    }

    public Location getPointTwo() {
        return pointTwo;
    }

    public List<Location> getWholeRange() {
        List<Location> range = new ArrayList<>();
        if (pointOne.getX() > pointTwo.getX()) {
            Location temp = pointOne;
            pointOne = pointTwo;
            pointTwo = temp;
        }
        for (int x = pointOne.getBlockX(); x <= pointTwo.getBlockX(); x++) {
            for (int y = pointOne.getBlockY(); y <= pointTwo.getBlockY(); y++) {
                for (int z = pointOne.getBlockZ(); z <= pointTwo.getBlockZ(); z++) {
                    Location loc = new Location(pointOne.getWorld(), x, y, z);
                }
            }
        }
            return range;
    }

}
