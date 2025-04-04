package net.robert.soulland.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.SoulLand;
import net.robert.soulland.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SoulLand.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.RAW_SHEN_SILVER_INGOT);
        simpleItem(ModItems.SHEN_SILVER_NUGGET);
        simpleItem(ModItems.SHEN_SILVER_INGOT);

        simpleItem(ModItems.INCREASE_SOUL_POWER_PILL_LV1);
        simpleItem(ModItems.INCREASE_SOUL_POWER_PILL_LV2);

        simpleItem(ModItems.INCREASE_SOUL_POWER_PR_LV1);
        simpleItem(ModItems.INCREASE_SOUL_POWER_PR_LV2);

        simpleItem(ModItems.AL_FURNACE_FUEL_LV1);

        simpleItem(ModItems.PLANT_01);
        simpleItem(ModItems.PLANT_02);
        simpleItem(ModItems.PLANT_03);
        simpleItem(ModItems.PLANT_04);
        simpleItem(ModItems.PLANT_05);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SoulLand.MOD_ID, "item/" + item.getId().getPath()));
    }
}
