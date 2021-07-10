package dev.mattrm.mc.gametools.timer;

import org.bukkit.plugin.java.JavaPlugin;

public class GameTimer extends AbstractTimer {
    private final long timerValueMillis;
    private long startTime;
    private long time;
    private final Runnable onStop;

    public GameTimer(JavaPlugin plugin, long refreshRateTicks, long timerValueMillis, Runnable onStop) {
        super(plugin, refreshRateTicks);
        this.timerValueMillis = timerValueMillis;
        this.time = timerValueMillis;
        this.onStop = onStop;
    }

    public GameTimer(JavaPlugin plugin, long refreshRateTicks, long timerValueMillis) {
        this(plugin, refreshRateTicks, timerValueMillis, null);
    }

    @Override
    public GameTimer start() {
        this.startTime = System.currentTimeMillis();
        this.onUpdate();
        return (GameTimer) super.start();
    }

    @Override
    protected void onUpdate() {
        if (this.isDone()) {
            return;
        }

        this.time = this.timerValueMillis - (System.currentTimeMillis() - startTime);
        if (this.time <= 0) {
            this.time = 0;
            if (this.onStop != null) {
                this.onStop.run();
            }
            this.stop();
        }
    }

    @Override
    public long getAbsoluteMillis() {
        return this.time;
    }

    @Override
    public long getAbsoluteSeconds() {
        return this.time / 1000;
    }

    @Override
    public long getAbsoluteMinutes() {
        return this.time / 60000;
    }

    @Override
    public long getAbsoluteHours() {
        return this.time / 3600000;
    }
}
