package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionDependent;
import dev.mattrm.mc.gametools.util.version.IVersioned;
import dev.mattrm.mc.gametools.util.version.VersionDependentClasses;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@VersionDependent
public interface TitleUtils extends IVersioned {
    static TitleUtils get() {
        return (TitleUtils) VersionDependentClasses.get(TitleUtils.class);
    }

    void sendActionBarMessage(@NotNull Player bukkitPlayer, @NotNull String message);

    void sendRawActionBarMessage(@NotNull Player bukkitPlayer, @NotNull String rawMessage);

    void sendActionBarMessage(@NotNull final Player bukkitPlayer, @NotNull final String message,
                              @NotNull final int duration, @NotNull Plugin plugin);

}
