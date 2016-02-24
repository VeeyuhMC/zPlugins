package com.zp4rker.core.pckt.handler;

import org.bukkit.entity.Player;

import com.zp4rker.core.pckt.Cancellable;

/**
 * Wrapper class for sent packets
 *
 * @see Packet
 * @see ReceivedPacket
 */
public class SentPacket extends Packet {

	public SentPacket(Object packet, Cancellable cancel, Player player) {
		super(packet, cancel, player);
	}

}
