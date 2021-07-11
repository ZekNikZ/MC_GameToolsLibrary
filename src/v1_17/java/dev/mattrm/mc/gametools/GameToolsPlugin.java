package dev.mattrm.mc.gametools;

import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import static dev.mattrm.mc.gametools.Constants.*;

@Plugin(name = PLUGIN_NAME, version = "1.17_" + PLUGIN_VERSION)
@Author(PLUGIN_AUTHOR)
@SoftDependency("ProtocolLib")
@SoftDependency("SmartInvs")
@ApiVersion(ApiVersion.Target.v1_17)
public final class GameToolsPlugin extends GameToolsLibrary {

}
