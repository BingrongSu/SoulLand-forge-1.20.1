package net.robert.soulland.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IncreaseSoulPowerPill extends Item {
    public final Double amount;

    public IncreaseSoulPowerPill(Properties pProperties, double amount) {
        super(pProperties);
        this.amount = amount;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        // append the increase soul power lines here ~~~~~~~~~~~~
        System.out.printf("~~~~~~~~~~ Soul Power Increased by %.2f.%n", this.amount);
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
