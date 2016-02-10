package com.theretronix.hubp.listeners;

import com.theretronix.hubp.api.GUIItems;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Field;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.getPlayer().teleport(event.getPlayer().getLocation().getWorld().getSpawnLocation().add(.5, .5, .5));

        PlayerInventory playerInv = event.getPlayer().getInventory();
        boolean hasCompass = false;

        for (ItemStack itemStack : playerInv.getContents()) {
            if (itemStack != null) {
                if (itemStack.getType() == Material.COMPASS) {
                    hasCompass = true;
                }
            }
        }

        if (!hasCompass) {

            event.getPlayer().getInventory().setItem(0, GUIItems.getCompass());
            event.getPlayer().getInventory().setItem(4, GUIItems.getStaff());

        }

        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a(
                "{\"text\": \"§6§lWelcomes you, §b§l" + event.getPlayer().getName() + "§6§l!\"}");
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"§9§lThe §4§lRetronix\"}");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle, 20, 20 * 15, 20);
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle, 20, 20 * 15, 20);
        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(subTitle);

        PacketPlayOutPlayerListHeaderFooter tabListPacket = getTabListPacket();
        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(tabListPacket);

    }

    Field bField;

    private PacketPlayOutPlayerListHeaderFooter getTabListPacket() {
        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a(
                "{\"text\": \"§9§lThe§4§lRetronix\"}"
        );
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a(
                "{\"text\": \"§6§lwww.theretronix.com\"}"
        );
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(header);
        try {
            if (bField == null) {
                bField = packet.getClass().getDeclaredField("b");
                bField.setAccessible(true);
            }
            bField.set(packet, footer);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.print("An error occurred!");
        }
        return packet;
    }

}
