package zplugin.znexusfactions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import zplugin.znexusfactions.api.Faction;

public class EnterFactionEvent extends Event implements Cancellable {

    private HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private Player player;
    private Faction faction;
    private String message;

    public EnterFactionEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
        this.message = "§9" + faction.getName() + " ~ " + faction.getTag();
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}