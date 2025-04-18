package net.robert.soulland.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.robert.soulland.SoulLand;
import net.robert.soulland.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SoulLand.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DEEPSLATE_SHEN_SILVER_ORE.get())
                .add(ModBlocks.NETHER_SHEN_SILVER_ORE.get())
                .add(ModBlocks.SHEN_SILVER_ORE.get())
                .add(ModBlocks.AWAKEN_BASE.get())
                .add(ModBlocks.ALCHEMY_FURNACE.get());
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SHEN_SILVER_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SHEN_SILVER_BLOCK.get())
                .add(ModBlocks.DEEPSLATE_SHEN_SILVER_ORE.get())
                .add(ModBlocks.NETHER_SHEN_SILVER_ORE.get())
                .add(ModBlocks.SHEN_SILVER_ORE.get())
                .add(ModBlocks.AWAKEN_BASE.get())
                .add(ModBlocks.ALCHEMY_FURNACE.get());
    }
}
