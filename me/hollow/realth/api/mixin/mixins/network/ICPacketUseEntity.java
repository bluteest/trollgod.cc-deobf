//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.network;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketUseEntity.class })
public interface ICPacketUseEntity
{
    @Accessor("entityId")
    void setEntityId(final int p0);
    
    @Accessor("action")
    void setAction(final CPacketUseEntity.Action p0);
}
