package zplugin.zguns.guns;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public enum GunType {

    PISTOL(2, 0, 5), RIFLE(1, 0.1, 5), SHOTGUN(2, 0.1, 5), SNIPER_RIFLE(10, 0.01, 5), MACHINE_GUN(3, 0.1, 5);

    private double damage;
    private double accuracy;
    private double speed;
    private Player owner;

    GunType(double damage, double accuracy, double speed) {
        this.damage = damage;
        this.accuracy = accuracy;
        this.speed = speed;
    }

    public double getDamage() {
        return damage;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getSpeed() {
        return speed;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        owner = player;
    }

}
