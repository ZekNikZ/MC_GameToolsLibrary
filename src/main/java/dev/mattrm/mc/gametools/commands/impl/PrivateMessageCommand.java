package dev.mattrm.mc.gametools.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Commands;

import java.util.Arrays;
import java.util.stream.Collectors;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(
        name = "msg",
        aliases = {"tell", "w"},
        desc = "Privately message a player",
        usage = "/msg <player> <message>"
))
public class PrivateMessageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Not a player");
            return true;
        }

        Player recipient = Bukkit.getPlayer(args[0]);
        if (recipient == null) {
            sender.sendMessage("Player is not online");
            return true;
        }

        String message = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));

        recipient.sendMessage("" + ChatColor.GRAY + ChatColor.ITALIC + "[" + ((Player) sender).getDisplayName() + " ->] " + message);
        sender.sendMessage("" + ChatColor.GRAY + ChatColor.ITALIC + "[-> " + recipient.getDisplayName() + "] " + message);

        return true;
    }
}
