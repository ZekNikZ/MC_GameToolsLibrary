package dev.mattrm.mc.gametools.readyup.commands;

import dev.mattrm.mc.gametools.readyup.ReadyUpService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Commands;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(
    name = "ready",
    desc = "Ready up!",
    usage = "/ready"
))
public class ReadyUpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from the console.");
            return true;
        }

        if (!ReadyUpService.getInstance().recordReady(((Player) sender).getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "Nothing is waiting for you to be ready.");
        } else {
            sender.sendMessage(ChatColor.GRAY + "You are now ready!");
        }

        return true;
    }
}
