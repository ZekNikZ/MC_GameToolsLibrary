package dev.mattrm.mc.gametools.util.version;

import java.util.HashMap;

public class VersionDependentClasses {
    private static final HashMap<Class<? extends IVersioned>, Object> versionedClasses = new HashMap<>();

    public static void register(Class<? extends IVersioned> intf, Object obj) {
        versionedClasses.put(intf, obj);
    }

    public static Object get(Class<? extends IVersioned> clazz) {
        return versionedClasses.get(clazz);
    }
}
