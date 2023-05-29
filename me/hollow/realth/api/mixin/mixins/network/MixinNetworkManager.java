/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandlerContext
 *  net.minecraft.network.NetworkManager
 *  net.minecraft.network.Packet
 */
package me.hollow.realth.api.mixin.mixins.network;

import io.netty.channel.ChannelHandlerContext;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.TickRate;
import me.hollow.realth.client.events.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={NetworkManager.class})
public class MixinNetworkManager {
    @Inject(method={"sendPacket(Lnet/minecraft/network/Packet;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void onSendPacket(Packet<?> packetIn, CallbackInfo ci) {
        PacketEvent.Send event = new PacketEvent.Send(packetIn);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method={"channelRead0"}, at={@At(value="HEAD")}, cancellable=true)
    public void onReceivePacket(ChannelHandlerContext p_channelRead0_1_, Packet<?> packet, CallbackInfo ci) {
        PacketEvent.Receive event = new PacketEvent.Receive(packet);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
        if (packet instanceof SPacketTimeUpdate) {
            TickRate.update(packet);
        }
    }
    @Inject(method = "closeChannel", at = @At("HEAD"))
    public void closechanenl(ITextComponent message, CallbackInfo ci) {
        TickRate.reset();
    }
}

