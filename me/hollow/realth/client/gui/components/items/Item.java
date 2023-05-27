//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.gui.components.items;

import me.hollow.realth.api.*;
import me.hollow.realth.client.modules.other.*;
import java.awt.*;

public class Item implements Util
{
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private boolean hidden;
    final String name;
    public static final int BUTTON_OFF_ON;
    public static final int BUTTON_OFF_OFF;
    
    public Item(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getColor(final boolean enabled) {
        return enabled ? new Color((int)ClickGui.getInstance().enableRed.getValue(), (int)ClickGui.getInstance().enableGreen.getValue(), (int)ClickGui.getInstance().enableBlue.getValue(), (int)ClickGui.getInstance().enableAlpha.getValue()).getRGB() : new Color((int)ClickGui.getInstance().disableRed.getValue(), (int)ClickGui.getInstance().disableGreen.getValue(), (int)ClickGui.getInstance().disableBlue.getValue(), (int)ClickGui.getInstance().disableAlpha.getValue()).getRGB();
    }
    
    public int getColor() {
        return new Color(128, 128, 128).getRGB();
    }
    
    public void setLocation(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
    }
    
    public void update() {
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public boolean setHidden(final boolean hidden) {
        return this.hidden = hidden;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    static {
        BUTTON_OFF_ON = new Color(255, 32, 32).getRGB();
        BUTTON_OFF_OFF = new Color(255, 191, 202).getRGB();
    }
}
