package zplugin.znexusfactions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import zplugin.znexusfactions.api.Faction;

public class DisbandFactionEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private HandlerList handlerList = new HandlerList();

    private Player player;
    private Faction faction;
    private String playerMessage = "§6You disbanded §1" + faction.getName() + "§6!";
    private String factionMessage = "§6Your faction was disbanded by §1" + player.getName() + "§!";
    private String serverMessage = "§1" + faction.getName() + " §6was disbanded!";

    public DisbandFactionEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public String getFactionMessage() {
        return factionMessage;
    }

    public void setFactionMessage(String factionMessage) {
        this.factionMessage = factionMessage;
    }

    public String getPlayerMessage() {
        return playerMessage;
    }

    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
