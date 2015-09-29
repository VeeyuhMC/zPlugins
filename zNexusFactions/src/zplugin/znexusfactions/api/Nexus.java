package zplugin.znexusfactions.api;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Nexus {

    private Location location;
    private EnderCrystal nexus;
    public boolean hasVault = false;

    public Nexus(Location location) {
        this.location = location;
        nexus = (EnderCrystal) location.getWorld().spawnEntity(location.clone().add(.5, -.25, .5), EntityType.ENDER_CRYSTAL);
    }

    public Location getLocation() {
        return location;
    }

    public Vault createVault(Player player) {
        return new Vault(this, player);
    }

}
