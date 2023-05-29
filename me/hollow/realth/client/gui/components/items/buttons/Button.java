package me.hollow.realth.client.gui.components.items.buttons;

import java.awt.Color;
import java.util.ArrayList;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.RenderUtil;
import me.hollow.realth.client.gui.TrollGui;
import me.hollow.realth.client.gui.components.Component;
import me.hollow.realth.client.gui.components.items.Item;
import me.hollow.realth.client.modules.other.ClickGui;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Button
        extends Item {
    private boolean state;

    public Button(String name) {
        super(name);
        this.height = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + 95, this.y + (float)this.height - 0.5f, this.getColor(this.getState()));
        JordoHack.fontManager.drawString(this.getName(), this.x + 2.3f, this.y - 2.0f - (float)TrollGui.getClickGui().getTextOffset(), this.getState() ? new Color(ClickGui.getInstance().textEnableRed.getValue(), ClickGui.getInstance().textEnableGreen.getValue(), ClickGui.getInstance().textEnableBlue.getValue(), 255).getRGB() : new Color(ClickGui.getInstance().textDisableRed.getValue(), ClickGui.getInstance().textDisableGreen.getValue(), ClickGui.getInstance().textDisableBlue.getValue(), 255).getRGB(), false);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onMouseClick();
        }
    }

    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.BLOCK_METAL_PLACE, (float)10.0f));
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

    public boolean isHovering(int mouseX, int mouseY) {
        ArrayList<Component> components = TrollGui.getClickGui().getComponents();
        for (int i = 0; i < components.size(); ++i) {
            if (!components.get((int)i).drag) continue;
            return false;
        }
        return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
    }
}