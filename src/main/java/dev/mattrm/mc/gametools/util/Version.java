package dev.mattrm.mc.gametools.util;

import org.bukkit.Bukkit;

public class Version {
    public static boolean is8() {
        return Bukkit.getBukkitVersion().contains("1.8");
    }

    public static boolean is16() {
        return Bukkit.getBukkitVersion().contains("1.16");
    }

    public static int getJavaVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }
}
