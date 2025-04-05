package net.robert.soulland.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.robert.soulland.SoulLand;

public class ModTags {
    public static class Blocks {

        public static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(SoulLand.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AL_FURNACE_FUEL = tag("al_furnace_fuel");
        public static final TagKey<Item> PILL_RECIPE = tag("pill_recipe");
        public static final TagKey<Item> PLANTS = tag("plants");

        public static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(SoulLand.MOD_ID, name));
        }
    }
}
