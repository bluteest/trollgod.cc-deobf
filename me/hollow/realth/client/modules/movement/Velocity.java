//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.movement;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraftforge.common.*;
import me.hollow.realth.client.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.projectile.*;
import net.b0at.api.event.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@ModuleManifest(label = "Velocity", category = Module.Category.MOVEMENT)
public class Velocity extends Module
{
    public Setting<Boolean> blockPush;
    
    public Velocity() {
        this.blockPush = (Setting<Boolean>)this.register(new Setting("Block Push", (Object)true));
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @EventHandler
    public void onPacketReceive(final PacketEvent.Receive event) {
        if ((event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == Velocity.mc.player.getEntityId()) || event.getPacket() instanceof SPacketExplosion || event.getPacket() instanceof EntityFishHook) {
            event.cancel();
        }
        if (event.getPacket() instanceof EntityFishHook) {
            event.cancel();
        }
    }
    
    @SubscribeEvent
    public void onPushOutOfBlocks(final PlayerSPPushOutOfBlocksEvent event) {
        if (this.blockPush.getValue()) {
            event.setCanceled(true);
        }
    }
}
