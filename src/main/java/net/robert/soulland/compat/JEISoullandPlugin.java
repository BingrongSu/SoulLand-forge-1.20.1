package net.robert.soulland.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.robert.soulland.SoulLand;
import net.robert.soulland.recipe.AlFurnaceRecipe;
import net.robert.soulland.screen.AlchemyFurnaceScreen;

import java.util.List;

@JeiPlugin
public class JEISoullandPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SoulLand.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlchemyFurnaceCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<AlFurnaceRecipe> furnaceRecipes = recipeManager.getAllRecipesFor(AlFurnaceRecipe.Type.INSTANCE);
        registration.addRecipes(AlchemyFurnaceCategory.AL_FURNACE_RECIPE_TYPE, furnaceRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlchemyFurnaceScreen.class, 84, 49, 22, 15,
                AlchemyFurnaceCategory.AL_FURNACE_RECIPE_TYPE);
    }
}
