package zplugin.zguns;

import org.bukkit.plugin.java.JavaPlugin;
import zplugin.zguns.arena.Arenas;
import zplugin.zguns.listeners.EggThrowListener;
import zplugin.zguns.listeners.EntityDamageByEntityListener;
import zplugin.zguns.listeners.PlayerInteractListener;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class zGuns extends JavaPlugin {

    public void onEnable() {

        setupDatabase();

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new EggThrowListener(), this);

    }

    public void setupDatabase() {
        try {
            getDatabase().find(Arenas.class).findRowCount();
        } catch (Exception e) {
            getLogger().info("Installing database for " + getDescription().getName() + " due to first time usage!");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Arenas.class);
        return list;
    }

}
