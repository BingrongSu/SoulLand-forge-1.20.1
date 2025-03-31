package net.robert.soulland.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.robert.soulland.SoulLand;
import net.robert.soulland.block.ModBlocks;
import net.robert.soulland.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        oreSmeltingBlasting(consumer, List.of(
                    ModBlocks.SHEN_SILVER_ORE.get(),
                    ModBlocks.DEEPSLATE_SHEN_SILVER_ORE.get(),
                    ModBlocks.NETHER_SHEN_SILVER_ORE.get(),
                    ModItems.RAW_SHEN_SILVER_INGOT.get()
                ), ModItems.SHEN_SILVER_INGOT.get(), 0.2f, 300, "shen_silver"
        );

        reversePackingRecipe9(consumer, RecipeCategory.MISC, ModItems.SHEN_SILVER_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SHEN_SILVER_BLOCK.get());
        reversePackingRecipe9(consumer, RecipeCategory.MISC, ModItems.SHEN_SILVER_NUGGET.get(), RecipeCategory.MISC, ModItems.SHEN_SILVER_INGOT.get());
    }


    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  SoulLand.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    private static void reversePackingRecipe9(Consumer<FinishedRecipe> consumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked) {
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked)
                .pattern("...")
                .pattern("...")
                .pattern("...")
                .define('.', pUnpacked)
                .unlockedBy(getHasName(pUnpacked), has(pUnpacked))
                .save(consumer, new ResourceLocation(SoulLand.MOD_ID, "%s_from_%s".formatted(getSimpleRecipeName(pPacked), getSimpleRecipeName(pUnpacked))));
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9)
                .requires(pPacked)
                .unlockedBy(getHasName(pPacked), has(pPacked))
                .save(consumer, new ResourceLocation(SoulLand.MOD_ID, "%s_from_%s".formatted(getSimpleRecipeName(pUnpacked), getSimpleRecipeName(pPacked))));
    }

    private static void oreSmeltingBlasting(Consumer<FinishedRecipe> consumer, List<ItemLike> ores, ItemLike result, float pExperience, int basicCookingTime, String pGroup) {
        oreSmelting(consumer, ores,
                RecipeCategory.MISC, result, pExperience, basicCookingTime, pGroup);
        oreBlasting(consumer, ores,
                RecipeCategory.MISC, result, pExperience, basicCookingTime/2, pGroup);
    }
}
