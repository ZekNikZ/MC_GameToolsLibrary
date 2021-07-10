package dev.mattrm.mc.gametools;

import dev.mattrm.mc.gametools.commands.CommandOverrides;
import dev.mattrm.mc.gametools.data.DataService;
import dev.mattrm.mc.gametools.event.CustomEventHandler;
import dev.mattrm.mc.gametools.readyup.ReadyUpCommands;
import dev.mattrm.mc.gametools.readyup.ReadyUpService;
import dev.mattrm.mc.gametools.scoreboards.ScoreboardService;
import dev.mattrm.mc.gametools.settings.GameSettingsService;
import dev.mattrm.mc.gametools.settings.commands.SettingsCommands;
import dev.mattrm.mc.gametools.teams.TeamCommands;
import dev.mattrm.mc.gametools.teams.TeamService;
import org.atteo.classindex.ClassFilter;
import org.atteo.classindex.ClassIndex;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GameToolsLibrary extends JavaPlugin {
    Logger LOGGER = this.getLogger();

    @Override
    public void onEnable() {
        LOGGER.info("Loading Game Tools Library");
        loadVersionDependents();
        registerServices();
        registerCommands();
        LOGGER.info("Done loading");
        this.getServer().getPluginManager().registerEvents(new CustomEventHandler(this), this);
    }

    private void loadVersionDependents() {
        LOGGER.info("Loading version-dependent classes...");
//        Iterable<Class<?>> versionDependents = ClassFilter.only().interfaces().from(ClassIndex.getAnnotated(VersionDependent.class));
        Iterable<Class<?>> versionDependents = ClassIndex.getAnnotated(VersionDependent.class, this.getClassLoader());
        versionDependents.forEach(clazz -> {
            LOGGER.info("Loading version-dependent class " + clazz.getCanonicalName());

            if (!clazz.isInterface()) {
                LOGGER.severe("The @VersionDependent annotation can only be applied to interfaces");
                return;
            }

            if (Arrays.stream(clazz.getFields()).noneMatch(field -> Modifier.isStatic(field.getModifiers()) && Arrays.stream(field.getAnnotations()).anyMatch(annotation -> annotation instanceof VersionedInstance))) {
                LOGGER.severe("The @VersionedInstance annotation must be applied to a static field in a @VersionDependent interface");
                return;
            }

            Iterable<Class<?>> versionImplementations = ClassFilter.only().classes().withPublicDefaultConstructor().from(ClassIndex.getAnnotated(VersionImplementation.class, this.getClassLoader()));
            Class<?> implementation = null;
            for (Class<?> impl : versionImplementations) {
                if (clazz.isAssignableFrom(impl)) {
                    implementation = impl;
                    break;
                }
            }
            if (implementation == null) {
                LOGGER.severe("No version implementation found for class " + clazz.getCanonicalName());
                return;
            }

            AtomicBoolean success = new AtomicBoolean(true);
            try {
                Constructor<?> ctor = implementation.getConstructor();
                Object o = ctor.newInstance();
                Arrays.stream(clazz.getFields()).filter(field -> Modifier.isStatic(field.getModifiers()) && Arrays.stream(field.getAnnotations()).anyMatch(annotation -> annotation instanceof VersionedInstance)).forEach(field -> {
                    try {
                        Field modifiers = field.getClass().getDeclaredField("modifiers");
                        modifiers.setAccessible(true);
                        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                        field.set(null, o);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        LOGGER.severe("Could not save instance in " + clazz.getCanonicalName() + "." + field.getName());
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                        success.set(false);
                    }
                });
            } catch (NoSuchMethodException e) {
                LOGGER.severe("No default constructor found for class " + implementation.getCanonicalName());
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                return;
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                LOGGER.severe("Could not construct class " + implementation.getCanonicalName());
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                return;
            }

            if (success.get()) {
                LOGGER.info("Successfully loaded version-dependent class " + clazz.getCanonicalName());
            }
        });

        LOGGER.info("Done loading version-dependent classes.");
    }

    @Override
    public void onDisable() {
        DataService.getInstance().saveAll();
    }

    private void registerServices() {
        DataService.getInstance().setup(this);

        Service[] services = new Service[]{
                TeamService.getInstance(),
                ScoreboardService.getInstance(),
                ReadyUpService.getInstance(),
                GameSettingsService.getInstance()
        };

        PluginManager pluginManager = this.getServer().getPluginManager();
        for (Service service : services) {
            service.setup(this);
            pluginManager.registerEvents(service, this);
        }
    }

    private void registerCommands() {
        new TeamCommands().registerCommands(this);
        new ReadyUpCommands().registerCommands(this);
        new CommandOverrides().registerCommands(this);
        new SettingsCommands().registerCommands(this);
    }
}
