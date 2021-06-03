package dev.mattrm.mc.gametools.readyup;

import dev.mattrm.mc.gametools.CommandGroup;
import dev.mattrm.mc.gametools.readyup.commands.ReadyUpCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadyUpCommands extends CommandGroup {
    @Override
    public void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("ready").setExecutor(new ReadyUpCommand());
    }
}
