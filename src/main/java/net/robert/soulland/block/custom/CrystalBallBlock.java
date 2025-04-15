package net.robert.soulland.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.robert.soulland.block.ModBlocks;
import net.robert.soulland.block.entity.AlchemyFurnaceBlockEntity;
import net.robert.soulland.block.entity.CrystalBallBlockEntity;
import net.robert.soulland.block.entity.ModBlockEntities;
import net.robert.soulland.stat.DataCache;
import org.jetbrains.annotations.Nullable;

public class CrystalBallBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(3,2,3,13,12,13);

    public CrystalBallBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            CrystalBallBlockEntity blockEntity = (CrystalBallBlockEntity) pLevel.getBlockEntity(pPos);
            assert blockEntity != null;
            if (pLevel.getBlockState(pPos.below(1)).is(ModBlocks.AWAKEN_BASE.get()) && blockEntity.isBroken()
                    || !pLevel.getBlockState(pPos.below(1)).is(ModBlocks.AWAKEN_BASE.get())) {
                pLevel.destroyBlock(pPos, false);
            }
            if (pLevel.getBlockState(pPos).is(ModBlocks.CRYSTAL_BALL.get())) {
                DataCache.globalData.awaken((ServerPlayer) pPlayer);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrystalBallBlockEntity(blockPos, blockState);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1.0F;
    }
}
