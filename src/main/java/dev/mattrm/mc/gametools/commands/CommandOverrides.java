package dev.mattrm.mc.gametools.commands;

import dev.mattrm.mc.gametools.CommandGroup;
import dev.mattrm.mc.gametools.commands.impl.PrivateMessageCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandOverrides extends CommandGroup {
    @Override
    public void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("msg").setExecutor(new PrivateMessageCommand());
    }
}
