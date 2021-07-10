package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionDependent;
import dev.mattrm.mc.gametools.VersionedInstance;
import org.bukkit.Sound;

@VersionDependent
public interface Sounds {
    @VersionedInstance
    Sounds INSTANCE = null;

    static Sounds getInstance() {
        return INSTANCE;
    }

    Sound notePling();
}
