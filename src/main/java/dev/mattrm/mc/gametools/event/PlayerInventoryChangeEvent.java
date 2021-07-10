package dev.mattrm.mc.gametools.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInventoryChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final PlayerInventory inventory;
    private final Cancellable event;

    public PlayerInventoryChangeEvent(Player player, PlayerInventory inventory) {
        this(player, inventory, null);
    }

    public <T extends Event & Cancellable> PlayerInventoryChangeEvent(Player player, PlayerInventory inventory, T event) {
        this.player = player;
        this.inventory = inventory;
        this.event = event;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerInventory getInventory() {
        return this.inventory;
    }

    public Cancellable getCause() {
        return this.event;
    }

    @Override
    public boolean isCancelled() {
        return this.event != null && this.event.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        if (this.event != null) {
            this.event.setCancelled(cancel);
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
