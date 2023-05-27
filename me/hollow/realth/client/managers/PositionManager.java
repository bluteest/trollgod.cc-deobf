//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class PositionManager
{
    Minecraft mc;
    private double x;
    private double y;
    private double z;
    private boolean onground;
    
    public PositionManager() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void updatePosition() {
        this.x = this.mc.player.posX;
        this.y = this.mc.player.posY;
        this.z = this.mc.player.posZ;
        this.onground = this.mc.player.onGround;
    }
    
    public void restorePosition() {
        this.mc.player.posX = this.x;
        this.mc.player.posY = this.y;
        this.mc.player.posZ = this.z;
        this.mc.player.onGround = this.onground;
    }
    
    public void setPlayerPosition(final double x, final double y, final double z) {
        this.mc.player.posX = x;
        this.mc.player.posY = y;
        this.mc.player.posZ = z;
    }
    
    public void setPlayerPosition(final double x, final double y, final double z, final boolean onground) {
        this.mc.player.posX = x;
        this.mc.player.posY = y;
        this.mc.player.posZ = z;
        this.mc.player.onGround = onground;
    }
    
    public void setPositionPacket(final double x, final double y, final double z, final boolean onGround, final boolean setPos, final boolean noLagBack) {
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, onGround));
        if (setPos) {
            this.mc.player.setPosition(x, y, z);
            if (noLagBack) {
                this.updatePosition();
            }
        }
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
}
