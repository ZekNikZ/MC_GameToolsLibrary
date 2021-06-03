package dev.mattrm.mc.gametools.readyup;

import dev.mattrm.mc.gametools.Service;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class ReadyUpSession {
    public Map<UUID, Boolean> readyPlayers = new ConcurrentHashMap<>();
    public Runnable runnable;

    public ReadyUpSession(Runnable runnable) {
        this.runnable = runnable;
    }
}

public class ReadyUpService extends Service {
    private static final ReadyUpService INSTANCE = new ReadyUpService();

    public static ReadyUpService getInstance() {
        return INSTANCE;
    }

    private int nextId = 0;
    private final Map<Integer, ReadyUpSession> sessions = new ConcurrentHashMap<>();

    public int waitForReady(Collection<UUID> players, Runnable handler) {
        int id = this.nextId++;
        ReadyUpSession session = new ReadyUpSession(handler);
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
        if (session != null && runHandler) {
            session.runnable.run();
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
        // todo: improve
        player.sendMessage("Are you ready? Type /ready to confirm.");
    }
}
