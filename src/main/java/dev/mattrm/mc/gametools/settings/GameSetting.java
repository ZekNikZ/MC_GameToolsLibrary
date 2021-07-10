package dev.mattrm.mc.gametools.settings;

import dev.mattrm.mc.gametools.util.ListUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class GameSetting<T> implements IGameSetting<T> {
    private final String name;
    private final List<String> description;
    private final ItemStack display;
    private final int priority;
    private Consumer<T> handler = null;

    public GameSetting(String name, List<String> description, ItemStack display) {
        this(name, description, display, Integer.MAX_VALUE);
    }

    public GameSetting(String name, List<String> description, ItemStack display, int priority) {
        this.name = name;
        this.description = description == null ? ListUtils.of() : description;
        this.display = display;
        this.priority = priority;

        ItemMeta meta = this.display.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + this.name);
        meta.setLore(
            this.description.stream()
                .map(line -> "" + ChatColor.RESET + ChatColor.GRAY + line)
                .collect(Collectors.toList())
        );
        this.display.setItemMeta(meta);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> getDescription() {
        return this.description;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public ItemStack getDisplayItem() {
        return this.display;
    }

    public GameSetting<T> hook(Consumer<T> handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public final void set(T value) {
        this.setValue(value);

        if (this.handler != null) {
            this.handler.accept(value);
        }
    }

    protected abstract void setValue(T value);
}
