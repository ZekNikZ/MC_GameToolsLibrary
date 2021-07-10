package dev.mattrm.mc.gametools.teams;

import dev.mattrm.mc.gametools.data.IYamlSectionSerializable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scoreboard.Team;

import java.awt.*;

public class GameTeam implements IYamlSectionSerializable<GameTeam> {
    public static final GameTeam SPECTATOR = new GameTeam("spectators", "Spectators", "SPEC");

    private String id;
    private String name;

    private String prefix;
    private ChatColor formatCode;
    private Color color;

    public GameTeam(String id, String name, String prefix) {
        this();
        this.prefix = prefix;
        this.setId(id);
        this.setName(name);
    }

    public GameTeam() {
        this.formatCode = ChatColor.RESET;
    }

    public ChatColor getFormatCode() {
        return this.formatCode;
    }

    public void setFormatCode(ChatColor formatCode) {
        this.formatCode = formatCode;
        this.updateMinecraftTeam();
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.updateMinecraftTeam();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        this.updateMinecraftTeam();
    }

    private void updateMinecraftTeam() {
        Team scoreboardTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(this.getId());
        if (scoreboardTeam == null) {
            return;
        }
        scoreboardTeam.setPrefix("" + this.getFormatCode() + ChatColor.BOLD + this.getPrefix() + ChatColor.RESET + this.getFormatCode() + " ");
        scoreboardTeam.setSuffix("" + ChatColor.RESET);
        scoreboardTeam.setDisplayName(this.getName());
    }

    @Override
    public void serialize(ConfigurationSection section) {
        section.set("name", this.getName());
        section.set("prefix", this.getPrefix());
        section.set("format", this.getFormatCode().name());
    }

    @Override
    public String getSectionKey() {
        return getId();
    }

    @Override
    public GameTeam deserialize(ConfigurationSection section) {
        this.setId(section.getName());
        this.setName(section.getString("name"));
        this.setPrefix(section.getString("prefix"));
        this.setFormatCode(ChatColor.valueOf(section.getString("format")));
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
