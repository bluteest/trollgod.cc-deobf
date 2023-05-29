package me.hollow.realth.client.modules.misc;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.api.property.Setting;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//author lyric
@ModuleManifest(label="Announcer", category=Module.Category.MISC, listen = true)
public class Announcer extends Module {
    public Setting<Boolean> movement = this.register(new Setting<Boolean>("Movement", true));
    public final Setting<Boolean> breakBlock = this.register(new Setting<Boolean>("Break", true));
    public final Setting<Boolean> eat = this.register(new Setting<Boolean>("Eating", true));
    public final Setting<Boolean> custom = this.register(new Setting<Boolean>("Custom Name", false));
    public final Setting<String> message = this.register(new Setting<Object>("Name" , "DotGod.CC" , v -> custom.getValue()));

    public final Setting<Double> delay = this.register(new Setting<Double>("Delay", 10d, 1d, 30d));
    public final Setting<Boolean> auto = this.register(new Setting<Boolean>("Auto EZ", false));
    public final Setting<String> message2 = this.register(new Setting<Object>("Auto EZ says.." , "GG " , v -> auto.getValue()));
    private double lastPositionX;
    private double lastPositionY;
    private double lastPositionZ;
    public EntityPlayer target;

    public int targetResetTimer = 20;
    public Map<EntityPlayer, Integer> targets = new ConcurrentHashMap<EntityPlayer, Integer>();

    private final Timer entityTimer = new Timer();

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

    private int eaten;

    private int broken;

    private final Timer timer = new Timer();

    @Override
    public void onToggle() {
        eaten = 0;
        broken = 0;
        timer.reset2();
        target = null;
    }
    @EventHandler
    public void onTick()
    {
        if(fullNullCheck())
        {
            return;
        }

        this.targets.replaceAll((p, v) -> (int) (this.entityTimer.getPassedTimeMs() / 1000L));
        for (EntityPlayer player : this.targets.keySet()) {
            if (this.targets.get(player) <= this.targetResetTimer) continue;
            this.targets.remove(player);
            this.entityTimer.reset();
        }
    }

    @EventHandler
    public void onUpdate(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
        {
            return;
        }


        double traveledX = lastPositionX - mc.player.lastTickPosX;
        double traveledY = lastPositionY - mc.player.lastTickPosY;
        double traveledZ = lastPositionZ - mc.player.lastTickPosZ;

        double traveledDistance = Math.sqrt(traveledX * traveledX + traveledY * traveledY + traveledZ * traveledZ);
        int dist = ((int) traveledDistance);

        if (movement.getValue() && traveledDistance >= 2 && traveledDistance <= 1000 && timer.passedS(delay.getValue())) {

            if(custom.getValue())
            {
                mc.player.sendChatMessage("I just flew " + dist + " blocks like a butterfly thanks to " + message.getValue() + "!");
            }
            else {
                mc.player.sendChatMessage("I just flew " + dist + " blocks like a butterfly thanks to TrollGod.CC!");
            }

            lastPositionX = mc.player.lastTickPosX;
            lastPositionY = mc.player.lastTickPosY;
            lastPositionZ = mc.player.lastTickPosZ;

            timer.reset2();
        }
    }


    @EventHandler
    public void onEntityDeath(DeathEvent event) {
        if(fullNullCheck())
        {
            return;
        }
        if (auto.getValue() && this.targets.containsKey(event.player)) {
            this.announceDeath(event.player);
            timer.reset2();
            this.targets.remove(event.player);
        }
    }
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityPlayer && !JordoHack.INSTANCE.getFriendManager().isFriend(event.getEntityPlayer())) {
            this.targets.put((EntityPlayer) event.getTarget(), 0);
        }
    }

    @EventHandler
    public void onSendAttackPacket(PacketEvent.Send event) {
        CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.world) instanceof EntityPlayer && !JordoHack.INSTANCE.getFriendManager().isFriend((EntityPlayer) packet.getEntityFromWorld(mc.world))) {
            this.targets.put((EntityPlayer) packet.getEntityFromWorld(mc.world), 0);
        }
    }


    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent.Finish event) {
        if (fullNullCheck())
        {
            return;
        }
        int random = MathUtil.randomBetween(1, 6);
        if (eat.getValue()
                && event.getEntity() == mc.player
                && event.getItem().getItem() instanceof ItemFood
                || event.getItem().getItem() instanceof ItemAppleGold) {

            ++eaten;

            if (timer.passedS(delay.getValue()) && eaten >= random) {
                if (custom.getValue())
                {
                    mc.player.sendChatMessage("I just ate " + eaten + " "+ event.getItem().getDisplayName() + " thanks to " + message.getValue() + "!");

                }
                else {
                    mc.player.sendChatMessage("I just ate "+ eaten + " " + event.getItem().getDisplayName() + " thanks to TrollGod.CC!");

                }
                eaten = 0;
                timer.reset2();
            }
        }
    }

    @EventHandler
    public void onBreakBlock(ClickBlockEvent event) {
        if (fullNullCheck())
        {
            return;
        }
        ++broken;

        if (breakBlock.getValue() && timer.passedS(delay.getValue()) && !(BlockUtil.getState(event.getPos()).getLocalizedName().equalsIgnoreCase("BEDROCK")) && !(BlockUtil.getState(event.getPos()).getLocalizedName().equalsIgnoreCase("AIR")) && (BlockUtil.getState(event.getPos()).getLocalizedName().equalsIgnoreCase("OBSIDIAN") || (BlockUtil.getState(event.getPos()).getLocalizedName().equalsIgnoreCase("ENDER_CHEST")))) {
            if (custom.getValue())
            {
                mc.player.sendChatMessage("I just broke "+ broken +" "+ BlockUtil.getState(event.getPos()).getLocalizedName() + " thanks to " + message.getValue() +"!");

            }
            else
            {
                mc.player.sendChatMessage("I just broke " + broken +" "+ BlockUtil.getState(event.getPos()).getLocalizedName() + " thanks to TrollGod.CC!");

            }
            broken = 0;

            timer.reset2();
        }
    }
    public void announceDeath(EntityPlayer target) {
        mc.player.sendChatMessage(" " + message2.getValue() + target.getDisplayNameString() + " !");
    }

}
