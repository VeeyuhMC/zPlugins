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

package com.zp4rker.core.pckt.event;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.zp4rker.core.pckt.Cancellable;
import com.zp4rker.core.pckt.handler.PacketHandler;

/**
 * @deprecated now handled by {@link PacketHandler}
 */
@Deprecated
public class PacketReceiveEvent extends Event {

	private static HandlerList	handlers	= new HandlerList();
	private Player				player;
	private Object				packet;
	private Cancellable			cancel;

	public PacketReceiveEvent(Object packet, Cancellable cancel, Player player) {
		super(true);
		this.player = player;
		this.packet = packet;
		this.cancel = cancel;
	}

	public void setPacketValue(String field, Object value) {
		try {
			Field f = this.packet.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(this.packet, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getPacketValue(String field) {
		Object value = null;
		try {
			Field f = this.packet.getClass().getDeclaredField(field);
			f.setAccessible(true);
			value = f.get(this.packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public void setCancelled(boolean b) {
		this.cancel.setCancelled(b);
	}

	public boolean isCancelled() {
		return this.cancel.isCancelled();
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getPlayername() {
		return this.player.getName();
	}

	public void setPacket(Object packet) {
		this.packet = packet;
	}

	public Object getPacket() {
		return this.packet;
	}

	public String getPacketName() {
		return this.packet.getClass().getSimpleName();
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
