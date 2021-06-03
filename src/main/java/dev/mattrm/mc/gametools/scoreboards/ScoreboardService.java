package dev.mattrm.mc.gametools.scoreboards;

import dev.mattrm.mc.gametools.Service;
import dev.mattrm.mc.gametools.teams.GameTeam;
import dev.mattrm.mc.gametools.teams.TeamService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        if (this.globalScoreboard != null && teamScoreboards.values().stream().noneMatch(s -> s.equals(this.globalScoreboard)) && playerScoreboards.values().stream().noneMatch(s -> s.equals(this.globalScoreboard))) {
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

        this.teamScoreboards.put(teamId, scoreboard);

        TeamService.getInstance().getOnlineTeamMembers(teamId).forEach(this::updatePlayerScoreboard);
    }

    public void setPlayerScoreboard(UUID uuid, GameScoreboard scoreboard) {
        GameScoreboard current = this.playerScoreboards.get(uuid);
        if (current != null && !this.globalScoreboard.equals(current) && teamScoreboards.values().stream().noneMatch(s -> s.equals(current)) && playerScoreboards.values().stream().noneMatch(s -> s.equals(current))) {
            current.cleanup();
        }

        this.playerScoreboards.put(uuid, scoreboard);

        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            this.updatePlayerScoreboard(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerJoin(PlayerJoinEvent event) {
        this.updatePlayerScoreboard(event.getPlayer());
    }

    public GameScoreboard createNewScoreboard(String title) {
        return new GameScoreboard(title);
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
}
