package net.robert.soulland.item.custom;

import net.minecraft.world.item.Item;

public class AlFurnaceFuel extends Item {
    public final Integer burnAbility;
    public final int level;

    public AlFurnaceFuel(Properties pProperties, int burnAbility, int level) {
        super(pProperties);
        this.burnAbility = burnAbility;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
