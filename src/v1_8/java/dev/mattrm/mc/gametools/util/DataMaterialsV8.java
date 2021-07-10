package dev.mattrm.mc.gametools.util;

import dev.mattrm.mc.gametools.VersionImplementation;
import org.bukkit.DyeColor;
import org.bukkit.Material;

@VersionImplementation
public class DataMaterialsV8 implements DataMaterials {
    @Override
    public DataMaterial limeDye() {
        return new DataMaterial(Material.INK_SACK, DyeColor.LIME.getDyeData());
    }

    @Override
    public DataMaterial grayDye() {
        return new DataMaterial(Material.INK_SACK, DyeColor.GRAY.getDyeData());
    }

    @Override
    public DataMaterial cyanDye() {
        return new DataMaterial(Material.INK_SACK, DyeColor.CYAN.getDyeData());
    }
}
