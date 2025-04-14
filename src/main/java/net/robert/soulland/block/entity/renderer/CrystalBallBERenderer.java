package net.robert.soulland.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.data.ModelData;
import net.robert.soulland.block.ModBlocks;
import net.robert.soulland.block.entity.CrystalBallBlockEntity;

public class CrystalBallBERenderer implements BlockEntityRenderer<CrystalBallBlockEntity> {
    public BlockEntityRendererProvider.Context context;

    public CrystalBallBERenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(CrystalBallBlockEntity blockEntity, float tickDelta, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        // 启用透明渲染
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        Level level = blockEntity.getLevel();
        poseStack.pushPose();
        assert level != null;
        double offset = Math.sin((level.getGameTime() + tickDelta) / 8.0) / 16d * 2d;
        // 移动物品
        poseStack.translate(0.5, offset + 0.5, 0.5);
        // 中心点调整并旋转
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees((level.getGameTime() + tickDelta) * 4));
//
//        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(ModBlocks.CRYSTAL_BALL.get().defaultBlockState(), poseStack,
//                bufferSource, light, overlay);
//        context.getBlockRenderDispatcher().renderSingleBlock(
//                ModBlocks.CRYSTAL_BALL.get().defaultBlockState(),
//                poseStack,
//                bufferSource,
//                light,
//                overlay,
//                ModelData.EMPTY,
//                RenderType.translucent()
//        );
//        Minecraft.getInstance().getItemRenderer().render();
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(ModBlocks.CRYSTAL_BALL.get()),
                ItemDisplayContext.HEAD, light, overlay, poseStack, bufferSource, level, 0);

//        getItemRenderer().renderItem(new ItemStack(ModBlocks.JX_BALL, 1), ModelTransformationMode.HEAD, getLightLevel(world, entity.getPos()), overlay, matrices, vertexConsumers, world, 0);
        poseStack.popPose();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
