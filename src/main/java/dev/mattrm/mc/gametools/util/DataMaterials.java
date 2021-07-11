package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionDependent;
import dev.mattrm.mc.gametools.util.version.IVersioned;
import dev.mattrm.mc.gametools.util.version.VersionDependentClasses;

@VersionDependent
public interface DataMaterials extends IVersioned {
    static DataMaterials get() {
        return (DataMaterials) VersionDependentClasses.get(DataMaterials.class);
    }

    DataMaterial limeDye();

    DataMaterial grayDye();

    DataMaterial cyanDye();
}
