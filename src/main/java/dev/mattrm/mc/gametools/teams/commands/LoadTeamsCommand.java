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
        name = "loadteams",
        desc = "Load team data",
        usage = "/loadteams"
))
public class LoadTeamsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TeamService.getInstance().loadData();
        sender.sendMessage(ChatColor.GRAY + "Successfully loaded teams data.");

        return true;
    }
}
