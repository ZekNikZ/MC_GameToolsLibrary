package dev.mattrm.mc.gametools.scoreboards.impl;

import dev.mattrm.mc.gametools.scoreboards.GameScoreboard;
import dev.mattrm.mc.gametools.timer.AbstractTimer;

public class TimerEntry extends StringValueEntry {
    private final AbstractTimer timer;
    private final String timerFormat;
    private final int timerHookId;

    public TimerEntry(GameScoreboard scoreboard, AbstractTimer timer) {
        super(scoreboard, timer.toString());
        this.timer = timer;
        this.timerFormat = null;
        this.timerHookId = this.timer.addHook(this::updateHook);
    }

    public TimerEntry(GameScoreboard scoreboard, AbstractTimer timer, String timerFormat) {
        super(scoreboard, timer.toString());
        this.timer = timer;
        this.timerFormat = timerFormat;
        this.timerHookId = this.timer.addHook(this::updateHook);
    }

    public TimerEntry(GameScoreboard scoreboard, AbstractTimer timer, String format, ValuePos valuePos, String timerFormat) {
        super(scoreboard, format, valuePos, timer.toString());
        this.timer = timer;
        this.timerFormat = timerFormat;
        this.timerHookId = this.timer.addHook(this::updateHook);
    }

    void updateHook() {
        if (this.timerFormat != null) {
            this.setValue(this.timer.format(this.timerFormat));
        } else {
            this.setValue(this.timer.toString());
        }
    }

    @Override
    protected void cleanup() {
        this.timer.removeHook(this.timerHookId);
    }
}
