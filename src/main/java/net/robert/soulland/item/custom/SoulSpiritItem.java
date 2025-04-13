package net.robert.soulland.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.robert.soulland.stat.DataCache;
import org.jetbrains.annotations.NotNull;

public class SoulSpiritItem extends Item {
    String soulSpirit;

    public SoulSpiritItem(Properties pProperties, String soulSpirit) {
        super(pProperties);
        this.soulSpirit = soulSpirit;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide()) {
            System.out.printf("Consumed add soul spirit item: %s.\n", soulSpirit);
            DataCache.globalData.appendSoulSpirit((ServerPlayer) pLivingEntity, soulSpirit);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
