package zplugin.znocrit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerFallListener implements Listener {

    private static List<Player> fallingPlayers = new ArrayList<>();

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (player.getFallDistance() >= 0) {

            if (player.getFallDistance() == 0) {
                if (fallingPlayers.contains(player)) {
                    fallingPlayers.remove(player);
                }
            } else {
                if (!fallingPlayers.contains(player)) {
                    fallingPlayers.add(player);
                }
            }

        }

    }

    public static boolean isFalling(Player player) {
        if (fallingPlayers.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

}
