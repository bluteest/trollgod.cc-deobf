package me.hollow.realth.client.modules.movement;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.PacketEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleManifest(label="Velocity", category=Module.Category.MOVEMENT)
public class Velocity
        extends Module {
    @Override
    public void onEnable()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @Override
    public void onDisable()
    {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
    public Setting<Boolean> blockPush = this.register(new Setting<Boolean>("Block Push", true));
    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == mc.player.getEntityId() || event.getPacket() instanceof SPacketExplosion ||event.getPacket() instanceof EntityFishHook ) {
            event.cancel();
        }
        if (event.getPacket() instanceof EntityFishHook) {
            event.cancel();
        }
    }
    @SubscribeEvent
    public void onPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event) {
        if (blockPush.getValue()) event.setCanceled(true);
    }
}
