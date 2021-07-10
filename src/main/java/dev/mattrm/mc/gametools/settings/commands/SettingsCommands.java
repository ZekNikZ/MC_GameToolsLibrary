package dev.mattrm.mc.gametools.settings.commands;

import dev.mattrm.mc.gametools.CommandGroup;
import org.bukkit.plugin.java.JavaPlugin;

public class SettingsCommands implements CommandGroup {
    @Override
    public void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("settings").setExecutor(new OpenSettingsMenuCommand());
    }
}
