package com.zp4rker.core.inv.listener;

import com.zp4rker.core.inv.gui.GUI;
import com.zp4rker.core.inv.gui.GUIRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

public class InvClickListener implements Listener {

    public static Map<GUI, GUIRunnable> guis = new HashMap<>();

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {

        if (guis.size() > 0) {

            for (GUI gui : guis.keySet()) {

                if (gui.getTitle() == event.getInventory().getTitle()) {

                    event.setCancelled(true);

                    guis.get(gui).run(event);

                }

            }

        }

    }

}
