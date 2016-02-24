package com.zp4rker.core.inv.listener;

import com.zp4rker.core.inv.gui.GUI;
import com.zp4rker.core.inv.gui.GUIRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;

public class InvCloseListener implements Listener {

    public static Map<GUI, GUIRunnable> guis = new HashMap<>();

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {

        if (guis.size() > 0) {

            for (GUI gui : guis.keySet()) {

                if (gui.getTitle() == event.getInventory().getTitle()) {

                    guis.get(gui).run(event);

                }

            }

        }

    }

}
