package cf.bluebracket.znexusfactions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import cf.bluebracket.znexusfactions.api.Faction;

public class LeaveFactionEvent extends Event implements Cancellable {

    private HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private Player player;
    private Faction faction;
    private String playerMessage;
    private String factionMessage;

    public LeaveFactionEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
        this.playerMessage = "§6You left the faction §5" + faction.getName();
        this.factionMessage = "§9" + player.getName() + " §left the faction!";
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
		return player;
	}

	public Faction getFaction() {
		return faction;
	}

    public String getPlayerMessage() {
        return playerMessage;
    }

    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }

    public String getFactionMessage() {
        return factionMessage;
    }

    public void setFactionMessage(String factionMessage) {
        this.factionMessage = factionMessage;
    }

}
