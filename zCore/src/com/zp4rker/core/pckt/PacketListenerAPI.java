/*
 * Copyright 2015 Marvin Schäfer (inventivetalent). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package de.inventivegames.packetlistener;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.MetricsLite;

import de.inventivegames.packetlistener.event.PacketReceiveEvent;
import de.inventivegames.packetlistener.event.PacketSendEvent;
import de.inventivegames.packetlistener.handler.PacketHandler;
import de.inventivegames.packetlistener.handler.ReceivedPacket;
import de.inventivegames.packetlistener.handler.SentPacket;

/**
 *
 * © Copyright 2015 inventivetalent
 *
 * @author inventivetalent
 *
 */
@SuppressWarnings("deprecation")
public class PacketListenerAPI extends JavaPlugin implements IPacketEventHandler, Listener {

	private static PacketListenerAPI	instance;

	public static BukkitRunnable		cleanupTask;

	static Injector						injector;

	static boolean						disableEvents	= false;

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

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);

		try {
			MetricsLite metrics = new MetricsLite(this);
			if (metrics.start()) {
				System.out.println("[PacketListenerAPI] Metrics started.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public static PacketListenerAPI getInstance() {
		return instance;
	}

	@Override
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

	/**
	 * @see PacketHandler#addHandler(PacketHandler)
	 */
	public static boolean addPacketHandler(PacketHandler handler) {
		return PacketHandler.addHandler(handler);
	}

	/**
	 * @see PacketHandler#removeHandler(PacketHandler)
	 */
	public static boolean removePacketHandler(PacketHandler handler) {
		return PacketHandler.removeHandler(handler);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		injector.addChannel(e.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		injector.removeChannel(e.getPlayer());
	}

	private void callEvent(final Event e) {
		if (disableEvents) return;
		if (!this.isEnabled()) return;
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {

			@Override
			public void run() {
				try {
					Bukkit.getPluginManager().callEvent(e);
				} catch (IllegalStateException ex) {
					System.out.println("[PacketListenerAPI] Error while calling event (" + e.getEventName() + ")");
					ex.printStackTrace();
				}
			}
		});
	}

	@Override
	public Object onPacketReceive(Player p, Object packet, Cancellable cancellable) {
		if (!packet.getClass().getName().startsWith("net.minecraft.server.")) return packet;
		if (!disableEvents) {
			PacketReceiveEvent event = new PacketReceiveEvent(packet, cancellable, p);
			this.callEvent(event);
		}

		ReceivedPacket pckt = new ReceivedPacket(packet, cancellable, p);
		PacketHandler.notifyHandlers(pckt);
		return pckt.getPacket();
	}

	@Override
	public Object onPacketSend(Player p, Object packet, Cancellable cancellable) {
		if (!packet.getClass().getName().startsWith("net.minecraft.server.")) return packet;
		if (!disableEvents) {
			PacketSendEvent event = new PacketSendEvent(packet, cancellable, p);
			this.callEvent(event);
		}

		SentPacket pckt = new SentPacket(packet, cancellable, p);
		PacketHandler.notifyHandlers(pckt);
		return pckt.getPacket();
	}

}
