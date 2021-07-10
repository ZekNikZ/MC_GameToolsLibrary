package dev.mattrm.mc.gametools.settings;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IGameSetting<T> {
    String getName();

    List<String> getDescription();

    T get();

    void set(T value);

    int getPriority();

    ItemStack getDisplayItem();

    ItemStack getCurrentValueItem();

    T toggleValueForwards();

    T toggleValueBackwards();

    void setFromString(String value);

    Object getSerializedObject();
}
