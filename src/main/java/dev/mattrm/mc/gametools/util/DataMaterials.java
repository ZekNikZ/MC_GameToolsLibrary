package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionDependent;
import dev.mattrm.mc.gametools.VersionedInstance;

@VersionDependent
public interface DataMaterials {
    @VersionedInstance
    DataMaterials INSTANCE = null;

    static DataMaterials getInstance() {
        return INSTANCE;
    }

    DataMaterial limeDye();

    DataMaterial grayDye();

    DataMaterial cyanDye();
}
