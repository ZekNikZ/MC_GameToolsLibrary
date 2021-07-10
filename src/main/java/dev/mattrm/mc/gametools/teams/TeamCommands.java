package dev.mattrm.mc.gametools.teams;

import dev.mattrm.mc.gametools.CommandGroup;
import dev.mattrm.mc.gametools.teams.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class TeamCommands implements CommandGroup {
    @Override
    public void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("addteam").setExecutor(new AddTeamCommand());
        plugin.getCommand("loadteams").setExecutor(new LoadTeamsCommand());
        plugin.getCommand("saveteams").setExecutor(new SaveTeamsCommand());
        plugin.getCommand("removeteam").setExecutor(new RemoveTeamCommand());
        plugin.getCommand("setupdefaultteams").setExecutor(new DefaultTeamsCommand());
        plugin.getCommand("jointeam").setExecutor(new JoinTeamCommand());
        plugin.getCommand("leaveteam").setExecutor(new LeaveTeamCommand());
    }
}
