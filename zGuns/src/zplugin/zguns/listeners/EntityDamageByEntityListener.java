package zplugin.zguns.listeners;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import zplugin.zguns.guns.Gun;

@SuppressWarnings("unused")
public class EntityDamageByEntityListener implements Listener {

    public EntityDamageByEntityListener() {}

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        Entity damager = event.getDamager();

        if (damager.getType().equals(EntityType.SNOWBALL)) {

            for (Egg bullet : Gun.bullets.keySet()) {
                if (bullet.getEntityId() == damager.getEntityId()) {
                    Gun gun = Gun.bullets.get(bullet);
                    event.setDamage(gun.getDamage());
                }
            }

        }

    }

}
