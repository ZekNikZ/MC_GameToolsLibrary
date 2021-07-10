package dev.mattrm.mc.gametools.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityUtils {
    public static boolean isHostile(Entity entity) {
        EntityType type = entity.getType();

        return type == EntityType.BLAZE || type == EntityType.CAVE_SPIDER || type == EntityType.CREEPER || type == EntityType.ENDERMAN || type == EntityType.ENDERMITE || type == EntityType.GHAST || type == EntityType.GIANT || type == EntityType.GUARDIAN || type == EntityType.SILVERFISH || type == EntityType.SKELETON || type == EntityType.SLIME || type == EntityType.SPIDER || type == EntityType.WITCH || type == EntityType.WITHER || type == EntityType.ZOMBIE;
    }
}
