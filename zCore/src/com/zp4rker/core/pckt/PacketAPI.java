package com.zp4rker.core.pckt;

import com.zp4rker.core.CoreMain;
import com.zp4rker.core.pckt.handler.PacketHandler;
import com.zp4rker.core.pckt.handler.ReceivedPacket;
import com.zp4rker.core.pckt.handler.SentPacket;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PacketAPI implements IPacketEventHandler {

    private CoreMain plugin;
    private static PacketAPI instance;

    public PacketAPI(CoreMain plugin) {
        this.plugin = plugin;
        PacketAPI.instance = this;
    }

    private void callEvent(final Event e) {
        if (!plugin.isEnabled()) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    Bukkit.getPluginManager().callEvent(e);
                } catch (IllegalStateException ex) {
                    System.out.println("Error while calling event (" + e.getEventName() + ")");
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public Object onPacketReceive(Player p, Object packet, Cancellable cancellable) {
        if (!packet.getClass().getName().startsWith("net.minecraft.server.")) {
            return packet;
        }
        ReceivedPacket pckt = new ReceivedPacket(packet, cancellable, p);
        PacketHandler.notifyHandlers(pckt);
        return pckt.getPacket();
    }

    @Override
    public Object onPacketSend(Player p, Object packet, Cancellable cancellable) {
        if (!packet.getClass().getName().startsWith("net.minecraft.server.")) {
            return packet;
        }
        SentPacket pckt = new SentPacket(packet, cancellable, p);
        PacketHandler.notifyHandlers(pckt);
        return pckt.getPacket();
    }

    public static PacketAPI getInstance() {
        return instance;
    }

    public void sendPacketAll(Packet packet) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void sendPacketAllExcept(Packet packet, Player except) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.equals(except)) {
                return;
            } else {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public void sendPacketAllWorld(Packet packet, World world) {
        for (Player player : world.getPlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void sendPacketAllWorldExcept(Packet packet, Player except) {
        for (Player player : except.getWorld().getPlayers()) {
            if (player.equals(except)) {
                return;
            } else {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

}
