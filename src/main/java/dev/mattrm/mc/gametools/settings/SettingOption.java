package dev.mattrm.mc.gametools.settings;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface SettingOption {
    String name();
    Material item();
    short damage() default 0;
    String[] description() default {};
}
