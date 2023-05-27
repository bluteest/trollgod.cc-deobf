//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.gui.components.items.buttons;

import me.hollow.realth.api.property.*;
import me.hollow.realth.api.util.*;
import me.hollow.realth.*;
import me.hollow.realth.client.gui.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import me.hollow.realth.client.events.*;

public class StringButton extends Button
{
    private final Setting setting;
    public boolean isListening;
    private CurrentString currentString;
    
    public StringButton(final Setting setting) {
        super(setting.getName());
        this.currentString = new CurrentString("");
        this.setting = setting;
        this.width = 15;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.getColor(this.getState()));
        if (this.isListening) {
            JordoHack.fontManager.drawString(this.currentString.getString() + "_", this.x + 2.3f, this.y - 1.7f - TrollGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        }
        else {
            JordoHack.fontManager.drawString((this.setting.getName().equals("Buttons") ? "Buttons " : (this.setting.getName().equals("Prefix") ? ("Prefix  " + ChatFormatting.GRAY) : "")) + this.setting.getValue(), this.x + 2.3f, this.y - 1.7f - TrollGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            StringButton.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.isListening) {
            switch (keyCode) {
                case 1: {
                    break;
                }
                case 28: {
                    this.enterString();
                    break;
                }
                case 14: {
                    this.setString(removeLastChar(this.currentString.getString()));
                    break;
                }
                default: {
                    if (!ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                        break;
                    }
                    this.setString(this.currentString.getString() + typedChar);
                    break;
                }
            }
            JordoHack.INSTANCE.getEventManager().fireEvent((Object)new ClientEvent());
        }
    }
    
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    private void enterString() {
        if (this.currentString.getString().isEmpty()) {
            this.setting.setValue(this.setting.getDefaultValue());
        }
        else {
            this.setting.setValue((Object)this.currentString.getString());
        }
        this.setString("");
        super.onMouseClick();
    }
    
    public int getHeight() {
        return 14;
    }
    
    public void toggle() {
        this.isListening = !this.isListening;
    }
    
    public boolean getState() {
        return !this.isListening;
    }
    
    public void setString(final String newString) {
        this.currentString = new CurrentString(newString);
    }
    
    public static String removeLastChar(final String str) {
        String output = "";
        if (str != null && str.length() > 0) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }
    
    public static class CurrentString
    {
        private final String string;
        
        public CurrentString(final String string) {
            this.string = string;
        }
        
        public String getString() {
            return this.string;
        }
    }
}
