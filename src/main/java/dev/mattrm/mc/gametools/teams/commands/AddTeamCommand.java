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
        name = "addteam",
        desc = "Add a team",
        usage = "/addteam <id> <prefix> <name>"
))
public class AddTeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            return false;
        }

        TeamService.getInstance().newTeam(args[0], Arrays.stream(args).skip(2).collect(Collectors.joining(" ")), args[1]);

        sender.sendMessage(ChatColor.GRAY + "Successfully created new team '" + args[0] + "'");

        return true;
    }
}
