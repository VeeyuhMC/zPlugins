/*
 * Copyright 2015 Marvin Sch�fer (inventivetalent). All rights reserved.
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

package com.zp4rker.core.pckt.channel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.zp4rker.core.pckt.Cancellable;
import com.zp4rker.core.pckt.reflection.NMSUtils;

public class NMUHandler implements Handler {

	private static Class<?>	entityPlayer		= NMSUtils.getNMSClass("EntityPlayer");
	private static Class<?>	playerConnection	= NMSUtils.getNMSClass("PlayerConnection");
	private static Class<?>	networkManager		= NMSUtils.getNMSClass("NetworkManager");

	private static Field	channelField		= getChannelField();
	private static Field	network				= NMSUtils.getField(playerConnection, "networkManager");
	private static Field	connection			= NMSUtils.getField(entityPlayer, "playerConnection");

	private static Field getChannelField() {
		Field channelField = null;
		try {
			channelField = NMSUtils.getFirstFieldOfTypeWithException(networkManager, Channel.class);
		} catch (Exception e) {
			System.out.print("Channel class not found");
		}
		if (channelField != null) {
			channelField.setAccessible(true);
		}
		return channelField;
	}

	@Override
	public void addChannel(final Player player) {
		try {
			final Object handle = NMSUtils.getHandle(player);
			final Object connection = NMUHandler.connection.get(handle);
			final Channel channel = (Channel) channelField.get(network.get(connection));
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						channel.pipeline().addBefore("packet_handler", "packet_listener_player", new ChannelHandler(player));
					} catch (Exception e) {
					}
				}
			}, "PacketListenerAPI channel adder (" + player.getName() + ")").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeChannel(final Player player) {
		try {
			final Object handle = NMSUtils.getHandle(player);
			final Object connection = NMUHandler.connection.get(handle);
			final Channel channel = (Channel) channelField.get(network.get(connection));
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						channel.pipeline().remove("packet_listener_player");
					} catch (Exception e) {
					}
				}
			}, "PacketListenerAPI channel remover (" + player.getName() + ")").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	class ListenerList<E> extends ArrayList<E> {

		@Override
		public boolean add(E paramE) {
			try {
				final E a = paramE;
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Channel channel = null;
							while (channel == null) {
								channel = (Channel) channelField.get(a);
							}
							if (channel.pipeline().get("packet_listener_server") == null) {
								channel.pipeline().addBefore("packet_handler", "packet_listener_server", new ChannelHandler(null));
							}
						} catch (Exception e) {
						}
					}
				}, "PacketListenerAPI channel adder (server)").start();
			} catch (Exception e) {
			}
			return super.add(paramE);
		}

		@Override
		public boolean remove(Object arg0) {
			try {
				final Object a = arg0;
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Channel channel = null;
							while (channel == null) {
								channel = (Channel) channelField.get(a);
							}
							channel.pipeline().remove("packet_listener_server");
						} catch (Exception e) {
						}
					}
				}, "PacketListenerAPI channel remover (server)").start();
			} catch (Exception e) {
			}
			return super.remove(arg0);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addServerConnectionChannel() {
		try {
			Server server = Bukkit.getServer();
			Object dedicatedserver = NMSUtils.getMethod(server.getClass(), "getServer").invoke(server);
			Class<?> serverconnectionclass = NMSUtils.getNMSClass("ServerConnection");
			Object serverconnection = NMSUtils.getFirstFieldOfType(NMSUtils.getNMSClass("MinecraftServer"), serverconnectionclass).get(dedicatedserver);
			Field f = NMSUtils.getLastFieldOfType(serverconnectionclass, List.class);
			List currentlist = (List<?>) f.get(serverconnection);
			Field f1 = NMSUtils.getField(currentlist.getClass().getSuperclass(), "list");
			Object list = f1.get(currentlist);
			if (list.getClass().equals(ListenerList.class)) return;
			List newlist = Collections.synchronizedList(new ListenerList());
			for (Object o : currentlist) {
				newlist.add(o);
			}
			f.set(serverconnection, newlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Class<?>	packet	= NMSUtils.getNMSClass("Packet");

	class ChannelHandler extends ChannelDuplexHandler {

		private Player	player;

		public ChannelHandler(Player p) {
			this.player = p;
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			Cancellable cancellable = new Cancellable();
			Object pckt = msg;
			if (packet.isAssignableFrom(msg.getClass())) {
				pckt = PacketListenerAPI.getInstance().onPacketSend(this.player, msg, cancellable);
			}
			if (cancellable.isCancelled()) return;
			super.write(ctx, pckt, promise);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			Cancellable cancellable = new Cancellable();
			Object pckt = msg;
			if (packet.isAssignableFrom(msg.getClass())) {
				pckt = PacketListenerAPI.getInstance().onPacketReceive(this.player, msg, cancellable);
			}
			if (cancellable.isCancelled()) return;
			super.channelRead(ctx, pckt);
		}

		@Override
		public String toString() {
			return "ChannelHandler (" + this.player + ")";
		}
	}

}
