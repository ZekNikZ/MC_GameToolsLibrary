package dev.mattrm.mc.gametools.teams.commands;

import dev.mattrm.mc.gametools.teams.GameTeam;
import dev.mattrm.mc.gametools.teams.TeamService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Commands;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(
        name = "leaveteam",
        desc = "Leave team",
        usage = "/leaveteam <player> [player...]"
))
public class LeaveTeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            Player player = Bukkit.getPlayer(args[i]);
            if (player != null) {
                TeamService.getInstance().leaveTeam(player);
            } else {
                sender.sendMessage(ChatColor.RED + "Player '" + args[i] + "' is not online.");
            }
        }

        sender.sendMessage(ChatColor.GRAY + "Successfully added players to team '" + args[0] + "'");

        return true;
    }
}
