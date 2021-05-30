package dev.mattrm.mc.gametools.data;

import org.bukkit.configuration.ConfigurationSection;

public interface IYamlSectionSerializable<T> {
    void serialize(ConfigurationSection section);

    String getSectionKey();

    T deserialize(ConfigurationSection section);
}
