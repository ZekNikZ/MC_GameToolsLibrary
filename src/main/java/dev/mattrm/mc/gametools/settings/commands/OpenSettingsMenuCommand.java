package dev.mattrm.mc.gametools.settings.commands;

import dev.mattrm.mc.gametools.settings.GameSettingsMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Commands;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(
        name = "settings",
        aliases = {"menu"},
        desc = "Open the game menu",
        usage = "/settings"
))
public class OpenSettingsMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Not a player");
            return true;
        }

        GameSettingsMenu.openMenu(((Player) sender), sender.isOp());

        return true;
    }
}
