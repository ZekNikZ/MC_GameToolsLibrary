package dev.mattrm.mc.gametools.settings.impl;

import dev.mattrm.mc.gametools.settings.GameSetting;
import dev.mattrm.mc.gametools.util.DataMaterial;
import dev.mattrm.mc.gametools.util.DataMaterials;
import dev.mattrm.mc.gametools.util.ISB;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class BooleanSetting extends GameSetting<Boolean> {
    private static final ItemStack DISABLED = ISB.material(DataMaterials.getInstance().grayDye()).name("Off").build();
    private static final ItemStack ENABLED = ISB.material(DataMaterials.getInstance().limeDye()).name("On").build();

    private boolean value;

    public BooleanSetting(String name, List<String> description, ItemStack display) {
        this(name, description, display, true);
    }

    public BooleanSetting(String name, List<String> description, ItemStack display, boolean defaultValue) {
        this(name, description, display, defaultValue, Integer.MAX_VALUE);
    }

    public BooleanSetting(String name, List<String> description, ItemStack display, boolean defaultValue, int priority) {
        super(name, description, display, priority);
        this.value = defaultValue;
    }

    @Override
    public Boolean get() {
        return this.value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public ItemStack getCurrentValueItem() {
        return this.value ? ENABLED : DISABLED;
    }

    @Override
    public Boolean toggleValueForwards() {
        this.set(!this.value);
        return this.value;
    }

    @Override
    public Boolean toggleValueBackwards() {
        return this.toggleValueForwards();
    }

    @Override
    public void setFromString(String value) {
        this.set(Boolean.valueOf(value));
    }

    @Override
    public Boolean getSerializedObject() {
        return this.get();
    }
}
