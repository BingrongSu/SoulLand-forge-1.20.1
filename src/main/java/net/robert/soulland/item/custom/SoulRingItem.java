package net.robert.soulland.item.custom;

import net.minecraft.world.item.Item;

public class SoulRingItem extends Item {
    private final double year;

    public SoulRingItem(Properties pProperties, double year) {
        super(pProperties);
        this.year = year;
    }

    public double getYear() {
        return year;
    }
}
