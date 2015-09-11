package zplugin.zguns.guns;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class Gun {

    public static HashMap<Egg, Gun> bullets = new HashMap<>();

    private GunType gunType;
    private String name;

    private double damage;
    private double accuracy;
    private double speed;

    public Gun(GunType gunType, String name) {
        this.gunType = gunType;
        this.name = name;

        damage = gunType.getDamage();
        accuracy = gunType.getAccuracy();
        speed = gunType.getSpeed();
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public GunType getGunType() {
        return gunType;
    }

    public void shoot(Player player) {

        double randX, randY, randZ;
        randX = accuracy;
        randY = accuracy;
        randZ = accuracy;

        for (int i = 0; i < 5; i++) {
            Egg bullet = player.getWorld().spawn(player.getLocation().add(0, 1, 0), Egg.class);
            Vector velocity = new Vector().setX(randX).setY(randY).setZ(randZ);
            velocity.add(player.getLocation().getDirection().multiply(speed));
            bullet.setVelocity(velocity);
            bullet.setShooter(player);
            gunType.setOwner(player);
            bullets.put(bullet, this);
        }

    }

    public static List<Material> getGunItems() {
        List<Material> list = new ArrayList<>();
        list.add(Material.WOOD_AXE);    // P250 (PISTOL)
        list.add(Material.STONE_AXE);   // UMP45 (SHOTGUN)
        list.add(Material.IRON_AXE);    // M60 (MACHINE GUN)
        list.add(Material.GOLD_AXE);    // SCAR-20 (SNIPER RIFLE)
        list.add(Material.DIAMOND_AXE); // AK-47 (RIFLE)

        return list;
    }

    public static boolean isGun(ItemStack itemStack) {
        for (Material material : getGunItems()) {
            if (itemStack.getType().equals(material)) {
                return true;
            }
        }
        return false;
    }

    public static Gun getGunType(ItemStack itemStack) {
        if (itemStack.getType() == Material.WOOD_AXE) {
            return new P250();
        } else if(itemStack.getType() == Material.STONE_AXE) {
            return new UMP45();
        } else if(itemStack.getType() == Material.IRON_AXE) {
            return new M60();
        } else if(itemStack.getType() == Material.GOLD_AXE) {
            return new SCAR20();
        } else {
            return new AK47();
        }
    }

}
