package com.zp4rker.core.updater;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UpdateAPI {

    private Plugin plugin;
    public static String serverVersion;

    public UpdateAPI(Plugin plugin) {

        this.plugin = plugin;
        this.serverVersion = plugin.getServer().getBukkitVersion().substring(0, 3);

    }

    private String getJSON(String url) {
        HttpURLConnection c = null;
        try {

            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.connect();
            int response = c.getResponseCode();

            switch (response) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (Exception e) {
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
        return null;
    }

    public String getLatestVersion(String plugin) {
        String data = getJSON("http://zplugins.cf/" + plugin);
        JSONObject jsonObject = null;
        if (data != null) {
            JSONParser parser = new JSONParser();
            try {
                jsonObject = (JSONObject) parser.parse(data);
            } catch (Exception e) {
            }
        }
        JSONArray versions;
        String version = null;
        if (jsonObject.get("versions") instanceof JSONArray) {
            versions = (JSONArray) jsonObject.get("versions");
            for (int i = 0; i < versions.size(); i++) {
                if (version != null) {
                    if (((JSONArray) ((JSONObject)
                            versions.get(i)).get("game_versions")).get(0).toString().contains(serverVersion)) {
                        if (Double.parseDouble(version) < Double.parseDouble(((JSONObject)
                                versions.get(i)).get("version").toString())) {
                            version = ((JSONObject) versions.get(i)).get("version").toString();
                        }
                    }
                } else {
                    if (((JSONArray) ((JSONObject)
                            versions.get(i)).get("game_versions")).get(0).toString().contains(serverVersion)) {
                        version = ((JSONObject) versions.get(i)).get("version").toString();
                    }
                }
            }
        }
        return version;
    }

    public String getDownload(String plugin) {

        String data = getJSON("http://zplugins.cf/" + plugin);
        JSONObject jsonObject = null;
        if (data != null) {
            JSONParser parser = new JSONParser();
            try {
                jsonObject = (JSONObject) parser.parse(data);
            } catch (Exception e) {
            }
        }

        JSONArray versions = (JSONArray) jsonObject.get("versions");
        List<JSONObject> validVersions = new ArrayList<>();

        for (int i = 0; i < versions.size(); i++) {

            JSONObject version = (JSONObject) versions.get(i);

            if (((JSONArray) version.get("game_versions")).get(0).toString().contains(serverVersion)) {
                validVersions.add(version);
            }

        }

        JSONObject latest = null;

        for (JSONObject version : validVersions) {

            long currentVersion = 0;

            if (latest == null) {
                latest = version;
                currentVersion = Long.parseLong(version.get("version").toString());
            }

            if (Long.parseLong(version.get("version").toString()) > currentVersion) {
                currentVersion = Long.parseLong(version.get("version").toString());
                latest = version;
            }

        }

        return latest.get("download").toString();

    }

    public void update(String pluginName, CommandSender sender) {
        sender.sendMessage("§6Commencing update...");
        String updateURL = getDownload(pluginName);
        String out;
        try {
            File to = new File(plugin.getServer().getUpdateFolderFile(),
                    updateURL.substring(updateURL.lastIndexOf('/') + 1, updateURL.length()));
            File tmp = new File(to.getPath() + ".au");
            if (!tmp.exists()) {
                plugin.getServer().getUpdateFolderFile().mkdirs();
                tmp.createNewFile();
            }
            URL url = new URL(updateURL);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(tmp);
            byte[] buffer = new byte[4096];
            int fetched;
            while ((fetched = is.read(buffer)) != -1) {
                os.write(buffer, 0, fetched);
            }
            is.close();
            os.flush();
            os.close();
            if (to.exists()) {
                to.delete();
            }
            tmp.renameTo(to);
            sender.sendMessage("§2Restart server to update!");
        } catch (Exception e) {
            sender.sendMessage("§4Failed to update!");
        }
    }

    public void update(String pluginName) {
        plugin.getLogger().info("Commencing update...");
        String updateURL = getDownload(pluginName);
        String out;
        try {
            File to = new File(plugin.getServer().getUpdateFolderFile(),
                    updateURL.substring(updateURL.indexOf("/") + 1, updateURL.length()));
            File tmp = new File(to.getPath() + ".au");
            if (!tmp.exists()) {
                plugin.getServer().getUpdateFolderFile().mkdirs();
                tmp.createNewFile();
            }
            URL url = new URL(updateURL);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(tmp);
            byte[] buffer = new byte[4096];
            int fetched;
            while ((fetched = is.read(buffer)) != -1) {
                os.write(buffer, 0, fetched);
            }
            is.close();
            os.flush();
            os.close();
            if (to.exists()) {
                to.delete();
            }
            tmp.renameTo(to);
            plugin.getLogger().info("Update complete. Please restart the server.");
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to update!");
        }
    }

}