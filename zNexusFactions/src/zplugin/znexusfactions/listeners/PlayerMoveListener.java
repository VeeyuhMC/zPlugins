package zplugin.znexusfactions.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import zplugin.znexusfactions.api.Faction;
import zplugin.znexusfactions.zNexusFactions;

public class PlayerMoveListener implements Listener {

    zNexusFactions plugin;

    public PlayerMoveListener(zNexusFactions plugin) {
        this.plugin = plugin;
    }

    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (plugin.m.inFactionBase(player.getLocation().getBlock().getLocation())) {
            Faction faction = plugin.m.getFactionAtLocation(player.getLocation().getBlock().getLocation());
            player.sendMessage("§9" + faction.getName() + " ~ " + faction.getTag());
        }

    }

}
