//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.api.*;
import java.util.*;
import net.minecraft.entity.player.*;
import me.hollow.realth.client.modules.misc.*;
import com.mojang.realmsclient.gui.*;
import me.hollow.realth.api.util.*;
import net.b0at.api.event.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import me.hollow.realth.*;
import me.hollow.realth.client.events.*;
import net.minecraft.entity.*;

public class PopManager implements Util
{
    private final Map<String, Integer> popMap;
    
    public PopManager() {
        this.popMap = new HashMap<String, Integer>();
    }
    
    @EventHandler
    public void onUpdate(final TickEvent event) {
        if (PopManager.mc.player == null || PopManager.mc.world == null) {
            return;
        }
        for (int i = 0; i < PopManager.mc.world.playerEntities.size(); ++i) {
            final EntityPlayer player = PopManager.mc.world.playerEntities.get(i);
            if (player.getHealth() <= 0.0f) {
                if (this.popMap.containsKey(player.getName())) {
                    final int l_Count = this.popMap.get(player.getName());
                    if (PopCounter.getInstance().isEnabled()) {
                        if (l_Count == 1) {
                            MessageUtil.sendClientMessage(player.getName() + " died after popping " + ChatFormatting.GREEN + this.popMap.get(player.getName()) + ChatFormatting.LIGHT_PURPLE + " totem!", player.getEntityId());
                        }
                        else {
                            MessageUtil.sendClientMessage(player.getName() + " died after popping " + ChatFormatting.GREEN + this.popMap.get(player.getName()) + ChatFormatting.LIGHT_PURPLE + " totems!", player.getEntityId());
                        }
                    }
                    this.popMap.remove(player.getName(), this.popMap.get(player.getName()));
                }
            }
        }
    }
    
    @EventHandler
    public void onPacket(final PacketEvent.Receive event) {
        if (PopManager.mc.player == null || PopManager.mc.world == null) {
            return;
        }
        final SPacketEntityStatus packet;
        if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getOpCode() == 35) {
            final Entity entity = packet.getEntity((World)PopManager.mc.world);
            JordoHack.INSTANCE.getEventManager().fireEvent((Object)new TotemPopEvent((EntityPlayer)entity));
            if (this.popMap.get(entity.getName()) == null) {
                this.popMap.put(entity.getName(), 1);
                final int popCounter = this.popMap.get(entity.getName());
                if (PopCounter.getInstance().isEnabled()) {
                    MessageUtil.sendClientMessage(entity.getName() + " has popped " + ChatFormatting.RED + popCounter + ChatFormatting.LIGHT_PURPLE + " time in total!", entity.getEntityId());
                }
            }
            else if (this.popMap.get(entity.getName()) != null) {
                final int popCounter = this.popMap.get(entity.getName());
                final int newPopCounter = popCounter + 1;
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
        JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
    }
    
    public void unload() {
        JordoHack.INSTANCE.getEventManager().deregisterListener((Object)this);
    }
}
