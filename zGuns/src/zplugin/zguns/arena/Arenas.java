package zplugin.zguns.arena;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity()
@Table(name = "Arenas")
public class Arenas {

    @Id
    private int id;

    @NotEmpty
    private String name;

    @NotNull
    private Arena arena;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public void setTheArena(Arena arena) {
        this.arena = arena;
        this.name = arena.getName();
    }

}
