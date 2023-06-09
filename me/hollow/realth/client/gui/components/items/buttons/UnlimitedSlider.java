//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.other.audio.ISound
 *  net.minecraft.other.audio.PositionedSoundRecord
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.util.SoundEvent
 */
package me.hollow.realth.client.gui.components.items.buttons;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.RenderUtil;
import me.hollow.realth.client.gui.TrollGui;
import me.hollow.realth.client.modules.other.ClickGui;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class UnlimitedSlider
extends Button {
    public final Setting setting;

    public UnlimitedSlider(Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + (float)this.width + 7.4f, this.y + (float)this.height - 0.5f, this.isHovering(mouseX, mouseY) ? JordoHack.INSTANCE.getColorManager().getColorWithAlpha(ClickGui.getInstance().hoverAlpha.getValue()) : ClickGui.getInstance().hoverAlpha.getValue().intValue());
        UnlimitedSlider.mc.fontRenderer.drawStringWithShadow(" - " + this.setting.getName() + " " + (Object)ChatFormatting.GRAY + this.setting.getValue() + (Object)ChatFormatting.RESET + " +", this.x + 2.3f, this.y - 1.7f - (float)TrollGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            if (this.isRight(mouseX)) {
                if (this.setting.getValue() instanceof Double) {
                    this.setting.setValue((Double)this.setting.getValue() + 1.0);
                } else if (this.setting.getValue() instanceof Float) {
                    this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() + 1.0f));
                } else if (this.setting.getValue() instanceof Integer) {
                    this.setting.setValue((Integer)this.setting.getValue() + 1);
                }
            } else if (this.setting.getValue() instanceof Double) {
                this.setting.setValue((Double)this.setting.getValue() - 1.0);
            } else if (this.setting.getValue() instanceof Float) {
                this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() - 1.0f));
            } else if (this.setting.getValue() instanceof Integer) {
                this.setting.setValue((Integer)this.setting.getValue() - 1);
            }
        }
    }

    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public void toggle() {
    }

    @Override
    public boolean getState() {
        return true;
    }

    public boolean isRight(int x) {
        return (float)x > this.x + ((float)this.width + 7.4f) / 2.0f;
    }
}

