//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.events;

import net.b0at.api.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;

@Cancelable
public class DamageBlockEvent extends Event
{
    BlockPos pos;
    int progress;
    int breakerId;
    
    public DamageBlockEvent(final BlockPos pos, final int progress, final int breakerId) {
        this.pos = pos;
        this.progress = progress;
        this.breakerId = breakerId;
    }
    
    public BlockPos getPosition() {
        return this.pos;
    }
    
    public int getProgress() {
        return this.progress;
    }
    
    public int getBreakerId() {
        return this.breakerId;
    }
}
