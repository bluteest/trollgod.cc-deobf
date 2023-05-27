//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.api.*;
import me.hollow.realth.api.util.font.*;
import java.awt.*;

public class FontManager implements Util
{
    public CFontRenderer fontRenderer;
    private boolean customFont;
    
    public FontManager() {
        this.fontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, true);
        this.customFont = false;
    }
    
    public void setCustomFont(final boolean bool) {
        this.customFont = bool;
    }
    
    public String normalizeCases(final Object o) {
        return Character.toUpperCase(o.toString().charAt(0)) + o.toString().toLowerCase().substring(1);
    }
    
    public int drawString(final String text, final float x, final float y, final int color) {
        if (this.customFont) {
            return (int)this.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        return FontManager.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
    
    public int drawCenteredString(final String text, final float x, final float y, final int color, final boolean shadow) {
        if (this.customFont) {
            return (int)this.fontRenderer.drawCenteredString(text, x, y, color, shadow);
        }
        return FontManager.mc.fontRenderer.drawString(text, x - this.getStringWidth(text) / 2.0f, y, color, shadow);
    }
    
    public int drawStringNoShadow(final String text, final float x, final float y, final int color) {
        if (this.customFont) {
            return (int)this.fontRenderer.drawString(text, (double)x, (double)y, color, false);
        }
        return FontManager.mc.fontRenderer.drawString(text, x, y, color, false);
    }
    
    public int getStringHeight() {
        if (this.customFont) {
            return this.fontRenderer.getStringHeight("A");
        }
        return 9;
    }
    
    public int getStringWidth(final String text) {
        if (this.customFont) {
            return (int)this.fontRenderer.getStringWidth(text);
        }
        return FontManager.mc.fontRenderer.getStringWidth(text);
    }
}
