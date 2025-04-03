package net.robert.soulland.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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

            List<Item> rawFuel = List.of(Items.COAL, Items.CHARCOAL);

            if (!world.isClientSide && player.isCrouching()) {
                Item usingItem = itemStack.getItem();

                if (rawFuel.contains(usingItem)) {
                    ItemStack newItem = new ItemStack(ModItems.AL_FURNACE_FUEL_LV1.get(), 1);

                    boolean added = player.getInventory().add(newItem);

                    if (!added) {
                        player.drop(newItem, false);
                    }

                    itemStack.shrink(1);
                }
            }
            // TODO: Reduce player's soul power.
        }
    }
}
