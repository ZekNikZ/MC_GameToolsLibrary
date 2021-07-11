package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionDependent;
import dev.mattrm.mc.gametools.util.version.IVersioned;
import dev.mattrm.mc.gametools.util.version.VersionDependentClasses;
import org.bukkit.Sound;

@VersionDependent
public interface Sounds extends IVersioned {
    static Sounds get() {
        return (Sounds) VersionDependentClasses.get(Sounds.class);
    }

    Sound notePling();
}
