//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.property;

import java.awt.*;
import java.util.function.*;

public class ColorSetting extends Setting<Color>
{
    public ColorSetting(final String name, final Color value) {
        super(name, value);
    }
    
    public ColorSetting(final String name, final Color value, final Predicate<Color> shown) {
        super(name, value, shown);
    }
    
    public void setColor(final Color value) {
        this.value = (T)value;
    }
}
