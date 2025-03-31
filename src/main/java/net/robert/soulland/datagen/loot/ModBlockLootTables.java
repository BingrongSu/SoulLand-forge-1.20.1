package net.robert.soulland.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.block.ModBlocks;
import net.robert.soulland.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.SHEN_SILVER_BLOCK.get());

        createUniformOreDrops(ModBlocks.SHEN_SILVER_ORE.get(), ModItems.RAW_SHEN_SILVER_INGOT.get(), 1, 1);
        createUniformOreDrops(ModBlocks.DEEPSLATE_SHEN_SILVER_ORE.get(), ModItems.RAW_SHEN_SILVER_INGOT.get(), 1, 1);
        createUniformOreDrops(ModBlocks.NETHER_SHEN_SILVER_ORE.get(), ModItems.SHEN_SILVER_NUGGET.get(), 4, 9);

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void createUniformOreDrops(Block pBlock, Item item, float pMin, float pMax) {
        this.add(pBlock, block -> createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(pMin, pMax)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
}
