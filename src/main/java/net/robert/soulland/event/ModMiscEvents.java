package net.robert.soulland.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.robert.soulland.SoulLand;
import net.robert.soulland.item.ModItems;

import java.util.List;

public class ModMiscEvents {

    public static void register() {
        SoulLand.LOGGER.info("Registering Mod Misc Events.");
    }

    @Mod.EventBusSubscriber(modid = "soulland", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class PlayerUseItemHandler {

        @SubscribeEvent
        public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
            Player player = event.getEntity();
            ItemStack itemStack = event.getItemStack();
            Level world = player.level();

            List<Item> rawFuelLv1 = List.of(Items.COAL, Items.CHARCOAL);
            List<Item> rawFuelLv2 = List.of(Items.BLAZE_ROD);

            double powerConsumption = 0d;

            if (player.isCrouching()) {
                Item usingItem = itemStack.getItem();
                if (rawFuelLv1.contains(usingItem)) {
                    giveItem(player, ModItems.AL_FURNACE_FUEL_LV1.get(), itemStack);
                    powerConsumption = 10;
                } else if (rawFuelLv2.contains(usingItem)) {
                    giveItem(player, ModItems.AL_FURNACE_FUEL_LV2.get(), itemStack);
                    powerConsumption = 20;
                }
            }
            // TODO: Modify the power consumption of this usage.
            if (!player.level().isClientSide())
                SoulLand.globalData.addSoulPower(player, -powerConsumption);

        }

        private static void giveItem(Player player, Item item, ItemStack itemStack) {
            ItemStack newItem = new ItemStack(item, 1);
            boolean added = player.getInventory().add(newItem);
            if (!added) {
                player.drop(newItem, false);
            }
            itemStack.shrink(1);
        }
    }
}
