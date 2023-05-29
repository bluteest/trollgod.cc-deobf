//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.other.CPacketPlayer$Position
 *  net.minecraft.network.play.other.CPacketUseEntity
 *  net.minecraft.network.play.other.CPacketUseEntity$Action
 *  net.minecraft.world.World
 */
package me.hollow.realth.client.modules.combat;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.PacketEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;

@ModuleManifest(label="Criticals", category=Module.Category.COMBAT)
public class Criticals
extends Module {
    public final Setting<Integer> packets = this.register(new Setting<Integer>("Packets", 2, 1, 5));
    private final double[] packetzzz = new double[]{0.11, 0.11, 0.1100013579, 0.1100013579, 0.1100013579, 0.1100013579};

    @EventHandler
    public void onPacket(PacketEvent.Send event) {
        CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)this.mc.world) instanceof EntityLivingBase) {
            if (!this.mc.player.onGround) {
                return;
            }
            for (int i = 0; i < this.packets.getValue(); ++i) {
                this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + this.packetzzz[i], this.mc.player.posZ, false));
            }
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
        }
    }
}

