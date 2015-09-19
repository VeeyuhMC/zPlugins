package zplugin.zheightboosts.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import zplugin.zheightboosts.zHeightBoosts;

public class PlayerMoveListener implements Listener {

    private zHeightBoosts plugin;

    public PlayerMoveListener(zHeightBoosts plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (plugin.m.getBoosts(player) != null) {
            for (PotionEffect boost : plugin.m.getBoosts(player)) {
                player.addPotionEffect(boost);
            }
        }

    }

}
