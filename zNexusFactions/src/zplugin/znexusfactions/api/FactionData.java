package zplugin.znexusfactions.api;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.Location;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "FactionsDB")
public class FactionData {

    @Id
    private int id;

    @NotNull
    private Location location;

    @NotNull
    private String name;

    @NotNull
    private Faction faction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
        this.name = faction.getName();
    }

}
