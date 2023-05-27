//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import net.minecraftforge.common.*;
import java.util.*;
import net.b0at.api.event.*;
import net.minecraftforge.event.entity.player.*;
import me.hollow.realth.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.item.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.api.util.*;

@ModuleManifest(label = "Announcer", category = Category.MISC, listen = true)
public class Announcer extends Module
{
    public Setting<Boolean> movement;
    public final Setting<Boolean> breakBlock;
    public final Setting<Boolean> eat;
    public final Setting<Boolean> custom;
    public final Setting<String> message;
    public final Setting<Double> delay;
    public final Setting<Boolean> auto;
    public final Setting<String> message2;
    private double lastPositionX;
    private double lastPositionY;
    private double lastPositionZ;
    public EntityPlayer target;
    public int targetResetTimer;
    public Map<EntityPlayer, Integer> targets;
    private final Timer entityTimer;
    private int eaten;
    private int broken;
    private final Timer timer;
    
    public Announcer() {
        this.movement = (Setting<Boolean>)this.register(new Setting("Movement", (Object)true));
        this.breakBlock = (Setting<Boolean>)this.register(new Setting("Break", (Object)true));
        this.eat = (Setting<Boolean>)this.register(new Setting("Eating", (Object)true));
        this.custom = (Setting<Boolean>)this.register(new Setting("Custom Name", (Object)false));
        this.message = (Setting<String>)this.register(new Setting("Name", (Object)"DotGod.CC", v -> (boolean)this.custom.getValue()));
        this.delay = (Setting<Double>)this.register(new Setting("Delay", (Object)10.0, (Object)1.0, (Object)30.0));
        this.auto = (Setting<Boolean>)this.register(new Setting("Auto EZ", (Object)false));
        this.message2 = (Setting<String>)this.register(new Setting("Auto EZ says..", (Object)"GG ", v -> (boolean)this.auto.getValue()));
        this.targetResetTimer = 20;
        this.targets = new ConcurrentHashMap<EntityPlayer, Integer>();
        this.entityTimer = new Timer();
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @Override
    public void onToggle() {
        this.eaten = 0;
        this.broken = 0;
        this.timer.reset2();
        this.target = null;
    }
    
    @EventHandler
    @Override
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        this.targets.replaceAll((p, v) -> Integer.valueOf((int)(this.entityTimer.getPassedTimeMs() / 1000L)));
        for (final EntityPlayer player : this.targets.keySet()) {
            if (this.targets.get(player) <= this.targetResetTimer) {
                continue;
            }
            this.targets.remove(player);
            this.entityTimer.reset();
        }
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        final double traveledX = this.lastPositionX - Announcer.mc.player.lastTickPosX;
        final double traveledY = this.lastPositionY - Announcer.mc.player.lastTickPosY;
        final double traveledZ = this.lastPositionZ - Announcer.mc.player.lastTickPosZ;
        final double traveledDistance = Math.sqrt(traveledX * traveledX + traveledY * traveledY + traveledZ * traveledZ);
        if ((boolean)this.movement.getValue() && traveledDistance >= 1.0 && traveledDistance <= 1000.0 && this.timer.passedS((double)this.delay.getValue())) {
            Announcer.mc.player.sendChatMessage("I just flew " + traveledDistance + " like a butterfly thanks to TrollGod.CC!");
            this.lastPositionX = Announcer.mc.player.lastTickPosX;
            this.lastPositionY = Announcer.mc.player.lastTickPosY;
            this.lastPositionZ = Announcer.mc.player.lastTickPosZ;
            this.timer.reset2();
        }
    }
    
    @EventHandler
    public void onEntityDeath(final DeathEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if ((boolean)this.auto.getValue() && this.targets.containsKey(event.player)) {
            this.announceDeath(event.player);
            this.timer.reset2();
            this.targets.remove(event.player);
        }
    }
    
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityPlayer && !JordoHack.INSTANCE.getFriendManager().isFriend(event.getEntityPlayer())) {
            this.targets.put((EntityPlayer)event.getTarget(), 0);
        }
    }
    
    @EventHandler
    public void onSendAttackPacket(final PacketEvent.Send event) {
        final CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)Announcer.mc.world) instanceof EntityPlayer && !JordoHack.INSTANCE.getFriendManager().isFriend((EntityPlayer)packet.getEntityFromWorld((World)Announcer.mc.world))) {
            this.targets.put((EntityPlayer)packet.getEntityFromWorld((World)Announcer.mc.world), 0);
        }
    }
    
    @SubscribeEvent
    public void onUseItem(final LivingEntityUseItemEvent.Finish event) {
        if (fullNullCheck()) {
            return;
        }
        final int random = MathUtil.randomBetween(1, 6);
        if (((boolean)this.eat.getValue() && event.getEntity() == Announcer.mc.player && event.getItem().getItem() instanceof ItemFood) || event.getItem().getItem() instanceof ItemAppleGold) {
            ++this.eaten;
            if (this.timer.passedS((double)this.delay.getValue()) && this.eaten >= random) {
                if (this.custom.getValue()) {
                    Announcer.mc.player.sendChatMessage("I just ate " + this.eaten + " " + event.getItem().getDisplayName() + " thanks to " + (String)this.message.getValue() + "!");
                }
                else {
                    Announcer.mc.player.sendChatMessage("I just ate " + this.eaten + " " + event.getItem().getDisplayName() + " thanks to TrollGod.CC!");
                }
                this.eaten = 0;
                this.timer.reset2();
            }
        }
    }
    
    @EventHandler
    public void onBreakBlock(final DamageBlockEvent event) {
        if (fullNullCheck()) {
            return;
        }
        ++this.broken;
        if ((boolean)this.breakBlock.getValue() && this.timer.passedS((double)this.delay.getValue()) && !BlockUtil.getState(event.getPosition()).getLocalizedName().equalsIgnoreCase("BEDROCK") && !BlockUtil.getState(event.getPosition()).getLocalizedName().equalsIgnoreCase("AIR") && (BlockUtil.getState(event.getPosition()).getLocalizedName().equalsIgnoreCase("OBSIDIAN") || BlockUtil.getState(event.getPosition()).getLocalizedName().equalsIgnoreCase("ENDER_CHEST"))) {
            if (this.custom.getValue()) {
                Announcer.mc.player.sendChatMessage("I just broke " + this.broken + " " + BlockUtil.getState(event.getPosition()).getLocalizedName() + " thanks to " + (String)this.message.getValue() + "!");
            }
            else {
                Announcer.mc.player.sendChatMessage("I just broke " + this.broken + " " + BlockUtil.getState(event.getPosition()).getLocalizedName() + " thanks to TrollGod.CC!");
            }
            this.broken = 0;
            this.timer.reset2();
        }
    }
    
    public void announceDeath(final EntityPlayer target) {
        Announcer.mc.player.sendChatMessage(" " + (String)this.message2.getValue() + target.getDisplayNameString() + " !");
    }
}
