package me.hollow.realth.api.mixin.mixins.render;

import me.hollow.realth.JordoHack;
import me.hollow.realth.client.modules.render.Nametags;
import me.hollow.realth.client.modules.render.NoRender;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderer.class})
public abstract class MixinEntityRenderer {
    @Inject(method={"updateCameraAndRender"}, at={@At(value="INVOKE", target="net/minecraft/client/gui/GuiIngame.renderGameOverlay(F)V")})
    public void onRender2D(float partialTicks, long nanoTime, CallbackInfo ci) {
        JordoHack.INSTANCE.getModuleManager().onRender2D();
    }

    @Inject(method={"renderWorldPass"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V")})
    public void onRender3D(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        JordoHack.INSTANCE.getModuleManager().onRender3D();
    }

    @Inject(method = "displayItemActivation(Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    public void preDisplayItemActivation(ItemStack stack, CallbackInfo callbackInfo) {
        if (stack.getItem() == Items.TOTEM_OF_UNDYING && NoRender.getInstance().isEnabled() && NoRender.getInstance().totemAnimation.getValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "renderItemActivation(IIF)V", at = @At("HEAD"), cancellable = true)
    public void preRenderItemActivation(int a, int b, float c, CallbackInfo callbackInfo) {
        if (NoRender.getInstance().isEnabled() && NoRender.getInstance().totemAnimation.getValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"drawNameplate"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderName(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking, CallbackInfo ci) {
        if (Nametags.getInstance().isEnabled()) {
            ci.cancel();
        }
    }
}
