package net.robert.soulland.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.SoulLand;
import net.robert.soulland.item.custom.AlFurnaceFuel;
import net.robert.soulland.item.custom.SoulRingItem;
import net.robert.soulland.item.custom.SoulSpiritItem;
import net.robert.soulland.item.custom.pill_recipes.IncreaseMaxSoulPowerPR;
import net.robert.soulland.item.custom.pill_recipes.IncreaseSoulPowerPR;
import net.robert.soulland.item.custom.pills.IncreaseMaxSoulPowerPill;
import net.robert.soulland.item.custom.pills.IncreaseSoulPowerPill;

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
            () -> new IncreaseSoulPowerPill(new Item.Properties().food(ModFoods.SOUL_POWER_PILL), 10, 1));
    public static final RegistryObject<Item> INCREASE_SOUL_POWER_PILL_LV2 = ITEMS.register("increase_soul_power_pill_lv2",
            () -> new IncreaseSoulPowerPill(new Item.Properties().food(ModFoods.SOUL_POWER_PILL), 50, 2));

    public static final RegistryObject<Item> INCREASE_MAX_SOUL_POWER_PILL_LV1 = ITEMS.register("increase_max_soul_power_pill_lv1",
            () -> new IncreaseMaxSoulPowerPill(new Item.Properties().food(ModFoods.SOUL_POWER_PILL), 10, 1));
    public static final RegistryObject<Item> INCREASE_MAX_SOUL_POWER_PILL_LV2 = ITEMS.register("increase_max_soul_power_pill_lv2",
            () -> new IncreaseMaxSoulPowerPill(new Item.Properties().food(ModFoods.SOUL_POWER_PILL), 50, 2));

    public static final RegistryObject<Item> PLANT_01 = ITEMS.register("plant_01",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANT_02 = ITEMS.register("plant_02",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANT_03 = ITEMS.register("plant_03",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANT_04 = ITEMS.register("plant_04",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANT_05 = ITEMS.register("plant_05",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INCREASE_SOUL_POWER_PR_LV1 = ITEMS.register("increase_soul_power_pill_recipe_lv1",
            () -> new IncreaseSoulPowerPR(new Item.Properties(), 1));
    public static final RegistryObject<Item> INCREASE_SOUL_POWER_PR_LV2 = ITEMS.register("increase_soul_power_pill_recipe_lv2",
            () -> new IncreaseSoulPowerPR(new Item.Properties(), 2));

    public static final RegistryObject<Item> INCREASE_MAX_SOUL_POWER_PR_LV1 = ITEMS.register("increase_max_soul_power_pill_recipe_lv1",
            () -> new IncreaseMaxSoulPowerPR(new Item.Properties(), 1));
    public static final RegistryObject<Item> INCREASE_MAX_SOUL_POWER_PR_LV2 = ITEMS.register("increase_max_soul_power_pill_recipe_lv2",
            () -> new IncreaseMaxSoulPowerPR(new Item.Properties(), 2));

    public static final RegistryObject<Item> AL_FURNACE_FUEL_LV1 = ITEMS.register("al_furnace_fuel_lv1",
            () -> new AlFurnaceFuel(new Item.Properties(), 300, 1));
    public static final RegistryObject<Item> AL_FURNACE_FUEL_LV2 = ITEMS.register("al_furnace_fuel_lv2",
            () -> new AlFurnaceFuel(new Item.Properties(), 3000, 2));

    public static final RegistryObject<Item> FENG_HUANG_SS_ITEM = ITEMS.register("feng_huang_ss_item",
            () -> new SoulSpiritItem(new Item.Properties().food(ModFoods.SOUL_SPIRIT_ITEM), "fengHuang"));
    public static final RegistryObject<Item> LIU_LI_SS_ITEM = ITEMS.register("liu_li_ss_item",
            () -> new SoulSpiritItem(new Item.Properties().food(ModFoods.SOUL_SPIRIT_ITEM), "liuLi"));
    public static final RegistryObject<Item> SAUSAGE_SS_ITEM = ITEMS.register("sausage_ss_item",
            () -> new SoulSpiritItem(new Item.Properties().food(ModFoods.SOUL_SPIRIT_ITEM), "sausage"));

    public static final RegistryObject<Item> SOUL_RING_TEN = ITEMS.register("soul_ring_ten",
            () -> new SoulRingItem(new Item.Properties().food(ModFoods.SOUL_RING_ITEM), 30));
    public static final RegistryObject<Item> SOUL_RING_HUNDRED = ITEMS.register("soul_ring_hud",
            () -> new SoulRingItem(new Item.Properties().food(ModFoods.SOUL_RING_ITEM), 500));
    public static final RegistryObject<Item> SOUL_RING_THOUSAND = ITEMS.register("soul_ring_thd",
            () -> new SoulRingItem(new Item.Properties().food(ModFoods.SOUL_RING_ITEM), 4000));
    public static final RegistryObject<Item> SOUL_RING_TEN_THOUSAND = ITEMS.register("soul_ring_ttd",
            () -> new SoulRingItem(new Item.Properties().food(ModFoods.SOUL_RING_ITEM), 60000));
    public static final RegistryObject<Item> SOUL_RING_HUNDRED_THOUSAND = ITEMS.register("soul_ring_htd",
            () -> new SoulRingItem(new Item.Properties().food(ModFoods.SOUL_RING_ITEM), 550000));
//    public static final RegistryObject<Item> SOUL_RING_MILLION = ITEMS.register("soul_ring_mil",
//            () -> new SoulRingItem(new Item.Properties(), 9999999));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
