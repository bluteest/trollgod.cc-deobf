//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.gui.components.items.buttons;

import me.hollow.realth.api.property.*;
import me.hollow.realth.*;
import me.hollow.realth.client.modules.other.*;
import me.hollow.realth.api.util.*;
import com.mojang.realmsclient.gui.*;
import me.hollow.realth.client.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;

public class UnlimitedSlider extends Button
{
    public final Setting setting;
    
    public UnlimitedSlider(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.isHovering(mouseX, mouseY) ? JordoHack.INSTANCE.getColorManager().getColorWithAlpha((int)ClickGui.getInstance().hoverAlpha.getValue()) : ((int)ClickGui.getInstance().hoverAlpha.getValue()));
        UnlimitedSlider.mc.fontRenderer.drawStringWithShadow(" - " + this.setting.getName() + " " + ChatFormatting.GRAY + this.setting.getValue() + ChatFormatting.RESET + " +", this.x + 2.3f, this.y - 1.7f - TrollGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            UnlimitedSlider.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            if (this.isRight(mouseX)) {
                if (this.setting.getValue() instanceof Double) {
                    this.setting.setValue((Object)((double)this.setting.getValue() + 1.0));
                }
                else if (this.setting.getValue() instanceof Float) {
                    this.setting.setValue((Object)((float)this.setting.getValue() + 1.0f));
                }
                else if (this.setting.getValue() instanceof Integer) {
                    this.setting.setValue((Object)((int)this.setting.getValue() + 1));
                }
            }
            else if (this.setting.getValue() instanceof Double) {
                this.setting.setValue((Object)((double)this.setting.getValue() - 1.0));
            }
            else if (this.setting.getValue() instanceof Float) {
                this.setting.setValue((Object)((float)this.setting.getValue() - 1.0f));
            }
            else if (this.setting.getValue() instanceof Integer) {
                this.setting.setValue((Object)((int)this.setting.getValue() - 1));
            }
        }
    }
    
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    public int getHeight() {
        return 14;
    }
    
    public void toggle() {
    }
    
    public boolean getState() {
        return true;
    }
    
    public boolean isRight(final int x) {
        return x > this.x + (this.width + 7.4f) / 2.0f;
    }
}
