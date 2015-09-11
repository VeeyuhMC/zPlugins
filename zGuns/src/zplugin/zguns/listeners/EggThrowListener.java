package zplugin.zguns.listeners;

import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import zplugin.zguns.guns.Gun;

@SuppressWarnings("unused")
public class EggThrowListener implements Listener {

    public EggThrowListener() {}

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {

        Egg bullet = event.getEgg();

        for (Egg egg : Gun.bullets.keySet()) {
            if(egg.getEntityId() == bullet.getEntityId()) {
                event.setHatching(false);
            }
        }

    }

}
