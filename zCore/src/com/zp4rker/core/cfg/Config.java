package com.zp4rker.core.cfg;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private int comments;
    private ConfigManager manager;

    private File file;
    private FileConfiguration config;
    private File dataFolder;
    private ClassLoader classLoader;
    private PluginLogger logger;


    @SuppressWarnings("deprecation")
    public Config(InputStream configStream, File configFile, int comments, JavaPlugin plugin) {
        this.comments = comments;
        this.manager = new ConfigManager(plugin);
        this.file = configFile;
        this.config = YamlConfiguration.loadConfiguration(configStream);
        this.dataFolder = plugin.getDataFolder();
        try {
            Method m = plugin.getClass().getDeclaredMethod("getClassLoader");
            m.setAccessible(true);
            this.classLoader = (ClassLoader) m.invoke(plugin);
            m.setAccessible(false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            Field field = plugin.getClass().getDeclaredField("logger");
            field.setAccessible(true);
            this.logger = (PluginLogger) field.get(plugin);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public Object get(String path, Object def) {
        return this.config.get(path, def);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    public void createSection(String path) {
        this.config.createSection(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return this.config.getDouble(path, def);
    }

    public List<?> getList(String path) {
        return this.config.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return this.config.getList(path, def);
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public void removeKey(String path) {
        this.config.set(path, null);
    }

    public void set(String path, Object value) {
        this.config.set(path, value);
    }

    public void set(String path, Object value, String comment) {
        if (!this.config.contains(path)) {
            this.config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comment);
            comments++;
        }
        this.config.set(path, value);
    }

    public void set(String path, Object value, String[] comment) {
        for (String comm : comment) {
            if (!this.config.contains(path)) {
                this.config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comm);
                comments++;
            }
        }
        this.config.set(path, value);
    }

    public void setHeader(String[] header) {
        manager.setHeader(this.file, header);
        this.comments = header.length + 2;
        this.reloadConfig();
    }

    @SuppressWarnings("deprecation")
    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(manager.getConfigContent(file));
    }

    public void saveConfig() {
        String config = this.config.saveToString();
        manager.saveConfig(config, this.file);
    }

    public Set<String> getKeys() {
        return this.config.getKeys(false);
    }

    public List<String> getStringList(String path) {
        List<?> objects = this.config.getList(path);
        List<String> list = new ArrayList<>();
        for (Object object : objects) {
            list.add(object.toString());
        }
        return list;
    }

    private void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + file);
        }

        File outFile = new File(dataFolder, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    private InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try {
            URL url = classLoader.getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }

    public void saveDefaults() {
        if(!file.exists()) {
            saveResource(file.getName(), false);
        }
    }

    public void overrideDefaults() {
        saveResource(file.getName(), true);
    }

}