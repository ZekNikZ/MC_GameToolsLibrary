package dev.mattrm.mc.gametools.util;

import org.bukkit.Material;

public class DataMaterial {
    private Material material;
    private short damage;

    public DataMaterial(Material material) {
        this(material, (short) 0);
    }

    public DataMaterial(Material material, short damage) {
        this.material = material;
        this.damage = damage;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public short getDamage() {
        return damage;
    }

    public void setDamage(short damage) {
        this.damage = damage;
    }
}
