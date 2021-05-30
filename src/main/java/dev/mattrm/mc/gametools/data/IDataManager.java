package dev.mattrm.mc.gametools.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public interface IDataManager {
    String getDataFileName();

    JavaPlugin getPlugin();

    void onLoad(ConfigurationSection config);

    void onSave(ConfigurationSection config);

    default void loadData() {
        String name = this.getDataFileName();

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getPlugin().getDataFolder(), name + ".yml"));

        this.onLoad(config);
    }

    default void saveData() {
        YamlConfiguration config = new YamlConfiguration();

        this.onSave(config);

        try {
            String name = this.getDataFileName();
            config.save(new File(getPlugin().getDataFolder(), name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
