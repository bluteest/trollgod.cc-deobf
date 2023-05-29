/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.other.CPacketPlayer
 */
package me.hollow.realth.api.mixin.mixins.entity;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketPlayer.class})
public interface ICPacketPlayer {
    @Accessor(value="yaw")
    public void setYaw(float var1);
    @Accessor(value = "x")void setX(double x);

    @Accessor(value = "y")void setY(double y);

    @Accessor(value = "z")void setZ(double z);

    @Accessor(value="pitch")
    public void setPitch(float var1);
}

