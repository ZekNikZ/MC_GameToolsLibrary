package dev.mattrm.mc.gametools.timer;

import org.bukkit.plugin.java.JavaPlugin;

public class GameStopwatch extends AbstractTimer {
    private long startTime;
    private long time;

    public GameStopwatch(JavaPlugin plugin, long refreshRateTicks) {
        super(plugin, refreshRateTicks);
    }

    @Override
    public GameStopwatch start() {
        this.startTime = System.currentTimeMillis();
        this.onUpdate();
        return (GameStopwatch) super.start();
    }

    @Override
    protected void onUpdate() {
        this.time = System.currentTimeMillis() - startTime;
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
