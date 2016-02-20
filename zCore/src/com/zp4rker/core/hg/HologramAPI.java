package com.zp4rker.core.hg;

import com.zp4rker.core.CoreMain;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HologramAPI {

    CoreMain plugin = CoreMain.getPlugin();

    public Hologram newHologram(String... strings) {

        return new Hologram(plugin, strings);

    }

    public void showToAll(Hologram hologram, Location location, long ticks) {

        hologram.show(location, ticks);

    }

    public void showToPlayer(Hologram hologram, Location location, Player player, long ticks) {

        hologram.show(player, location, ticks);

    }

    public void changeText(Hologram hologram, String... strings) {

        hologram.change(strings);

    }

    public void remove(Hologram hologram) {

        hologram.remove();

    }

}
