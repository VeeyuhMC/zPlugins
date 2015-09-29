package zplugin.znexusfactions.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getEntity().getType() == EntityType.ENDER_CRYSTAL) {
            event.setCancelled(true);
        }

    }

}
