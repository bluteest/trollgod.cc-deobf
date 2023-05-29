//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.SPacketEntityStatus
 *  net.minecraft.world.World
 */
package me.hollow.realth.client.managers;

import java.util.HashMap;
import java.util.Map;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.Util;
import me.hollow.realth.api.util.MessageUtil;
import me.hollow.realth.client.events.PacketEvent;
import me.hollow.realth.client.events.TickEvent;
import me.hollow.realth.client.events.TotemPopEvent;
import me.hollow.realth.client.modules.misc.PopCounter;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;

public class PopManager
implements Util {
    private final Map<String, Integer> popMap = new HashMap<String, Integer>();

    @EventHandler
    public void onUpdate(TickEvent event) {
        if (PopManager.mc.player == null || PopManager.mc.world == null) {
            return;
        }
        for (int i = 0; i < PopManager.mc.world.playerEntities.size(); ++i) {
            EntityPlayer player = (EntityPlayer)PopManager.mc.world.playerEntities.get(i);
            if (!(player.getHealth() <= 0.0f) || !this.popMap.containsKey(player.getName())) continue;
            int l_Count = this.popMap.get(player.getName());
            if (PopCounter.getInstance().isEnabled()) {
                if (l_Count == 1) {
                    MessageUtil.sendClientMessage(player.getName() + " died after popping " + ChatFormatting.GREEN + this.popMap.get(player.getName()) + ChatFormatting.LIGHT_PURPLE + " totem!", player.getEntityId());
                } else {
                    MessageUtil.sendClientMessage(player.getName() + " died after popping " + ChatFormatting.GREEN + this.popMap.get(player.getName()) + ChatFormatting.LIGHT_PURPLE + " totems!", player.getEntityId());
                }
            }
            this.popMap.remove(player.getName(), this.popMap.get(player.getName()));
        }
    }


    @EventHandler
    public void onPacket(PacketEvent.Receive event) {
        SPacketEntityStatus packet;
        if (PopManager.mc.player == null || PopManager.mc.world == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getOpCode() == 35) {
            Entity entity = packet.getEntity((World)PopManager.mc.world);
            JordoHack.INSTANCE.getEventManager().fireEvent(new TotemPopEvent((EntityPlayer)entity));
            if (this.popMap.get(entity.getName()) == null) {
                this.popMap.put(entity.getName(), 1);
                int popCounter = this.popMap.get(entity.getName());
                if (PopCounter.getInstance().isEnabled()) {
                    MessageUtil.sendClientMessage(entity.getName() + " has popped " + ChatFormatting.RED + popCounter + ChatFormatting.LIGHT_PURPLE + " time in total!", entity.getEntityId());
                }
            } else if (this.popMap.get(entity.getName()) != null) {
                int popCounter = this.popMap.get(entity.getName());
                int newPopCounter = popCounter + 1;
                this.popMap.put(entity.getName(), newPopCounter);
                if (PopCounter.getInstance().isEnabled()) {
                    MessageUtil.sendClientMessage(entity.getName() + " has popped " + ChatFormatting.RED + newPopCounter + ChatFormatting.LIGHT_PURPLE + " times in total!", entity.getEntityId());
                }
            }
        }
    }

    public final Map<String, Integer> getPopMap() {
        return this.popMap;
    }

    public void load() {
        JordoHack.INSTANCE.getEventManager().registerListener(this);
    }

    public void unload() {
        JordoHack.INSTANCE.getEventManager().deregisterListener(this);
    }
}

