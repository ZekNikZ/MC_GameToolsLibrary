package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionImplementation;
import org.bukkit.Sound;

@VersionImplementation
public class SoundsV16 implements Sounds {
    @Override
    public Sound notePling() {
        return Sound.BLOCK_NOTE_BLOCK_PLING;
    }
}
