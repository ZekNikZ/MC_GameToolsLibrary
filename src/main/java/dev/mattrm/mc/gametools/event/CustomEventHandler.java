package dev.mattrm.mc.gametools.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomEventHandler implements Listener {
    private final JavaPlugin plugin;

    public CustomEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent((Player) event.getWhoClicked(), event.getWhoClicked().getInventory(), event));
        }, 1);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent((Player) event.getWhoClicked(), event.getWhoClicked().getInventory(), event));
        }, 1);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent((Player) event.getWhoClicked(), event.getWhoClicked().getInventory(), event));
        }, 1);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent(event.getPlayer(), event.getPlayer().getInventory(), event));
        }, 1);
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent(event.getPlayer(), event.getPlayer().getInventory(), event));
        }, 1);
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent(event.getPlayer(), event.getPlayer().getInventory()));
        }, 1);
    }

//    @EventHandler
//    public void onEntityPickupItem(EntityPickupItemEvent event) {
//        if (!(event.getEntity() instanceof Player)) {
//            return;
//        }
//
//        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
//            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent(((Player) event.getEntity()), ((Player) event.getEntity()).getInventory(), event));
//        }, 1);
//    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent((Player) event.getWhoClicked(), event.getWhoClicked().getInventory(), event));
        }, 1);
    }

    @EventHandler
    public void onUseBucket(PlayerInteractAtEntityEvent event) {
        if (event.getPlayer().getInventory().getItemInHand().getType() != Material.BUCKET && event.getPlayer().getInventory().getItemInHand().getType() != Material.WATER_BUCKET) {
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerInventoryChangeEvent(event.getPlayer(), event.getPlayer().getInventory(), event));
        }, 1);
    }
}
