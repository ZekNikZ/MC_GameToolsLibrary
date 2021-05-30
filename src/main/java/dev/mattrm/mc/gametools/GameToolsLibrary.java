package dev.mattrm.mc.gametools;

import dev.mattrm.mc.gametools.commands.CommandOverrides;
import dev.mattrm.mc.gametools.data.DataService;
import dev.mattrm.mc.gametools.scoreboards.ScoreboardService;
import dev.mattrm.mc.gametools.teams.TeamCommands;
import dev.mattrm.mc.gametools.teams.TeamService;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "GameToolsLibrary", version = "1.0")
@Author("ZekNikZ")
@Dependency("ProtocolLib")
public final class GameToolsLibrary extends JavaPlugin {
    @Override
    public void onEnable() {
        registerServices();
        registerCommands();
        DataService.getInstance().loadAll();
        System.out.println("Done loading");
    }

    @Override
    public void onDisable() {
        DataService.getInstance().saveAll();
    }

    private void registerServices() {
        DataService.getInstance().setup(this);

        Service[] services = new Service[]{TeamService.getInstance(), ScoreboardService.getInstance()};

        PluginManager pluginManager = this.getServer().getPluginManager();
        for (Service service : services) {
            service.setup(this);
            pluginManager.registerEvents(service, this);
        }
    }

    private void registerCommands() {
        new TeamCommands().registerCommands(this);
        new CommandOverrides().registerCommands(this);
    }
}
