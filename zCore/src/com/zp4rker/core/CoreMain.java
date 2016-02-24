package com.zp4rker.core;

import com.zp4rker.core.inv.listener.InvClickListener;
import com.zp4rker.core.inv.listener.InvCloseListener;
import com.zp4rker.core.inv.listener.InvOpenListener;
import com.zp4rker.core.pckt.Injector;
import com.zp4rker.core.pckt.handler.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class CoreMain extends JavaPlugin implements Listener {

    private static CoreMain plugin;
    private static BukkitRunnable cleanupTask;
    private static Injector injector;
    private static boolean disableEvents = false;

    @Override
    public void onLoad() {
        injector = new Injector();
        boolean injected = injector.inject();
        if (injected) {
            injector.addServerConnectionChannel();
            System.out.println("[PacketListenerAPI] Injected custom Channel handler.");
        } else {
            System.err.println("[PacketListenerAPI] Failed to inject custom Channel handler!");
        }

    }

    public void onEnable() {

        CoreMain.plugin = this;

        registerEvent(this);
        registerEvent(new InvClickListener());
        registerEvent(new InvOpenListener());
        registerEvent(new InvCloseListener());

        this.saveDefaultConfig();
        disableEvents = this.getConfig().getBoolean("disableEvents", disableEvents);

        System.out.println("[PacketListenerAPI] Adding channels for all online players...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            injector.addChannel(p);
        }

        if (cleanupTask == null) {
            (cleanupTask = new BukkitRunnable() {

                @Override
                public void run() {
                    // System.out.println("[PacketListenerAPI] Running cleanup task...");
                    int handlers = PacketHandler.getHandlers().size();
                    int plugins = Bukkit.getPluginManager().getPlugins().length;
                    if (handlers > plugins) {
                        System.out.println("[PacketListenerAPI] There are more registered PacketHandlers than enabled plugins! ( " + handlers + " > " + plugins + " ) This could mean that some of the installed plugins are not un-registering their listeners correclty.");
                    }
                }
            }).runTaskTimer(this, 20 * 300, 20 * 300);
        }

    }

    public void onDisable() {
        System.out.println("[PacketListenerAPI] Removing channels for all online players...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            injector.removeChannel(p);
        }
        System.out.println("[PacketListenerAPI] Removing all (" + PacketHandler.getHandlers().size() + ") packet handlers...");
        while (!PacketHandler.getHandlers().isEmpty()) {
            PacketHandler.removeHandler(PacketHandler.getHandlers().get(0));
        }
    }

    public static CoreMain getPlugin() {

        return CoreMain.plugin;

    }

    private void registerEvent(Listener listener) {

        getServer().getPluginManager().registerEvents(listener, this);

    }

}