//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.managers;

import java.awt.Font;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.Util;
import me.hollow.realth.api.util.font.CFontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FontManager
implements Util {
    public CFontRenderer fontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, true);
    private boolean customFont = false;
    public int scaledHeight;
    public int scaledWidth;
    public int scaleFactor;

    public void setCustomFont(boolean bool) {
        this.customFont = bool;
    }
    public String normalizeCases(Object o) {
        return Character.toUpperCase(o.toString().charAt(0)) + o.toString().toLowerCase().substring(1);
    }

    public int drawString(String text, float x, float y, int color, boolean shadow) {
        if (this.customFont) {
            return (int) this.fontRenderer.drawStringWithShadow(text, x, y, color);
        }
        return FontManager.mc.fontRenderer.drawString(text, x, y, color, shadow);
    }
    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            JordoHack.INSTANCE.getFontManager().updateResolution();
        }
    }

    public int drawCenteredString(String text, float x, float y, int color, boolean shadow) {
        if (this.customFont) {
            return (int)this.fontRenderer.drawCenteredString(text, x, y, color, shadow);
        }
        return FontManager.mc.fontRenderer.drawString(text, x - (float)this.getStringWidth(text) / 2.0f, y, color, shadow);
    }

    public int drawStringNoShadow(String text, float x, float y, int color) {
        if (this.customFont) {
            return (int)this.fontRenderer.drawString(text, x, y, color, false);
        }
        return FontManager.mc.fontRenderer.drawString(text, x, y, color, false);
    }
    public void updateResolution() {
        this.scaledWidth = FontManager.mc.displayWidth;
        this.scaledHeight = FontManager.mc.displayHeight;
        this.scaleFactor = 1;
        boolean flag = mc.isUnicode();
        int i = FontManager.mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        double scaledWidthD = (double) this.scaledWidth / (double) this.scaleFactor;
        double scaledHeightD = (double) this.scaledHeight / (double) this.scaleFactor;
        this.scaledWidth = MathHelper.ceil(scaledWidthD);
        this.scaledHeight = MathHelper.ceil(scaledHeightD);
    }

    public int getStringHeight() {
        if (this.customFont) {
            return this.fontRenderer.getStringHeight("A");
        }
        return FontManager.mc.fontRenderer.FONT_HEIGHT;
    }

    public int getStringWidth(String text) {
        if (this.customFont) {
            return (int)this.fontRenderer.getStringWidth(text);
        }
        return FontManager.mc.fontRenderer.getStringWidth(text);
    }
}

