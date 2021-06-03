package dev.mattrm.mc.gametools.teams;

import com.comphenix.protocol.ProtocolManager;
import dev.mattrm.mc.gametools.Service;
import dev.mattrm.mc.gametools.data.DataService;
import dev.mattrm.mc.gametools.data.IDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

public class TeamService extends Service implements IDataManager {
    private static final TeamService INSTANCE = new TeamService();

    private final Map<String, GameTeam> teams = new HashMap<>();
    private final Map<UUID, String> players = new HashMap<>();

    public static TeamService getInstance() {
        return INSTANCE;
    }

    @Override
    protected void setupService() {
        DataService.getInstance().registerDataManager(this);
    }

    @Override
    protected void registerPacketListeners(ProtocolManager protocolManager) {

    }

    public GameTeam getPlayerTeam(Player player) {
        String team = this.players.get(player.getUniqueId());
        return team == null ? null : this.teams.get(team);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        GameTeam team = this.getPlayerTeam(event.getPlayer());
        if (team != null) {
            event.setFormat("<" + team.getFormatCode() + ChatColor.BOLD + team.getPrefix() + ChatColor.RESET + team.getFormatCode() + " %1$s" + ChatColor.RESET + "> %2$s");
        }
    }

    @Override
    public String getDataFileName() {
        return "teams";
    }

    @Override
    public void onLoad(ConfigurationSection config) {
        this.teams.clear();
        this.players.clear();

        ConfigurationSection teamSection = config.getConfigurationSection("teams");
        if (teamSection != null) {
            for (String key : teamSection.getKeys(false)) {
                this.newTeam(new GameTeam().deserialize(teamSection.getConfigurationSection(key)));
            }
        }

        ConfigurationSection playerSection = config.getConfigurationSection("players");
        if (playerSection != null) {
            for (String key : playerSection.getKeys(false)) {
                this.joinTeam(UUID.fromString(key), playerSection.getString(key));
            }
        }
    }

    @Override
    public void onSave(ConfigurationSection config) {
        ConfigurationSection teamRoot = config.createSection("teams");
        this.teams.forEach((key, team) -> {
            ConfigurationSection section = teamRoot.createSection(team.getSectionKey());
            team.serialize(section);
        });

        ConfigurationSection playerRoot = config.createSection("players");
        this.players.forEach((uuid, team) -> playerRoot.set(uuid.toString(), team));
    }

    public void newTeam(String id, String name, String prefix) {
        this.newTeam(new GameTeam(id, name, prefix));
    }

    public void newTeam(GameTeam team) {
        this.teams.put(team.getId(), team);

        Team oldTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team.getId());
        if (oldTeam != null) {
            oldTeam.unregister();
        }

        Team scoreboardTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(team.getId());
        scoreboardTeam.setPrefix("" + team.getFormatCode() + ChatColor.BOLD + team.getPrefix() + ChatColor.RESET + team.getFormatCode() + " ");
        scoreboardTeam.setDisplayName(team.getName());
    }

    public void removeTeam(String id) {
        this.teams.remove(id);

        // this.players.values().remove(id); would only remove the first instance
        this.players.values().removeAll(Collections.singleton(id));

        Team oldTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(id);
        if (oldTeam != null) {
            oldTeam.unregister();
        }
    }

    public void setupDefaultTeams() {
        this.teams.remove("blue");
        this.newTeam(new GameTeam("blue", "Blue Team", "BLUE"));
        this.teams.get("blue").setFormatCode(ChatColor.BLUE);

        this.teams.remove("red");
        this.newTeam(new GameTeam("red", "Red Team", "RED"));
        this.teams.get("red").setFormatCode(ChatColor.RED);

        this.teams.remove("GREEN");
        this.newTeam(new GameTeam("green", "Green Team", "GREEN"));
        this.teams.get("green").setFormatCode(ChatColor.GREEN);
    }

    public GameTeam getTeam(String id) {
        return this.teams.get(id);
    }

    public void joinTeam(Player player, GameTeam team) {
        if (this.getPlayerTeam(player) != null) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam(this.getPlayerTeam(player).getId()).removeEntry(player.getName());
        }

        this.joinTeam(player.getUniqueId(), team.getId());
    }

    public void joinTeam(UUID uuid, String teamId) {
        this.players.put(uuid, teamId);
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamId).addEntry(Bukkit.getOfflinePlayer(uuid).getName());
    }

    public void leaveTeam(Player player) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(this.getPlayerTeam(player).getId()).removeEntry(player.getName());
        this.players.remove(player.getUniqueId());
    }

    public List<UUID> getTeamMembers(String teamId) {
        return this.players.entrySet().stream()
                .filter((entry) -> teamId.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Player> getOnlineTeamMembers(String teamId) {
        return this.players.entrySet().stream()
                .filter((entry) -> teamId.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void clearTeam(String teamId) {
        this.players.entrySet().stream()
            .filter((entry) -> teamId.equals(entry.getValue()))
            .map(Map.Entry::getKey)
            .forEach(this.players::remove);
    }

    public void clearTeams() {
        this.players.clear();
    }
}
