/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.gui.components.items;

import java.awt.Color;
import me.hollow.realth.api.Util;
import me.hollow.realth.client.modules.other.ClickGui;

public class Item
implements Util {
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private boolean hidden;
    final String name;
    public static final int BUTTON_OFF_ON = new Color(255, 32, 32).getRGB();
    public static final int BUTTON_OFF_OFF = new Color(255, 191, 202).getRGB();

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getColor(boolean enabled) {
        return enabled ? new Color(ClickGui.getInstance().enableRed.getValue(), ClickGui.getInstance().enableGreen.getValue(), ClickGui.getInstance().enableBlue.getValue(), ClickGui.getInstance().enableAlpha.getValue()).getRGB() : new Color(ClickGui.getInstance().disableRed.getValue(), ClickGui.getInstance().disableGreen.getValue(), ClickGui.getInstance().disableBlue.getValue(), ClickGui.getInstance().disableAlpha.getValue()).getRGB();
    }

    public int getColor() {
        return new Color(128, 128, 128).getRGB();
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
    }

    public void update() {
    }

    public void onKeyTyped(char typedChar, int keyCode) {
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

    public boolean setHidden(boolean hidden) {
        this.hidden = hidden;
        return this.hidden;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

