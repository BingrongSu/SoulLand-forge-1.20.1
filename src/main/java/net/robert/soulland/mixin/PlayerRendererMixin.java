package net.robert.soulland.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.robert.soulland.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void render(AbstractClientPlayer pEntity, float pEntityYaw, float pPartialTicks, PoseStack matrixStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        matrixStack.pushPose();
        matrixStack.translate(1, 1, 0);
        matrixStack.scale(5, 5, 5);
        itemRenderer.renderStatic(pEntity, new ItemStack(ModItems.SHEN_SILVER_INGOT.get()), ItemDisplayContext.HEAD, false, matrixStack,
                pBuffer, Minecraft.getInstance().level, pPackedLight, OverlayTexture.NO_OVERLAY, 0);
        matrixStack.popPose();
    }

}
