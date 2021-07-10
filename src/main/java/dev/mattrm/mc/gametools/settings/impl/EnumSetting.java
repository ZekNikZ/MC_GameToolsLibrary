package dev.mattrm.mc.gametools.settings.impl;

import dev.mattrm.mc.gametools.settings.GameSetting;
import dev.mattrm.mc.gametools.settings.IGameSettingEnum;
import dev.mattrm.mc.gametools.settings.SettingOption;
import dev.mattrm.mc.gametools.util.ISB;
import dev.mattrm.mc.gametools.util.ListUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EnumSetting<T extends Enum<T> & IGameSettingEnum> extends GameSetting<T> {
    private final Class<T> enumType;
    private final List<T> values;
    private int value;

    public EnumSetting(String name, List<String> description, ItemStack display, Class<T> enumType, T defaultValue) {
        super(name, description, display);
        this.enumType = enumType;
        this.values = ListUtils.of(this.enumType.getEnumConstants());
        this.set(defaultValue);
    }

    public EnumSetting(String name, List<String> description, ItemStack display, int priority, Class<T> enumType, T defaultValue) {
        super(name, description, display, priority);
        this.enumType = enumType;
        this.values = ListUtils.of(this.enumType.getEnumConstants());
        this.set(defaultValue);
    }

    @Override
    public T get() {
        return this.values.get(this.value);
    }

    @Override
    public void setValue(T value) {
        this.value = this.values.indexOf(Objects.requireNonNull(value));
    }

    @Override
    public ItemStack getCurrentValueItem() {
        try {
            SettingOption option = enumType
                .getField(this.get().name())
                .getAnnotation(SettingOption.class);
            return ISB.material(option.item(), option.damage())
                .name(option.name())
                .lore(option.description())
                .build();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not find enum constant");
        }
    }

    @Override
    public T toggleValueForwards() {
        this.set(this.values.get((this.value + 1) % this.values.size()));

        return this.get();
    }

    @Override
    public T toggleValueBackwards() {
        this.set(this.values.get((this.value - 1 + this.values.size()) % this.values.size()));

        return this.get();
    }

    @Override
    public void setFromString(String value) {
        this.set(Arrays.stream(enumType.getEnumConstants()).filter(v -> v.toString().equals(value)).findFirst().orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public String getSerializedObject() {
        return this.get().toString();
    }
}
