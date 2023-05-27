//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.hollow.realth.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.*;
import me.hollow.realth.client.modules.render.*;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer
{
    @Inject(method = { "updateCameraAndRender" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiIngame.renderGameOverlay(F)V") })
    public void onRender2D(final float partialTicks, final long nanoTime, final CallbackInfo ci) {
        JordoHack.INSTANCE.getModuleManager().onRender2D();
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V") })
    public void onRender3D(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo ci) {
        JordoHack.INSTANCE.getModuleManager().onRender3D();
    }
    
    @Inject(method = { "displayItemActivation(Lnet/minecraft/item/ItemStack;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void preDisplayItemActivation(final ItemStack stack, final CallbackInfo callbackInfo) {
        if (stack.getItem() == Items.TOTEM_OF_UNDYING && NoRender.getInstance().isEnabled() && NoRender.getInstance().totemAnimation.getValue()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderItemActivation(IIF)V" }, at = { @At("HEAD") }, cancellable = true)
    public void preRenderItemActivation(final int a, final int b, final float c, final CallbackInfo callbackInfo) {
        if (NoRender.getInstance().isEnabled() && NoRender.getInstance().totemAnimation.getValue()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "drawNameplate" }, at = { @At("HEAD") }, cancellable = true)
    private static void renderName(final FontRenderer fontRendererIn, final String str, final float x, final float y, final float z, final int verticalShift, final float viewerYaw, final float viewerPitch, final boolean isThirdPersonFrontal, final boolean isSneaking, final CallbackInfo ci) {
        if (Nametags.getInstance().isEnabled()) {
            ci.cancel();
        }
    }
}
