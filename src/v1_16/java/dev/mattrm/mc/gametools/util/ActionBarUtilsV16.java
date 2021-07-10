package dev.mattrm.mc.gametools.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import dev.mattrm.mc.gametools.VersionImplementation;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

@VersionImplementation
public class ActionBarUtilsV16 extends ActionBarUtils.Impl {
    /**
     * Sends a message to the player's action bar.
     * <p/>
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param message      the message to send.
     */
    @Override
    public void sendActionBarMessage(@NotNull Player bukkitPlayer, @NotNull String message) {
        bukkitPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    /**
     * Sends a raw message (JSON format) to the player's action bar. Note: while the action bar accepts raw messages
     * it is currently only capable of displaying text.
     * <p/>
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param rawMessage   the json format message to send.
     */
    @Override
    public void sendRawActionBarMessage(@NotNull Player bukkitPlayer, @NotNull String rawMessage) {
        bukkitPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(rawMessage));
    }
}
