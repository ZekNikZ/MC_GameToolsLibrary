package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionImplementation;
import org.bukkit.Sound;

@VersionImplementation
public class SoundsV8 implements Sounds {
    @Override
    public Sound notePling() {
        return Sound.NOTE_PLING;
    }
}
