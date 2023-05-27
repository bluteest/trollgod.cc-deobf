//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.events;

import net.b0at.api.event.*;
import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;

public class ClientEvent extends Event
{
    Module module;
    Setting setting;
    private int stage;
    
    public ClientEvent(final Setting setting) {
        this.setting = setting;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public Setting getSetting() {
        return this.setting;
    }
    
    public ClientEvent() {
    }
    
    public ClientEvent(final int stage) {
        this.stage = stage;
    }
    
    public int getStage() {
        return this.stage;
    }
    
    public void setStage(final int stage) {
        this.stage = stage;
    }
}
