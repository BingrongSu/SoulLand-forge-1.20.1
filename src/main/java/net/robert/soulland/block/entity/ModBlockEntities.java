package net.robert.soulland.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.robert.soulland.SoulLand;
import net.robert.soulland.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SoulLand.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlchemyFurnaceBlockEntity>> ALCHEMY_FURNACE_BE =
            BLOCK_ENTITIES.register("alchemy_furnace_be", () ->
                    BlockEntityType.Builder.of(AlchemyFurnaceBlockEntity::new,
                            ModBlocks.ALCHEMY_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrystalBallBlockEntity>> CRYSTAL_BALL_BE =
            BLOCK_ENTITIES.register("crystal_ball_be", () ->
                    BlockEntityType.Builder.of(CrystalBallBlockEntity::new,
                            ModBlocks.CRYSTAL_BALL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
