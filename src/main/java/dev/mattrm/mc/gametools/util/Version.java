package dev.mattrm.mc.gametools.util;

import org.bukkit.Bukkit;

public class Version {
    public static boolean is8() {
        return Bukkit.getBukkitVersion().contains("1.8");
    }

    public static boolean is16() {
        return Bukkit.getBukkitVersion().contains("1.16");
    }
}
