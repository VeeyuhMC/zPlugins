package zplugin.znexusfactions.listeners;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import zplugin.znexusfactions.api.Faction;
import zplugin.znexusfactions.zNexusFactions;

public class PlayerChatListener implements Listener {

    private zNexusFactions plugin;

    public PlayerChatListener(zNexusFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        String prefix = "§2[" + plugin.m.getFaction(player).getName() + "]";

        event.setFormat(prefix + event.getFormat());

        if (plugin.v.factionChat.contains(player)) {

            event.setCancelled(true);

            Faction faction = plugin.m.getFaction(player).getFaction();

            for (OfflinePlayer offlineTarget : faction.getPlayers()) {

                Player target = Bukkit.getPlayer(offlineTarget.getUniqueId());

                if (target != null) {

                    target.sendMessage("§2" + player.getName() + ": §r");

                }

            }

        }

    }

}
