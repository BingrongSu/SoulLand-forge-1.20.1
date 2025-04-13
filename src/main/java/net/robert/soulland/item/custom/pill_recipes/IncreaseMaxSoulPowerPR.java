package net.robert.soulland.item.custom.pill_recipes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.robert.soulland.SoulLand;

public class IncreaseMaxSoulPowerPR extends Item {
    private final int level;

    public IncreaseMaxSoulPowerPR(Properties pProperties, int level) {
        super(pProperties);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
            CompoundTag tag = book.getOrCreateTag();

            tag.putString("title", "Recipe");
            tag.putString("author", SoulLand.MOD_ID);

            ListTag pages = new ListTag();
            pages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("increase_max_soul_power_pill_recipe.content.p1"))));
            pages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("increase_max_soul_power_pill_recipe.content.p2"))));
            pages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("increase_max_soul_power_pill_recipe.content.p3"))));
            pages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("increase_max_soul_power_pill_recipe.content.p4.lv%d".formatted(level)))));

            tag.put("pages", pages);
            Minecraft.getInstance().setScreen(new BookViewScreen(BookViewScreen.BookAccess.fromItem(book)));

        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
    }
}
