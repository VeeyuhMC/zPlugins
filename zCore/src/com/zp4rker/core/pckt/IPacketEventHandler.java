package com.zp4rker.core.pckt;

import org.bukkit.entity.Player;

public interface IPacketEventHandler {

	public Object onPacketReceive(Player p, Object packet, Cancellable cancellable);

	public Object onPacketSend(Player p, Object packet, Cancellable cancellable);

}
