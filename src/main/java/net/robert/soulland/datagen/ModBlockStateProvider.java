package net.robert.soulland.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.SoulLand;
import net.robert.soulland.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SoulLand.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SHEN_SILVER_BLOCK);
        blockWithItem(ModBlocks.DEEPSLATE_SHEN_SILVER_ORE);
        blockWithItem(ModBlocks.NETHER_SHEN_SILVER_ORE);
        blockWithItem(ModBlocks.SHEN_SILVER_ORE);

        simpleBlock(ModBlocks.ALCHEMY_FURNACE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/alchemy_furnace")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
