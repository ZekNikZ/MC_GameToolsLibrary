package dev.mattrm.mc.gametools.teams.commands;

import dev.mattrm.mc.gametools.teams.TeamService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.annotation.command.Commands;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(
        name = "saveteams",
        desc = "Save team data",
        usage = "/saveteams"
))
public class SaveTeamsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TeamService.getInstance().saveData();
        sender.sendMessage(ChatColor.GRAY + "Successfully saved teams data.");

        return true;
    }
}
