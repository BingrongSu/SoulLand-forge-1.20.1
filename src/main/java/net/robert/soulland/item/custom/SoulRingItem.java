package net.robert.soulland.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.robert.soulland.stat.DataCache;

public class SoulRingItem extends Item {
    private final double year;

    public SoulRingItem(Properties pProperties, double year) {
        super(pProperties);
        this.year = year;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide() && pLivingEntity instanceof ServerPlayer serverPlayer) {
            DataCache.globalData.addSoulRing(serverPlayer, year);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public double getYear() {
        return year;
    }
}
