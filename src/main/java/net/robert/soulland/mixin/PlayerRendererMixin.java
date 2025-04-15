package net.robert.soulland.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import net.robert.soulland.block.ModBlocks;
import net.robert.soulland.item.ModItems;
import net.robert.soulland.network.NetworkHandler;
import net.robert.soulland.network.ShowedRingsSyncPacket;
import net.robert.soulland.stat.DataCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void render(AbstractClientPlayer pEntity, float pEntityYaw, float pPartialTicks, PoseStack poseStack,
                       MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (!DataCache.clientPlayersShowedRings.containsKey(pEntity.getUUID())) {
            NetworkHandler.INSTANCE.sendToServer(new ShowedRingsSyncPacket(new ArrayList<>(), 0L, pEntity, true));
            return;
        }
        List<Double> years = DataCache.getPlayerShowedRings(pEntity);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        int n = years.size();
        List<PoseStack> poseStackList = Collections.nCopies(n, poseStack);
        int startSlot = (9 - n) / 2;                                    // 按0～8九个槽位计算，第一个魂环的槽位
        float rotateV = 0.6f;                                           // 魂环转速
        float sepMultiplier = 1.32f;                                    // 魂环间距乘数
        for (int j = 0; j < n; j++) {
            double pos, target = 0.5;
            long currentTick = pEntity.level().getGameTime();
            long showedTick = DataCache.getPlayerShowedTick(pEntity);
            if (currentTick < showedTick)   showedTick = 0L;
            long timeTick = (long) (0.8 * 20L);                         // 单个魂环显示的时间，单位：tick
            double advTick = timeTick * 0.44d;                          // 下一个魂环显示的提前量，单位：tick
            double dTickTotal = currentTick - showedTick + pPartialTicks;
            double dTick = dTickTotal - (timeTick - advTick) * j;

            double leftBound = j * (timeTick - advTick);
            double rightBound = leftBound + timeTick;

            float targetRadius = (float) Math.pow(sepMultiplier, startSlot + j + 1);
            float radius;
            if (dTickTotal < leftBound) {
                pos = 0;
                radius = 0;
            } else if (dTickTotal < rightBound) {
                pos = target * Math.sin(0.5 * Math.PI * (dTick / timeTick));
                radius = (float) (Math.sin(0.5 * Math.PI * (dTick / timeTick)) * targetRadius);
            } else {
                pos = target;
                radius = targetRadius;
            }

            poseStackList.get(j).pushPose();
            poseStackList.get(j).translate(0, 0.5, 0);
            poseStackList.get(j).scale(1.3f, 1f, 1.3f);
            poseStackList.get(j).scale(radius, 1f, radius);
            poseStackList.get(j).translate(0, pos + 0.001 * j, 0);
            Axis axis = j % 2 == 0 ? Axis.YP : Axis.YN;
            poseStackList.get(j).mulPose(axis.rotationDegrees(pEntity.level().getGameTime() * rotateV % 360));
            poseStackList.get(j).mulPose(axis.rotationDegrees(pEntity.level().getDayTime() * rotateV % 360));
//            matrices.translate(0, Math.sin(Math.sin(player.getWorld().getTickOrder() / 200.0) / 16d * 2d), 0); // 上下浮动效果
//            itemRenderer.renderItem(suitableStack(currentWuHun.get(j)), ModelTransformationMode.HEAD, lightLevel, OverlayTexture.DEFAULT_UV, matricesList.get(j), vertexConsumerProvider, player.getWorld(), 1);
            itemRenderer.renderStatic(suitableStack(years.get(j)), ItemDisplayContext.HEAD,
                    pPackedLight, 0, poseStack, pBuffer, pEntity.level(), 0);
            poseStackList.get(j).popPose();
        }
    }

    @Unique
    private ItemStack suitableStack(double year) {
        List<Item> rings = List.of(
                ModItems.SOUL_RING_TEN.get(),
                ModItems.SOUL_RING_HUNDRED.get(),
                ModItems.SOUL_RING_THOUSAND.get(),
                ModItems.SOUL_RING_TEN_THOUSAND.get(),
                ModItems.SOUL_RING_HUNDRED_THOUSAND.get()
                );
        return new ItemStack(rings.get(((int) Math.log10(year)) - 1));
    }
}
