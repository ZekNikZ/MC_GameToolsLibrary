package dev.mattrm.mc.gametools.scoreboards;

import dev.mattrm.mc.gametools.Service;
import dev.mattrm.mc.gametools.teams.GameTeam;
import dev.mattrm.mc.gametools.teams.TeamService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class ScoreboardService extends Service {
    private static final ScoreboardService INSTANCE = new ScoreboardService();

    public static ScoreboardService getInstance() {
        return INSTANCE;
    }

    private GameScoreboard globalScoreboard = null;
    private final Map<String, GameScoreboard> teamScoreboards = new HashMap<>();
    private final Map<UUID, GameScoreboard> playerScoreboards = new HashMap<>();

    @Override
    protected void setupService() {

    }

    public void setGlobalScoreboard(GameScoreboard scoreboard) {
        this.setGlobalScoreboard(scoreboard, true);
    }

    public void setGlobalScoreboard(GameScoreboard scoreboard, boolean cleanup) {
        if (cleanup && this.globalScoreboard != null && teamScoreboards.values().stream().noneMatch(s -> s.equals(this.globalScoreboard)) && playerScoreboards.values().stream().noneMatch(s -> s.equals(this.globalScoreboard))) {
            this.globalScoreboard.cleanup();
        }

        this.globalScoreboard = scoreboard;

        Bukkit.getOnlinePlayers().forEach(this::updatePlayerScoreboard);
    }

    public void setTeamScoreboard(String teamId, GameScoreboard scoreboard) {
        GameScoreboard current = this.teamScoreboards.get(teamId);
        if (current != null && !this.globalScoreboard.equals(current) && teamScoreboards.values().stream().noneMatch(s -> s.equals(current)) && playerScoreboards.values().stream().noneMatch(s -> s.equals(current))) {
            current.cleanup();
        }

        if (scoreboard != null) {
            this.teamScoreboards.put(teamId, scoreboard);
        } else {
            this.teamScoreboards.remove(teamId);
        }

        TeamService.getInstance().getOnlineTeamMembers(teamId).forEach(this::updatePlayerScoreboard);
    }

    public void setPlayerScoreboard(UUID uuid, GameScoreboard scoreboard) {
        GameScoreboard current = this.playerScoreboards.get(uuid);
        if (current != null && !this.globalScoreboard.equals(current) && teamScoreboards.values().stream().noneMatch(s -> s.equals(current)) && playerScoreboards.values().stream().noneMatch(s -> s.equals(current))) {
            current.cleanup();
        }

        if (scoreboard != null) {
            this.playerScoreboards.put(uuid, scoreboard);
        } else {
            this.playerScoreboards.remove(uuid);
        }

        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            this.updatePlayerScoreboard(player);
        }
    }

    public Set<GameScoreboard> getAllScoreboards() {
        Set<GameScoreboard> scoreboards = new HashSet<>();
        if (this.globalScoreboard != null) {
            scoreboards.add(this.globalScoreboard);
        }
        this.teamScoreboards.values().forEach(s -> {
            if (s != null) {
                scoreboards.add(s);
            }
        });
        this.playerScoreboards.values().forEach(s -> {
            if (s != null) {
                scoreboards.add(s);
            }
        });
        return scoreboards;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerJoin(PlayerJoinEvent event) {
        this.updatePlayerScoreboard(event.getPlayer());
    }

    public GameScoreboard createNewScoreboard(String title) {
        GameScoreboard scoreboard = new GameScoreboard(title);
        this.setupTeamsOnScoreboard(scoreboard.getScoreboard());
        return scoreboard;
    }

    private void updatePlayerScoreboard(Player player) {
        GameScoreboard scoreboard = playerScoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            GameTeam team = TeamService.getInstance().getPlayerTeam(player);
            if (team != null) {
                scoreboard = teamScoreboards.get(team.getId());
            }
            if (scoreboard == null) {
                scoreboard = globalScoreboard;
            }
        }

        if (scoreboard != null) {
            player.setScoreboard(scoreboard.getScoreboard());
        } else {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    public void setupTeams() {
        this.setupTeamsOnScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        this.getAllScoreboards().forEach(scoreboard -> this.setupTeamsOnScoreboard(scoreboard.getScoreboard()));
        this.updateTeams();
    }

    private void setupTeamsOnScoreboard(Scoreboard scoreboard) {
        TeamService.getInstance().getTeams().forEach(gameTeam -> {
            Team oldTeam = scoreboard.getTeam(gameTeam.getId());
            if (oldTeam != null) {
                oldTeam.unregister();
            }
            Team team = scoreboard.registerNewTeam(gameTeam.getId());
            team.setPrefix("" + gameTeam.getFormatCode() + ChatColor.BOLD + gameTeam.getPrefix() + ChatColor.RESET + gameTeam.getFormatCode() + " ");
            team.setSuffix("" + ChatColor.RESET);
            team.setCanSeeFriendlyInvisibles(true);
        });
    }

    public void updateTeams() {
//        TeamService.getInstance().getTeams().forEach(gameTeam -> {
//            TeamService.getInstance().getTeamMembers(gameTeam).forEach(uuid -> Bukkit.getScoreboardManager().getMainScoreboard().getTeam(gameTeam.getId()).addEntry(Bukkit.getOfflinePlayer(uuid).getName()));
//        });
        this.updateTeamsOnScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        this.getAllScoreboards().forEach(scoreboard -> this.updateTeamsOnScoreboard(scoreboard.getScoreboard()));
    }

    private void updateTeamsOnScoreboard(Scoreboard scoreboard) {
        TeamService.getInstance().getTeams().forEach(gameTeam -> {
            Team team = scoreboard.getTeam(gameTeam.getId());
            team.getEntries().forEach(team::removeEntry);
            TeamService.getInstance().getTeamMembers(gameTeam).forEach(uuid -> team.addEntry(Bukkit.getOfflinePlayer(uuid).getName()));
        });

        Bukkit.getOnlinePlayers().forEach(this::updatePlayerScoreboard);
    }

    public void resetAllScoreboards() {
        this.getAllScoreboards().forEach(GameScoreboard::cleanup);
        this.globalScoreboard = null;
        this.teamScoreboards.clear();
        this.playerScoreboards.clear();
    }
}
