package net.robert.soulland.item.custom.pills;

import net.minecraft.world.item.Item;

public abstract class AbstractPillItem extends Item {
    private final int level;

    public AbstractPillItem(Properties pProperties, int level) {
        super(pProperties);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
