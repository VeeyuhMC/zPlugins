package com.zp4rker.zdatabase;

import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class zDatabase extends JavaPlugin {

    public void onEnable() {

        getCommand("db").setExecutor(new DBCommand(this));

        setupDatabase();

    }

    public void onDisable() {}

    private void setupDatabase() {
        try {
            getDatabase().find(DB.class).findRowCount();
        } catch (PersistenceException ex) {
            System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(DB.class);
        return list;
    }

}
