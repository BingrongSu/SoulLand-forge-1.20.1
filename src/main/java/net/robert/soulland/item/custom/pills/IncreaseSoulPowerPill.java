package net.robert.soulland.item.custom.pills;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.robert.soulland.SoulLand;
import org.jetbrains.annotations.NotNull;

public class IncreaseSoulPowerPill extends AbstractPillItem {
    public final Double amount;

    public IncreaseSoulPowerPill(Properties pProperties, double amount, int level) {
        super(pProperties, level);
        this.amount = amount;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide()) {
            System.out.printf("Consumed Increasing-Soul-Power Pill. Level %d.\n", getLevel());
            SoulLand.globalData.addSoulPower((Player) pLivingEntity, amount);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
