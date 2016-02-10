package com.theretronix.jointitle;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinTitle extends JavaPlugin implements Listener {

    public void onEnable() {

        if (getConfig().getString("firstLine") == null) {
            getConfig().options().copyDefaults(true);
        }

        getServer().getPluginManager().registerEvents(this, this);

    }

    public void onDisable() {
        //Do Nothing
    }

    public void onPlayerJoin(PlayerJoinEvent event) {

        IChatBaseComponent titleString = IChatBaseComponent.ChatSerializer.a(
                "{\"text\": \"" + getConfig().getString("firstLine") + "\"}"
        );
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleString, 20, 20*15, 20);

        IChatBaseComponent subTitleString = IChatBaseComponent.ChatSerializer.a(
                "{\"text\": \"" + getConfig().getString("secondLine") + "\"}"
        );
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleString, 20, 20*15, 20);

        ((CraftPlayer) (event.getPlayer())).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) (event.getPlayer())).getHandle().playerConnection.sendPacket(subTitle);

    }

}
