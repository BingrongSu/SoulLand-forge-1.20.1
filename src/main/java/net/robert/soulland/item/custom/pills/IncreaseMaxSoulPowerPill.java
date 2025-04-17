package net.robert.soulland.item.custom.pills;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.robert.soulland.stat.DataCache;
import org.jetbrains.annotations.NotNull;

public class IncreaseMaxSoulPowerPill extends AbstractPillItem {
    public final Double amount;

    public IncreaseMaxSoulPowerPill(Properties pProperties, double amount, int level) {
        super(pProperties, level);
        this.amount = amount;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide()) {
            System.out.printf("Consumed Increase-Max-Soul-Power Pill. Level %d.\n", getLevel());
            DataCache.globalData.addMaxSoulPower((Player) pLivingEntity, amount, false);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
