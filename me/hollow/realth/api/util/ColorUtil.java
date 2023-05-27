//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import java.awt.*;
import org.lwjgl.opengl.*;

public class ColorUtil
{
    public static int getRainbow(final int speed, final int offset, final float s) {
        float hue = (float)((System.currentTimeMillis() + offset) % speed);
        return Color.getHSBColor(hue /= speed, s, 1.0f).getRGB();
    }
    
    public static void color(final int color) {
        GL11.glColor4f((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
    }
}
