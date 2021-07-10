package dev.mattrm.mc.gametools.settings.impl;

import dev.mattrm.mc.gametools.settings.GameSetting;
import dev.mattrm.mc.gametools.util.DataMaterial;
import dev.mattrm.mc.gametools.util.DataMaterials;
import dev.mattrm.mc.gametools.util.ISB;
import dev.mattrm.mc.gametools.util.ItemStackBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class IntRangeSetting extends GameSetting<Integer> {
    private static final DataMaterial SELECTOR_ITEM = DataMaterials.getInstance().cyanDye();

    private final int min, max, step;
    private int value;

    public IntRangeSetting(String name, List<String> description, ItemStack display, int min, int max) {
        this(name, description, display, min, max, 1);
    }

    public IntRangeSetting(String name, List<String> description, ItemStack display, int min, int max, int step) {
        super(name, description, display);
        this.min = min;
        this.max = max;
        this.step = step;
        this.value = min;
    }

    public IntRangeSetting(String name, List<String> description, ItemStack display, int priority, int min, int max, int step) {
        this(name, description, display, priority, min, max, step, min);
    }

    public IntRangeSetting(String name, List<String> description, ItemStack display, int priority, int min, int max, int step, int defaultValue) {
        super(name, description, display, priority);
        this.min = min;
        this.max = max;
        this.step = step;
        this.set(defaultValue);
    }

    @Override
    public Integer get() {
        return this.value;
    }

    @Override
    public void setValue(Integer value) {
        Objects.requireNonNull(value);

        if (value < this.min || value > this.max) {
            throw new IllegalArgumentException("Value out of range");
        }

        if ((value - this.min) % this.step != 0) {
            throw new IllegalArgumentException("Value is unattainable");
        }

        this.value = value;
    }

    @Override
    public ItemStack getCurrentValueItem() {
        ItemStackBuilder builder = ISB.material(SELECTOR_ITEM)
            .name("" + this.value);

        if (this.min >= 0 && this.max <= 64) {
            builder.amount(this.value);
        } else {
            builder.amount((this.value - this.min) / this.step + 1);
        }

        return builder.build();
    }

    @Override
    public Integer toggleValueForwards() {
        if (this.value >= this.max) {
            this.set(this.min);
        } else {
            this.set(this.value + this.step);
        }

        return this.get();
    }

    @Override
    public Integer toggleValueBackwards() {
        if (this.value <= this.min) {
            this.set(this.max);
        } else {
            this.set(this.value - this.step);
        }

        return this.get();
    }

    @Override
    public void setFromString(String value) {
        this.set(Integer.parseInt(value));
    }

    @Override
    public Integer getSerializedObject() {
        return this.get();
    }
}
