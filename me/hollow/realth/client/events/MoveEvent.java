//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.events;

import net.b0at.api.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class MoveEvent extends Event
{
    private double motionX;
    private double motionY;
    private double motionZ;
    
    public MoveEvent(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public double getMotionX() {
        return this.motionX;
    }
    
    public double getMotionY() {
        return this.motionY;
    }
    
    public double getMotionZ() {
        return this.motionZ;
    }
    
    public void setMotionX(final double motionX) {
        this.motionX = motionX;
    }
    
    public void setMotionY(final double motionY) {
        this.motionY = motionY;
    }
    
    public void setMotionZ(final double motionZ) {
        this.motionZ = motionZ;
    }
}
