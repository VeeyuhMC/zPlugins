package zplugin.zguns.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import zplugin.zguns.guns.Gun;

@SuppressWarnings("unused")
public class PlayerInteractListener implements Listener {

    public PlayerInteractListener() {}

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (Gun.isGun(player.getItemInHand())) {
            Gun gun = Gun.getGunType(player.getItemInHand());
            gun.shoot(player);
        }

    }

}
