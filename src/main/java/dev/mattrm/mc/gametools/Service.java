package dev.mattrm.mc.gametools;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Service implements Listener {
    protected JavaPlugin plugin;

    public final void setup(JavaPlugin plugin) {
        this.plugin = plugin;
        this.setupService();
        try {
            this.registerPacketListeners(ProtocolLibrary.getProtocolManager());
        } catch (NoClassDefFoundError e) {
            plugin.getLogger().warning("ProtocolLib was not found. Could not load packet listeners.");
        }
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    protected void registerPacketListeners(ProtocolManager protocolManager) {
    }

    protected void setupService() {};
}
