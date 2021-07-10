package dev.mattrm.mc.gametools.readyup;

import dev.mattrm.mc.gametools.Service;
import dev.mattrm.mc.gametools.teams.GameTeam;
import dev.mattrm.mc.gametools.teams.TeamService;
import dev.mattrm.mc.gametools.util.ActionBarUtils;
import dev.mattrm.mc.gametools.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ReadyUpService extends Service {
    public static class ReadyUpSession {
        private final Map<UUID, Boolean> readyPlayers = new ConcurrentHashMap<>();
        private final Runnable onAllReady;
        private final BiConsumer<UUID, ReadyUpSession> onPlayerReady;
        private final int taskId;

        public ReadyUpSession(Runnable onAllReady, BiConsumer<UUID, ReadyUpSession> onPlayerReady) {
            this.onAllReady = onAllReady;
            this.onPlayerReady = onPlayerReady;
            this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance().getPlugin(), () -> {
                readyPlayers.entrySet().stream().filter(entry -> !entry.getValue()).forEach(entry -> {
                    Player player = Bukkit.getPlayer(entry.getKey());
                    if (player != null) {
                        ActionBarUtils.getInstance().sendActionBarMessage(player, ChatColor.GOLD + "Are you ready? Type /ready to confirm.");
                    }
                });
            }, 10, 10);
        }

        public long getReadyPlayerCount() {
            return readyPlayers.values().stream().filter(Boolean::booleanValue).count();
        }

        public long getTotalPlayerCount() {
            return readyPlayers.size();
        }

        public void cancel() {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    private static final ReadyUpService INSTANCE = new ReadyUpService();

    public static ReadyUpService getInstance() {
        return INSTANCE;
    }

    private int nextId = 0;
    private String prefix = "";
    private final Map<Integer, ReadyUpSession> sessions = new ConcurrentHashMap<>();

    public int waitForReady(Collection<UUID> players, Runnable onAllReady) {
        return this.waitForReady(players, onAllReady, null);
    }

    public int waitForReady(Collection<UUID> players, Runnable onAllReady, BiConsumer<UUID, ReadyUpSession> onPlayerReady) {
        ReadyUpSession session = new ReadyUpSession(Objects.requireNonNull(onAllReady), onPlayerReady);
        int id = this.nextId++;
        players.forEach(p -> session.readyPlayers.put(p, false));
        this.sessions.put(id, session);
        players.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                displayReadyMessage(player);
            }
        });
        return id;
    }

    public void cancelReadyWait(int id, boolean runHandler) {
        ReadyUpSession session = this.sessions.remove(id);
        if (session != null) {
            session.cancel();
            if (runHandler) {
                session.onAllReady.run();
            }
        }
    }

    public boolean recordReady(UUID player) {
        Set<Integer> doneSessions = new HashSet<>();

        boolean success = false;
        for (Map.Entry<Integer, ReadyUpSession> entry : this.sessions.entrySet()) {
            Integer key = entry.getKey();
            ReadyUpSession session = entry.getValue();
            if (!session.readyPlayers.containsKey(player)) {
                continue;
            }

            session.readyPlayers.put(player, true);
            success = true;
            session.readyPlayers.keySet().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(p -> {
                GameTeam playerTeam = TeamService.getInstance().getPlayerTeam(player);
                Player thePlayer = Bukkit.getPlayer(player);
                if (playerTeam != null) {
                    p.sendMessage(prefix + ChatColor.RESET + ChatColor.GRAY + playerTeam.getFormatCode() + ChatColor.BOLD + playerTeam.getPrefix() + " " + playerTeam.getFormatCode() + thePlayer.getDisplayName() + ChatColor.RESET + " is ready!");
                } else {
                    p.sendMessage(prefix + ChatColor.RESET + thePlayer.getDisplayName() + " is ready!");
                }
            });

            if (session.onPlayerReady != null) {
                session.onPlayerReady.accept(player, session);
            }

            if (session.readyPlayers.values().stream().allMatch(Boolean::booleanValue)) {
                doneSessions.add(key);
            }
        }

        doneSessions.forEach(id -> this.cancelReadyWait(id, true));
        return success;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if (this.sessions.values().stream().anyMatch(s -> s.readyPlayers.containsKey(event.getPlayer().getUniqueId()))) {
            displayReadyMessage(event.getPlayer());
        }
    }

    private void displayReadyMessage(Player player) {
        player.sendMessage(prefix + "Are you ready? Type /ready to confirm.");
        player.playSound(player.getLocation(), Sounds.getInstance().notePling(), 1, 1);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
