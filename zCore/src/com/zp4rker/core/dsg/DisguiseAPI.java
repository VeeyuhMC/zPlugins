package com.zp4rker.core.dsg;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class DisguiseAPI {

    public void disguise(Player toHide, EntityType entityType, Player... bypass) {
        EntityLiving toSpawn = null;
        switch (entityType) {
            case BAT:
                toSpawn = new EntityBat(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case BLAZE:
                toSpawn = new EntityBlaze(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case CAVE_SPIDER:
                toSpawn = new EntityCaveSpider(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case CHICKEN:
                toSpawn = new EntityChicken(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case COW:
                toSpawn = new EntityCow(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case CREEPER:
                toSpawn = new EntityCreeper(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case ENDER_DRAGON:
                toSpawn = new EntityEnderDragon(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case ENDERMAN:
                toSpawn = new EntityEnderman(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case ENDERMITE:
                toSpawn = new EntityEndermite(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case GHAST:
                toSpawn = new EntityGhast(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case GIANT:
                toSpawn = new EntityGiantZombie(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case GUARDIAN:
                toSpawn = new EntityGuardian(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case HORSE:
                toSpawn = new EntityHorse(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case IRON_GOLEM:
                toSpawn = new EntityIronGolem(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case MAGMA_CUBE:
                toSpawn = new EntityMagmaCube(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case MUSHROOM_COW:
                toSpawn = new EntityMushroomCow(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case OCELOT:
                toSpawn = new EntityOcelot(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case PIG:
                toSpawn = new EntityPig(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case PIG_ZOMBIE:
                toSpawn = new EntityPigZombie(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case RABBIT:
                toSpawn = new EntityRabbit(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case SHEEP:
                toSpawn = new EntitySheep(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case SILVERFISH:
                toSpawn = new EntitySilverfish(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case SKELETON:
                toSpawn = new EntitySkeleton(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case SLIME:
                toSpawn = new EntitySlime(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case SNOWMAN:
                toSpawn = new EntitySnowman(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case SPIDER:
                toSpawn = new EntitySpider(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case SQUID:
                toSpawn = new EntitySquid(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case VILLAGER:
                toSpawn = new EntityVillager(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case WITCH:
                toSpawn = new EntityWitch(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case WITHER:
                toSpawn = new EntityWither(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case WOLF:
                toSpawn = new EntityWolf(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            case ZOMBIE:
                toSpawn = new EntityZombie(((CraftPlayer) toHide).getHandle().getWorld());
                break;
            default:
                throw new IllegalArgumentException(
                        "Mob must be a living entity other than a player! Provided: " + entityType.getName());
        }
        toSpawn.locX = toHide.getLocation().getX();
        toSpawn.locY = toHide.getLocation().getY();
        toSpawn.locZ = toHide.getLocation().getZ();
        try {
            Field a = toSpawn.getClass().getDeclaredField("a");
            a.setAccessible(true);
            a.set(toSpawn, ((CraftPlayer) toHide).getHandle().getUniqueID());
            a.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (a.equals(toHide)) {
                continue;
            }
            for (Player b : bypass) {
                if (b.equals(a)) {
                    continue;
                }
            }
            ((CraftPlayer) a).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(toSpawn.getId()));
            ((CraftPlayer) a).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(toSpawn));
        }

    }

}
