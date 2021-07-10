package dev.mattrm.mc.gametools.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import dev.mattrm.mc.gametools.VersionImplementation;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@VersionImplementation
public class ActionBarUtilsV8 extends ActionBarUtils.Impl {
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
        sendRawActionBarMessage(bukkitPlayer, "{\"text\": \"" + message + "\"}");
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
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer actionBarPacket = protocolManager.createPacket(PacketType.Play.Server.CHAT);
        actionBarPacket.getBytes().write(0, (byte) 2);
        actionBarPacket.getChatComponents().write(0, WrappedChatComponent.fromJson(rawMessage));
        try {
            protocolManager.sendServerPacket(bukkitPlayer, actionBarPacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
