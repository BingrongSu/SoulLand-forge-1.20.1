package net.robert.soulland.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.SoulLand;
import net.robert.soulland.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SoulLand.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SOUL_LAND_TAB = CREATIVE_MODE_TABS.register("soulland_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SHEN_SILVER_INGOT.get()))
                    .title(Component.translatable("creativetab.soulland_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SHEN_SILVER_INGOT.get());
                        output.accept(ModItems.SHEN_SILVER_NUGGET.get());
                        output.accept(ModItems.RAW_SHEN_SILVER_INGOT.get());
                        output.accept(ModItems.AL_FURNACE_FUEL_LV1.get());

                        output.accept(ModItems.INCREASE_SOUL_POWER_PILL_LV1.get());
                        output.accept(ModItems.INCREASE_SOUL_POWER_PILL_LV2.get());

                        output.accept(ModItems.INCREASE_SOUL_POWER_PR_LV1.get());
                        output.accept(ModItems.INCREASE_SOUL_POWER_PR_LV2.get());

                        output.accept(ModItems.PLANT_01.get());
                        output.accept(ModItems.PLANT_02.get());
                        output.accept(ModItems.PLANT_03.get());
                        output.accept(ModItems.PLANT_04.get());
                        output.accept(ModItems.PLANT_05.get());

                        output.accept(ModBlocks.SHEN_SILVER_BLOCK.get());
                        output.accept(ModBlocks.SHEN_SILVER_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_SHEN_SILVER_ORE.get());
                        output.accept(ModBlocks.NETHER_SHEN_SILVER_ORE.get());

                        output.accept(ModBlocks.ALCHEMY_FURNACE.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
