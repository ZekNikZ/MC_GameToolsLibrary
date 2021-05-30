package dev.mattrm.mc.gametools.teams.commands;

import dev.mattrm.mc.gametools.teams.TeamService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.annotation.command.Commands;

import java.util.Arrays;
import java.util.stream.Collectors;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(
        name = "removeteam",
        desc = "Remove a team",
        usage = "/removeteam <id>"
))
public class RemoveTeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return false;
        }

        TeamService.getInstance().removeTeam(args[0]);

        sender.sendMessage(ChatColor.GRAY + "Successfully removed team '" + args[0] + "'");

        return true;
    }
}
