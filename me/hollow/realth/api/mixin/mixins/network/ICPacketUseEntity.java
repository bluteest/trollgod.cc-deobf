/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.other.CPacketUseEntity
 *  net.minecraft.network.play.other.CPacketUseEntity$Action
 */
package me.hollow.realth.api.mixin.mixins.network;

import net.minecraft.network.play.client.CPacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketUseEntity.class})
public interface ICPacketUseEntity {
    @Accessor(value="entityId")
    public void setEntityId(int var1);

    @Accessor(value="action")
    public void setAction(CPacketUseEntity.Action var1);
}

