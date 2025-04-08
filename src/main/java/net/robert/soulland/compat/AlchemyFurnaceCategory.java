package net.robert.soulland.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.robert.soulland.SoulLand;
import net.robert.soulland.block.ModBlocks;
import net.robert.soulland.recipe.AlFurnaceRecipe;
import net.robert.soulland.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class AlchemyFurnaceCategory implements IRecipeCategory<AlFurnaceRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(SoulLand.MOD_ID, "alchemy_furnace");
    public static final ResourceLocation TEXTURE = new ResourceLocation(SoulLand.MOD_ID,
            "textures/gui/alchemy_furnace_jei_gui.png");

    public static final RecipeType<AlFurnaceRecipe> AL_FURNACE_RECIPE_TYPE =
            new RecipeType<>(UID, AlFurnaceRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public AlchemyFurnaceCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ALCHEMY_FURNACE.get()));
    }


    @Override
    public RecipeType<AlFurnaceRecipe> getRecipeType() {
        return AL_FURNACE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.soulland.alchemy_furnace");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlFurnaceRecipe recipe, IFocusGroup focuses) {
        Collection<Item> fuelItems = BuiltInRegistries.ITEM
                .stream()
                .filter(item -> item.builtInRegistryHolder().is(ModTags.Items.AL_FURNACE_FUEL))
                .toList();
        List<ItemStack> fuelStack = fuelItems.stream()
                .map(ItemStack::new)
                .toList();

        builder.addSlot(RecipeIngredientRole.INPUT, 34, 6).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 21, 26).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 46, 26).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.CATALYST, 86, 22).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 34, 62).addItemStacks(fuelStack);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 133, 44).addItemStack(recipe.getResultItem(null));
    }
}
