//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event;

public class Event
{
    private boolean cancelled;
    public int stage;
    
    public final boolean isCancelled() {
        return this.cancelled;
    }
    
    public final void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public final void cancel() {
        this.setCancelled(true);
    }
    
    public final void restore() {
        this.setCancelled(false);
    }
    
    public Event() {
    }
    
    public Event(final int stage) {
        this.stage = stage;
    }
    
    public int getStage() {
        return this.stage;
    }
    
    public void setStage(final int stage) {
        this.stage = stage;
    }
}
