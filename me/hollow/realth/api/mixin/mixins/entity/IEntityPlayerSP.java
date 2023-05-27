//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityPlayerSP.class })
public interface IEntityPlayerSP
{
    @Accessor("handActive")
    void mm_setHandActive(final boolean p0);
    
    @Accessor("serverSneakState")
    void setServerSneakState(final boolean p0);
    
    @Accessor("serverSneakState")
    boolean getServerSneakState();
    
    @Accessor("serverSprintState")
    void setServerSprintState(final boolean p0);
    
    @Accessor("serverSprintState")
    boolean getServerSprintState();
    
    @Accessor("prevOnGround")
    void setPrevOnGround(final boolean p0);
    
    @Accessor("prevOnGround")
    boolean getPrevOnGround();
    
    @Accessor("autoJumpEnabled")
    void setAutoJumpEnabled(final boolean p0);
    
    @Accessor("autoJumpEnabled")
    boolean getAutoJumpEnabled();
    
    @Accessor("lastReportedPosX")
    void setLastReportedPosX(final double p0);
    
    @Accessor("lastReportedPosX")
    double getLastReportedPosX();
    
    @Accessor("lastReportedPosY")
    void setLastReportedPosY(final double p0);
    
    @Accessor("lastReportedPosY")
    double getLastReportedPosY();
    
    @Accessor("lastReportedPosZ")
    void setLastReportedPosZ(final double p0);
    
    @Accessor("lastReportedPosZ")
    double getLastReportedPosZ();
    
    @Accessor("lastReportedYaw")
    void setLastReportedYaw(final float p0);
    
    @Accessor("lastReportedYaw")
    float getLastReportedYaw();
    
    @Accessor("lastReportedPitch")
    void setLastReportedPitch(final float p0);
    
    @Accessor("lastReportedPitch")
    float getLastReportedPitch();
    
    @Accessor("positionUpdateTicks")
    void setPositionUpdateTicks(final int p0);
    
    @Accessor("positionUpdateTicks")
    int getPositionUpdateTicks();
    
    @Invoker("onUpdateWalkingPlayer")
    void invokeOnUpdateWalkingPlayer();
}
