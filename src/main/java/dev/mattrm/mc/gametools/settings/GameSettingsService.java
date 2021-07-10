package dev.mattrm.mc.gametools.settings;

import dev.mattrm.mc.gametools.Service;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.stream.Collectors;

public class GameSettingsService extends Service {
    private static final GameSettingsService INSTANCE = new GameSettingsService();

    public static GameSettingsService getInstance() {
        return INSTANCE;
    }

    private final Map<String, IGameSetting<?>> settings = new HashMap<>();

    public void addGameSetting(String id, IGameSetting<?> setting) {
        this.settings.put(id, setting);
    }

    public IGameSetting<?> getSetting(String name) {
        return this.settings.get(name);
    }

    public List<IGameSetting<?>> getAllSettings() {
        return this.settings.entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<String, IGameSetting<?>> entry) -> entry.getValue().getPriority()).thenComparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public void saveSettingsTo(ConfigurationSection section) {
        this.settings.forEach((key, value) -> section.set(key, value.getSerializedObject()));
    }

    public void loadSettingsFrom(ConfigurationSection section) {
        Set<String> keys = section.getKeys(false);
        keys.forEach(key -> {
            IGameSetting<?> setting = this.getSetting(key);
            if (setting != null) {
                setting.setFromString(section.get(key).toString());
            }
        });
    }
}
