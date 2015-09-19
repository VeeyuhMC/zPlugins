package zplugin.zheightboosts.util;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import zplugin.zheightboosts.zHeightBoosts;

import java.util.ArrayList;
import java.util.List;


public class Methods {

    zHeightBoosts plugin;

    public Methods(zHeightBoosts plugin) {
        this.plugin = plugin;
    }

    public List<PotionEffect> getBoosts(Player player) {

        double height = player.getLocation().getY();

        List<PotionEffect> boosts = new ArrayList<>();

        String rank = getRank(player);

        for (Object Height : plugin.getConfig().getConfigurationSection(rank).getKeys(false)) {

            String[] rangeList = Height.toString().split("-");

            int min = Integer.parseInt(rangeList[0]);
            int max = Integer.parseInt(rangeList[1]);

            int[] range = new int[max - min];

            for (int i = min; min <= max; i++) {
                range[i - min] = i;
            }

            for (int number : range) {
                if (number == (int) height) {

                    for (Object Boost : plugin.getConfig().getConfigurationSection(rank + "." + Height.toString()).getKeys(false)) {

                        PotionEffect boost = new PotionEffect(PotionEffectType.getByName(Boost.toString()),
                                1, plugin.getConfig().getInt(rank + "." + Height.toString() + "." + Boost.toString()));

                        boosts.add(boost);

                    }

                }
            }

        }

        return (boosts.isEmpty() ? null : boosts);

    }

    public String getRank(Player player) {

        String rank = null;

        for (PermissionAttachmentInfo pio : player.getEffectivePermissions()) {
            String perm = pio.getPermission();
            if (perm.startsWith("zheightboosts.rank")) {
                String[] perms = perm.split(".");
                for (Object Rank : plugin.getConfig().getKeys(false)) {
                    if (Rank.toString().equalsIgnoreCase(perms[2])) {
                        rank = Rank.toString();
                    }
                }
            }
        }

        return rank;

    }

}
