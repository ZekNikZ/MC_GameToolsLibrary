package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionImplementation;
import org.bukkit.Material;

@VersionImplementation
public class DataMaterialsV16 implements DataMaterials {
    @Override
    public DataMaterial limeDye() {
        return new DataMaterial(Material.LIME_DYE);
    }

    @Override
    public DataMaterial grayDye() {
        return new DataMaterial(Material.GRAY_DYE);
    }

    @Override
    public DataMaterial cyanDye() {
        return new DataMaterial(Material.CYAN_DYE);
    }
}
