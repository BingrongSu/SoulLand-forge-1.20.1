package net.robert.soulland.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.SoulLand;
import net.robert.soulland.item.custom.IncreaseSoulPowerPill;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SoulLand.MOD_ID);

    public static final RegistryObject<Item> SHEN_SILVER_INGOT = ITEMS.register("shen_silver_ingot",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SHEN_SILVER_NUGGET = ITEMS.register("shen_silver_nugget",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> RAW_SHEN_SILVER_INGOT = ITEMS.register("raw_shen_silver_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INCREASE_SOUL_POWER_PILL_LV1 = ITEMS.register("increase_soul_power_pill_lv1",
            () -> new IncreaseSoulPowerPill(new Item.Properties().food(ModFoods.SOUL_POWER_PILL), 100));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
