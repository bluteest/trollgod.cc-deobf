//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.Minecraft
 *  net.minecraft.other.gui.FontRenderer
 *  net.minecraft.other.gui.Gui
 *  net.minecraft.other.gui.GuiNewChat
 */
package me.hollow.realth.api.mixin.mixins.gui;

import me.hollow.realth.JordoHack;
import me.hollow.realth.client.modules.render.BetterChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={GuiNewChat.class})
public class MixinGuiNewChat {
    @Final
    @Shadow
    private Minecraft mc;

    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    public void drawChatHook1(int left, int top, int right, int bottom, int color) {
        if (BetterChat.getInstance().isEnabled() && BetterChat.getInstance().giantBeetleSoundsLikeJackhammer.getValue().booleanValue()) {
            Gui.drawRect((int)left, (int)top, (int)right, (int)bottom, (int)0);
        } else {
            Gui.drawRect((int)left, (int)top, (int)right, (int)bottom, (int)color);
        }
    }

    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    public int drawChatHook2(FontRenderer fontRenderer, String text, float x, float y, int color) {
        if (BetterChat.getInstance().isEnabled() && BetterChat.getInstance().cFont.getValue().booleanValue()) {
            return JordoHack.fontManager.drawString(text, x, y, color, false);
        }
        return this.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
}

