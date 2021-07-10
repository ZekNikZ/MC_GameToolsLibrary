package dev.mattrm.mc.gametools.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import dev.mattrm.mc.gametools.VersionDependent;
import dev.mattrm.mc.gametools.VersionedInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@VersionDependent
public interface ActionBarUtils {
    @VersionedInstance
    ActionBarUtils INSTANCE = null;

    static ActionBarUtils getInstance() {
        return INSTANCE;
    }

    /**
     * Sends a message to the player's action bar.
     * <p/>
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param message      the message to send.
     */
    void sendActionBarMessage(@NotNull Player bukkitPlayer, @NotNull String message);

    /**
     * Sends a raw message (JSON format) to the player's action bar. Note: while the action bar accepts raw messages
     * it is currently only capable of displaying text.
     * <p/>
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param rawMessage   the json format message to send.
     */
    void sendRawActionBarMessage(@NotNull Player bukkitPlayer, @NotNull String rawMessage);

    abstract class Impl implements ActionBarUtils {
        private final Map<Player, BukkitTask> PENDING_MESSAGES = new HashMap<>();

        /**
         * Sends a message to the player's action bar that lasts for an extended duration.
         * <p/>
         * The message will appear above the player's hot bar for the specified duration and fade away during the last
         * second of the duration.
         * <p/>
         * Only one long duration message can be sent at a time per player. If a new message is sent via this message
         * any previous messages still being displayed will be replaced.
         *
         * @param bukkitPlayer the player to send the message to.
         * @param message      the message to send.
         * @param duration     the duration the message should be visible for in seconds.
         * @param plugin       the plugin sending the message.
         */
        public void sendActionBarMessage(@NotNull final Player bukkitPlayer, @NotNull final String message,
                                         @NotNull final int duration, @NotNull Plugin plugin) {
            cancelPendingMessages(bukkitPlayer);
            final BukkitTask messageTask = new BukkitRunnable() {
                private int count = 0;

                @Override
                public void run() {
                    if (count >= (duration - 3)) {
                        this.cancel();
                    }
                    sendActionBarMessage(bukkitPlayer, message);
                    count++;
                }
            }.runTaskTimer(plugin, 0L, 20L);
            PENDING_MESSAGES.put(bukkitPlayer, messageTask);
        }

        private void cancelPendingMessages(@NotNull Player bukkitPlayer) {
            if (PENDING_MESSAGES.containsKey(bukkitPlayer)) {
                PENDING_MESSAGES.get(bukkitPlayer).cancel();
            }
        }
    }
}
