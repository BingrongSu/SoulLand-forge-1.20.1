package net.robert.soulland.item.custom;

import net.minecraft.world.item.Item;

public class AlFurnaceFuel extends Item {
    public final Integer burnAbility;

    public AlFurnaceFuel(Properties pProperties, int burnAbility) {
        super(pProperties);
        this.burnAbility = burnAbility;
    }
}
