package net.robert.soulland.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.robert.soulland.SoulLand;
import net.robert.soulland.item.custom.AlFurnaceFuel;
import net.robert.soulland.item.custom.pill_recipes.IncreaseMaxSoulPowerPR;
import net.robert.soulland.item.custom.pill_recipes.IncreaseSoulPowerPR;
import net.robert.soulland.item.custom.pills.IncreaseMaxSoulPowerPill;
import net.robert.soulland.item.custom.pills.IncreaseSoulPowerPill;

import java.util.List;

@Mod.EventBusSubscriber(modid = SoulLand.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TooltipHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        int level = 0;
        if (stack.getItem() instanceof IncreaseSoulPowerPill) {
            level = ((IncreaseSoulPowerPill) stack.getItem()).getLevel();
        } else if (stack.getItem() instanceof IncreaseMaxSoulPowerPill) {
            level = ((IncreaseMaxSoulPowerPill) stack.getItem()).getLevel();
        } else if (stack.getItem() instanceof AlFurnaceFuel) {
            level = ((AlFurnaceFuel) stack.getItem()).getLevel();
        } else if (stack.getItem() instanceof IncreaseSoulPowerPR) {
            level = ((IncreaseSoulPowerPR) stack.getItem()).getLevel();
        } else if (stack.getItem() instanceof IncreaseMaxSoulPowerPR) {
            level = ((IncreaseMaxSoulPowerPR) stack.getItem()).getLevel();
        }
        if (level > 0)
            tooltip.add(Component.translatable("tooltip.lv"+level).withStyle(ChatFormatting.ITALIC));
    }
}
