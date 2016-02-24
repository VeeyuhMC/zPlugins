package com.zp4rker.core.pckt.handler;

import org.bukkit.entity.Player;

import com.zp4rker.core.pckt.Cancellable;

/**
 * Wrapper class or received packets
 *
 * @see Packet
 * @see SentPacket
 */
public class ReceivedPacket extends Packet {

	public ReceivedPacket(Object packet, Cancellable cancel, Player player) {
		super(packet, cancel, player);
	}

}
