package net.robert.soulland.item.custom.pill_recipes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IncreaseSoulPowerPR extends Item {
    private final int level;

    public IncreaseSoulPowerPR(Properties pProperties, int level) {
        super(pProperties);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        // TODO Create Screen
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
