package dev.mattrm.mc.gametools.world;

import dev.mattrm.mc.gametools.Service;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;

public class WorldSyncService extends Service {
    private static final WorldSyncService INSTANCE = new WorldSyncService();

    public static WorldSyncService getInstance() {
        return INSTANCE;
    }

    public WorldSyncService setGameRuleValue(String name, String value) {
        Bukkit.getWorlds().forEach(world -> world.setGameRuleValue(name, value));
        return this;
    }

    public WorldSyncService setWorldBorderCenter(double x, double y) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setCenter(world.getEnvironment() == World.Environment.NETHER ? x / 8 : x, world.getEnvironment() == World.Environment.NETHER ? y / 8 : y));
        return this;
    }

    public WorldSyncService setWorldBorderSize(double newSize) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setSize(world.getEnvironment() == World.Environment.NETHER ? newSize / 8 : newSize));
        return this;
    }

    public WorldSyncService setWorldBorderSize(double newSize, long seconds) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setSize(world.getEnvironment() == World.Environment.NETHER ? newSize / 8 : newSize, seconds));
        return this;
    }

    public WorldSyncService setWorldBorderWarningTime(int seconds) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setWarningTime(seconds));
        return this;
    }

    public WorldSyncService setWorldBorderWarningDistance(int distance) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setWarningDistance(distance));
        return this;
    }

    public WorldSyncService setWorldBorderDamageAmount(double damage) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setDamageAmount(damage));
        return this;
    }

    public WorldSyncService setWorldBorderDamageBuffer(double blocks) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setDamageBuffer(blocks));
        return this;
    }

    public WorldSyncService resetWorldBorder() {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().reset());
        return this;
    }

    public double getWorldBorderSize() {
        return Bukkit.getWorlds().get(0).getWorldBorder().getSize();
    }

    public WorldSyncService stopWorldBorder() {
        this.setWorldBorderSize(this.getWorldBorderSize());
        return this;
    }

    public WorldSyncService setDifficulty(Difficulty difficulty) {
        Bukkit.getWorlds().forEach(world -> world.setDifficulty(difficulty));
        return this;
    }

    public WorldSyncService setTime(int time) {
        Bukkit.getWorlds().forEach(world -> world.setTime(time));
        return this;
    }

    public WorldSyncService setWeatherClear() {
        Bukkit.getWorlds().forEach(world -> {
            world.setStorm(false);
            world.setThundering(false);
            world.setWeatherDuration(0);
        });
        return this;
    }

    public WorldSyncService setWeatherRain() {
        Bukkit.getWorlds().forEach(world -> {
            world.setStorm(true);
            world.setThundering(false);
        });
        return this;
    }

    public WorldSyncService setWeatherStorm() {
        Bukkit.getWorlds().forEach(world -> {
            world.setStorm(true);
            world.setThundering(true);
        });
        return this;
    }
}
