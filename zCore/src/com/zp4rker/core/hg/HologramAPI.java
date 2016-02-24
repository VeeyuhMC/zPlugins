package com.zp4rker.core.hg;

import com.zp4rker.core.CoreMain;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HologramAPI {

    CoreMain plugin = CoreMain.getPlugin();

    /**
     *
     * Creates a hologram
     *
     * @param strings The text that will be shown in the hologram
     * @return A hologram
     *
     */
    public Hologram newHologram(String... strings) {

        return new Hologram(plugin, strings);

    }

    /**
     *
     * Shows the spcecified hologram to all players in the world
     *
     * @param hologram The hologram to be shown
     * @param ticks The delay in ticks before the hologram is shown
     *
     */
    public void showToAll(Hologram hologram, long ticks) {

        Location location = hologram.getLocation();

        hologram.show(location, ticks);

    }

    /**
     *
     * Shows the specified hologram to a specific player
     *
     * @param hologram The hologram to be shown
     * @param player The player to show it to
     * @param ticks The delay in ticks before the hologram is shown
     *
     */
    public void showToPlayer(Hologram hologram, Player player, long ticks) {

        Location location = hologram.getLocation();

        hologram.show(player, location, ticks);

    }

    /**
     *
     * Changes the text of the specified hologram
     *
     * @param hologram The hologram to be modified
     * @param strings The new text for the hologram
     *
     */
    public void changeText(Hologram hologram, String... strings) {

        hologram.change(strings);

    }

    /**
     *
     * Removes the specified hologram from the world
     *
     * @param hologram The hologram to be removed
     *
     */
    public void remove(Hologram hologram) {

        hologram.remove();

    }

}
