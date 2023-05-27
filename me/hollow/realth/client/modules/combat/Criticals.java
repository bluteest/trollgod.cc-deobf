//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.combat;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "Criticals", category = Category.COMBAT)
public class Criticals extends Module
{
    public final Setting<Integer> packets;
    private final double[] packetzzz;
    
    public Criticals() {
        this.packets = (Setting<Integer>)this.register(new Setting("Packets", (Object)2, (Object)1, (Object)5));
        this.packetzzz = new double[] { 0.11, 0.11, 0.1100013579, 0.1100013579, 0.1100013579, 0.1100013579 };
    }
    
    @EventHandler
    public void onPacket(final PacketEvent.Send event) {
        final CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)Criticals.mc.world) instanceof EntityLivingBase) {
            if (!Criticals.mc.player.onGround) {
                return;
            }
            for (int i = 0; i < (int)this.packets.getValue(); ++i) {
                Criticals.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + this.packetzzz[i], Criticals.mc.player.posZ, false));
            }
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
        }
    }
}
