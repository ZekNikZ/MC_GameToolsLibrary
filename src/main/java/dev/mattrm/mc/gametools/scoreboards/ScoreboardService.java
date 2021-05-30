package dev.mattrm.mc.gametools.scoreboards;

import dev.mattrm.mc.gametools.Service;
import dev.mattrm.mc.gametools.data.SharedReference;
import dev.mattrm.mc.gametools.scoreboards.impl.IntValueEntry;
import dev.mattrm.mc.gametools.scoreboards.impl.SharedReferenceEntry;
import dev.mattrm.mc.gametools.scoreboards.impl.TimerEntry;
import dev.mattrm.mc.gametools.teams.GameTeam;
import dev.mattrm.mc.gametools.teams.TeamService;
import dev.mattrm.mc.gametools.timer.GameTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
    private Map<String, GameScoreboard> teamScoreboards = new HashMap<>();
    private Map<UUID, GameScoreboard> playerScoreboards = new HashMap<>();

    private SharedReference<Integer> totalClicks = new SharedReference<>(0);
    private Map<UUID, IntValueEntry> values = new HashMap<>();

    @Override
    protected void setupService() {

    }

    public void setGlobalScoreboard(GameScoreboard scoreboard) {
        this.globalScoreboard = scoreboard;

        Bukkit.getOnlinePlayers().forEach(this::updatePlayerScoreboard);
    }

    public void setTeamScoreboard(String teamId, GameScoreboard scoreboard) {
        this.teamScoreboards.put(teamId, scoreboard);

        TeamService.getInstance().getOnlineTeamMembers(teamId).forEach(this::updatePlayerScoreboard);
    }

    public void setPlayerScoreboard(UUID uuid, GameScoreboard scoreboard) {
        this.playerScoreboards.put(uuid, scoreboard);

        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            this.updatePlayerScoreboard(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerJoin(PlayerJoinEvent event) {
        this.setPlayerScoreboard(event.getPlayer().getUniqueId(), this.createNewScoreboard(event.getPlayer().getUniqueId()));
//        this.updatePlayerScoreboard(event.getPlayer());
    }

    private GameScoreboard createNewScoreboard(UUID uuid) {
        GameScoreboard scoreboard = new GameScoreboard("Boop");
        scoreboard.addEntry("Test 1");
        scoreboard.addEntry(ChatColor.RED + "Test 2");
        scoreboard.addSpace();
        scoreboard.addEntry(new SharedReferenceEntry<>(scoreboard, "TClicks: ", ValueEntry.ValuePos.SUFFIX, totalClicks));
        this.values.put(uuid, scoreboard.addEntry(new IntValueEntry(scoreboard, "Clicks: ", ValueEntry.ValuePos.SUFFIX, 0)));
        GameTimer timer = new GameTimer(this.plugin, 20, 61 * 60 * 1000).start();
        scoreboard.addSpace();
        scoreboard.addEntry(new TimerEntry(scoreboard, timer));
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

    @EventHandler
    private void onPlayerClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            this.totalClicks.setAndNotify(this.totalClicks.get() + 1);
            this.values.get(event.getPlayer().getUniqueId()).increment();
        }
    }
}
