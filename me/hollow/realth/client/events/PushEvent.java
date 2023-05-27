//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.events;

import me.hollow.realth.api.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class PushEvent extends EventStage
{
    private final Type type;
    
    public PushEvent(final Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        BLOCK, 
        ENTITY, 
        PISTON;
    }
}
