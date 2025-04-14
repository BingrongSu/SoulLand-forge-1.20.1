package net.robert.soulland.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalBallBlockEntity extends BlockEntity {
    protected final ContainerData data;
    private int usage = 0;
    private int maxUsage = 3;

    public CrystalBallBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRYSTAL_BALL_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CrystalBallBlockEntity.this.usage;
                    case 1 -> CrystalBallBlockEntity.this.maxUsage;
                    default -> -1;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CrystalBallBlockEntity.this.usage = pValue;
                    case 1 -> CrystalBallBlockEntity.this.maxUsage = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("crystal_ball.usage", usage);
        pTag.putInt("crystal_ball.max_usage", maxUsage);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        usage = pTag.getInt("crystal_ball.usage");
        maxUsage = pTag.getInt("crystal_ball.max_usage");
    }

    public boolean isBroken() {
        return ++usage > maxUsage;
    }
}
