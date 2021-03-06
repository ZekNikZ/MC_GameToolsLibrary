package dev.mattrm.mc.gametools.timer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public abstract class AbstractTimer {
    private final Map<Integer, Runnable> hooks = new ConcurrentHashMap<>();
    private final Map<Integer, Consumer<Integer>> tempHooks = new ConcurrentHashMap<>();
    private final JavaPlugin plugin;
    private final long refreshRate;
    private int taskId = -1;
    private boolean isStarted = false;
    private boolean isDone = false;
    private int nextHookId = 0;

    protected AbstractTimer(JavaPlugin plugin, long refreshRateTicks) {
        this.plugin = plugin;
        this.refreshRate = refreshRateTicks;
    }

    private void update() {
        this.onUpdate();
        this.hooks.values().forEach(Runnable::run);
        this.tempHooks.forEach((id, hook) -> hook.accept(id));
    }

    protected void onUpdate() {

    }

    public AbstractTimer start() {
        if (this.taskId == -1) {
            this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                this.plugin,
                this::update,
                this.refreshRate,
                this.refreshRate
            );
            this.isStarted = true;
            this.isDone = false;
        }

        return this;
    }

    public void pause() {
        if (this.taskId != -1) {
            Bukkit.getScheduler().cancelTask(this.taskId);
            this.taskId = -1;
        }
    }

    public void stop() {
        if (this.taskId != -1) {
            Bukkit.getScheduler().cancelTask(this.taskId);
            this.taskId = -1;
            this.isStarted = false;
            this.isDone = true;
        }
    }

    public boolean isRunning() {
        return this.taskId != -1;
    }

    public boolean isPaused() {
        return this.isStarted && !this.isStarted();
    }

    public boolean isStarted() {
        return this.isStarted;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public int addHook(Runnable hook) {
        this.hooks.put(++nextHookId, hook);
        return nextHookId;
    }

    public int addTempHook(Consumer<Integer> hook) {
        this.tempHooks.put(++nextHookId, hook);
        return nextHookId;
    }

    public void removeHook(int hookId) {
        this.hooks.remove(hookId);
        this.tempHooks.remove(hookId);
    }

    public abstract long getAbsoluteMillis();

    public abstract long getAbsoluteSeconds();

    public abstract long getAbsoluteMinutes();

    public abstract long getAbsoluteHours();

    public int getLocalMillis() {
        return (int) (this.getAbsoluteMillis() % 1000);
    }

    public int getLocalSeconds() {
        return (int) (this.getAbsoluteSeconds() % 60);
    }

    public int getLocalMinutes() {
        return (int) (this.getAbsoluteMinutes() % 60);
    }

    public int getLocalHours() {
        return (int) this.getAbsoluteHours();
    }

    /**
     * Format:
     * H, h     hour-of-day (0-23)          number            0
     * M, m     minute-of-hour              number            30
     * S, s     second-of-minute            number            55
     * L, l     millisecond-of-second       fraction          978
     *
     * @param format the format string
     * @return the formatted string
     */
    public String format(String format) {
        return format
            .replaceAll("%H", String.format("%02d", this.getLocalHours()))
            .replaceAll("%h", "" + this.getLocalHours())
            .replaceAll("%M", String.format("%02d", this.getLocalMinutes()))
            .replaceAll("%m", "" + this.getLocalMinutes())
            .replaceAll("%S", String.format("%02d", this.getLocalSeconds()))
            .replaceAll("%s", "" + this.getLocalSeconds())
            .replaceAll("%L", String.format("%03d", this.getLocalMillis()))
            .replaceAll("%l", "" + this.getLocalMillis());
    }

    public String toString() {
        if (this.getLocalHours() > 0) {
            return this.format("%h:%M:%S");
        }

        return this.format("%m:%S");
    }
}
