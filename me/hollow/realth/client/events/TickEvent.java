//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.events;

import net.b0at.api.event.*;

public class TickEvent extends Event
{
    private final int stage;
    
    public TickEvent(final int stage) {
        this.stage = stage;
    }
    
    public final int getStage() {
        return this.stage;
    }
}
