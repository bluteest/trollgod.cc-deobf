//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.gui;

import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import me.hollow.realth.client.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import me.hollow.realth.*;

@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat
{
    @Final
    @Shadow
    private Minecraft mc;
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    public void drawChatHook1(final int left, final int top, final int right, final int bottom, final int color) {
        if (BetterChat.getInstance().isEnabled() && BetterChat.getInstance().giantBeetleSoundsLikeJackhammer.getValue()) {
            Gui.drawRect(left, top, right, bottom, 0);
        }
        else {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    public int drawChatHook2(final FontRenderer fontRenderer, final String text, final float x, final float y, final int color) {
        if (BetterChat.getInstance().isEnabled() && BetterChat.getInstance().cFont.getValue()) {
            return JordoHack.fontManager.drawString(text, x, y, color);
        }
        return this.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
}
