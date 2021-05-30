package dev.mattrm.mc.gametools.timer;

import org.bukkit.plugin.java.JavaPlugin;

public class GameTimer extends AbstractTimer {
    private final long timerValueMillis;
    private long startTime;
    private long time;

    public GameTimer(JavaPlugin plugin, long refreshRateTicks, long timerValueMillis) {
        super(plugin, refreshRateTicks);
        this.timerValueMillis = timerValueMillis;
    }

    @Override
    public GameTimer start() {
        this.startTime = System.currentTimeMillis();
        this.onUpdate();
        return (GameTimer) super.start();
    }

    @Override
    protected void onUpdate() {
        this.time = this.timerValueMillis - (System.currentTimeMillis() - startTime);
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
