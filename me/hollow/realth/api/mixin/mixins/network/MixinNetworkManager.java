//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.network;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;
import net.minecraft.network.play.server.*;
import me.hollow.realth.api.util.*;
import net.minecraft.util.text.*;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void onSendPacket(final Packet<?> packetIn, final CallbackInfo ci) {
        final PacketEvent.Send event = new PacketEvent.Send(packetIn);
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    public void onReceivePacket(final ChannelHandlerContext p_channelRead0_1_, final Packet<?> packet, final CallbackInfo ci) {
        final PacketEvent.Receive event = new PacketEvent.Receive(packet);
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
        if (packet instanceof SPacketTimeUpdate) {
            TickRate.update(packet);
        }
    }
    
    @Inject(method = { "closeChannel" }, at = { @At("HEAD") })
    public void closechanenl(final ITextComponent message, final CallbackInfo ci) {
        TickRate.reset();
    }
}
