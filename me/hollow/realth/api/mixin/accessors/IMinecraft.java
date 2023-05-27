//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.accessors;

import net.minecraft.util.*;

public interface IMinecraft
{
    void setRightClickDelayTimer(final int p0);
    
    Timer getTimer();
}
