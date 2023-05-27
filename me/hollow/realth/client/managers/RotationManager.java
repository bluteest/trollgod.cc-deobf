//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.hollow.realth.api.mixin.mixins.entity.*;
import me.hollow.realth.api.util.*;

public class RotationManager
{
    Minecraft mc;
    private float yaw;
    private float pitch;
    
    public RotationManager() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void updateRotations() {
        this.yaw = this.mc.player.rotationYaw;
        this.pitch = this.mc.player.rotationPitch;
    }
    
    public void resetRotations() {
        this.mc.player.rotationYaw = this.yaw;
        this.mc.player.rotationYawHead = this.yaw;
        this.mc.player.rotationPitch = this.pitch;
    }
    
    public void setRotations(final float yaw, final float pitch) {
        this.mc.player.rotationYaw = yaw;
        this.mc.player.rotationYawHead = yaw;
        this.mc.player.rotationPitch = pitch;
    }
    
    public void lookAtPos(final BlockPos pos) {
        final float[] angle = MathUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() - 0.5f), (double)(pos.getZ() + 0.5f)));
        this.setRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final Vec3d vec3d) {
        final float[] angle = MathUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        this.setRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3dPacket(final Vec3d vec, final boolean normalize, final boolean update) {
        final float[] angle = this.getAngle(vec);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], normalize ? ((float)MathHelper.normalizeAngle((int)angle[1], 360)) : angle[1], this.mc.player.onGround));
        if (update) {
            ((IEntityPlayerSP)this.mc.player).setLastReportedYaw(angle[0]);
            ((IEntityPlayerSP)this.mc.player).setLastReportedPitch(angle[1]);
        }
    }
    
    public void lookAtVec3dPacket(final Vec3d vec, final boolean normalize) {
        final float[] angle = this.getAngle(vec);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], normalize ? ((float)MathHelper.normalizeAngle((int)angle[1], 360)) : angle[1], this.mc.player.onGround));
    }
    
    public void resetRotationsPacket() {
        final float[] angle = { this.mc.player.rotationYaw, this.mc.player.rotationPitch };
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], this.mc.player.onGround));
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public float[] getAngle(final Vec3d vec) {
        final Vec3d eyesPos = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { this.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - this.mc.player.rotationYaw), this.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - this.mc.player.rotationPitch) };
    }
    
    public float[] injectYawStep(final float[] angle, float steps) {
        if (steps < 0.1f) {
            steps = 0.1f;
        }
        if (steps > 1.0f) {
            steps = 1.0f;
        }
        if (steps < 1.0f && angle != null) {
            final float packetYaw = ((IEntityPlayerSP)this.mc.player).getLastReportedYaw();
            final float diff = MathHelper.wrapDegrees(angle[0] - packetYaw);
            if (Math.abs(diff) > 180.0f * steps) {
                angle[0] = packetYaw + diff * (180.0f * steps / Math.abs(diff));
            }
        }
        return new float[] { angle[0], angle[1] };
    }
    
    public int getYaw4D() {
        return MathHelper.floor(this.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
    }
    
    public String getDirection4D(final boolean northRed) {
        final int yaw = this.getYaw4D();
        if (yaw == 0) {
            return "South (+Z)";
        }
        if (yaw == 1) {
            return "West (-X)";
        }
        if (yaw == 2) {
            return (northRed ? "\u00c2§c" : "") + "North (-Z)";
        }
        if (yaw == 3) {
            return "East (+X)";
        }
        return "Loading...";
    }
    
    public boolean isInFov(final BlockPos pos) {
        final int yaw = this.getYaw4D();
        return (yaw != 0 || pos.getZ() - BlockUtil.mc.player.getPositionVector().z >= 0.0) && (yaw != 1 || pos.getX() - BlockUtil.mc.player.getPositionVector().x <= 0.0) && (yaw != 2 || pos.getZ() - BlockUtil.mc.player.getPositionVector().z <= 0.0) && (yaw != 3 || pos.getX() - BlockUtil.mc.player.getPositionVector().x >= 0.0);
    }
}
