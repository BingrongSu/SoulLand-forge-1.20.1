package net.robert.soulland.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.SoulLand;
import net.robert.soulland.block.custom.AlchemyFurnaceBlock;
import net.robert.soulland.block.custom.CrystalBallBlock;
import net.robert.soulland.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SoulLand.MOD_ID);

    public static final RegistryObject<Block> SHEN_SILVER_BLOCK = registerBlock("shen_silver_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).strength(6f, 6.5f)));
    public static final RegistryObject<Block> SHEN_SILVER_ORE = registerBlock("shen_silver_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_SHEN_SILVER_ORE = registerBlock("deepslate_shen_silver_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE)));
    public static final RegistryObject<Block> NETHER_SHEN_SILVER_ORE = registerBlock("nether_shen_silver_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE)));

    public static final RegistryObject<Block> AWAKEN_BASE = registerBlock("awaken_base",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> ALCHEMY_FURNACE = registerBlock("alchemy_furnace",
            () -> new AlchemyFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> CRYSTAL_BALL = registerBlock("crystal_ball",
            () -> new CrystalBallBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .noOcclusion()
                    .lightLevel(state -> 9)
                    .isViewBlocking((state, world, pos) -> false)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
