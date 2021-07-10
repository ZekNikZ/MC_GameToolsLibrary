package dev.mattrm.mc.gametools;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotating an interface with this annotation will mark it as version dependent and will cause the library to search for an implementing class to populate its corresponding static {@link VersionedInstance} field.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface VersionDependent {
}
