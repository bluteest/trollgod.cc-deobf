//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.network;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketChat.class })
public interface ISPacketChat
{
    @Accessor("chatComponent")
    void setChatComponent(final ITextComponent p0);
}
