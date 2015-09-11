package zplugin.znocrit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerAttackListener implements Listener {

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if(PlayerFallListener.isFalling(player) && !player.isOnGround()) {
                event.setDamage((event.getDamage() / 3) * 2);
            }
        }

    }

}
