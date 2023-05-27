//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.gui.components.items.buttons;

import me.hollow.realth.client.gui.components.items.*;
import me.hollow.realth.api.util.*;
import me.hollow.realth.*;
import me.hollow.realth.client.gui.*;
import me.hollow.realth.client.modules.other.*;
import java.awt.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import me.hollow.realth.client.gui.components.*;
import java.util.*;

public class Button extends Item
{
    private boolean state;
    
    public Button(final String name) {
        super(name);
        this.height = 15;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + 95.0f, this.y + this.height - 0.5f, this.getColor(this.getState()));
        JordoHack.fontManager.drawString(this.getName(), this.x + 2.3f, this.y - 2.0f - TrollGui.getClickGui().getTextOffset(), this.getState() ? new Color((int)ClickGui.getInstance().textEnableRed.getValue(), (int)ClickGui.getInstance().textEnableGreen.getValue(), (int)ClickGui.getInstance().textEnableBlue.getValue(), 255).getRGB() : new Color((int)ClickGui.getInstance().textDisableRed.getValue(), (int)ClickGui.getInstance().textDisableGreen.getValue(), (int)ClickGui.getInstance().textDisableBlue.getValue(), 255).getRGB());
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onMouseClick();
        }
    }
    
    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        Button.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_METAL_PLACE, 10.0f));
    }
    
    public void toggle() {
    }
    
    public boolean getState() {
        return this.state;
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        final ArrayList<Component> components = TrollGui.getClickGui().getComponents();
        for (int i = 0; i < components.size(); ++i) {
            if (components.get(i).drag) {
                return false;
            }
        }
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.height;
    }
}
