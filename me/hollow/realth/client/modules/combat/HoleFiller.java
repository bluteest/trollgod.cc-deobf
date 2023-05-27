//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.combat;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import me.hollow.realth.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import me.hollow.realth.api.util.*;
import net.minecraft.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

@ModuleManifest(label = "HoleFiller", category = Category.COMBAT)
public class HoleFiller extends Module
{
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> packet;
    private final Setting<Boolean> webs;
    private final Setting<Boolean> autoDisable;
    private final Setting<Double> range;
    private final Setting<Boolean> smart;
    private final Setting<Logic> logic;
    private final Setting<Integer> smartRange;
    private final Map<BlockPos, Long> renderBlocks;
    private EntityPlayer closestTarget;
    
    public HoleFiller() {
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (Object)false));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (Object)false, v -> (boolean)this.rotate.getValue()));
        this.webs = (Setting<Boolean>)this.register(new Setting("Webs", (Object)false));
        this.autoDisable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (Object)true));
        this.range = (Setting<Double>)this.register(new Setting("Radius", (Object)4.0, (Object)0.0, (Object)6));
        this.smart = (Setting<Boolean>)this.register(new Setting("Smart", (Object)false));
        this.logic = (Setting<Logic>)this.register(new Setting("Logic", (Object)Logic.PLAYER, v -> (boolean)this.smart.getValue()));
        this.smartRange = (Setting<Integer>)this.register(new Setting("EnemyRange", (Object)4, (Object)0, (Object)6, v -> (boolean)this.smart.getValue()));
        this.renderBlocks = new ConcurrentHashMap<BlockPos, Long>();
    }
    
    @Override
    public void onDisable() {
        this.closestTarget = null;
        JordoHack.INSTANCE.getRotationManager().resetRotationsPacket();
    }
    
    @Override
    public String getInfo() {
        if (this.smart.getValue()) {
            return JordoHack.INSTANCE.getFontManager().normalizeCases(this.logic.getValue());
        }
        return "Normal";
    }
    
    @Override
    public void onUpdate() {
        if (HoleFiller.mc.world == null) {
            return;
        }
        if (this.smart.getValue()) {
            this.findClosestTarget();
        }
        final List<BlockPos> blocks = this.getPlacePositions();
        BlockPos q = null;
        final int obbySlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        final int eChestSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
        final int webSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.WEB));
        if (obbySlot == -1 && eChestSlot == -1) {
            MessageUtil.sendClientMessage("<HoleFiller> No Obsidian or Ender Chests, Toggling!", -22221);
            this.toggle();
            return;
        }
        final int originalSlot = HoleFiller.mc.player.inventory.currentItem;
        for (final BlockPos blockPos : blocks) {
            if (!HoleFiller.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                continue;
            }
            if ((boolean)this.smart.getValue() && this.isInRange(blockPos)) {
                q = blockPos;
            }
            else if ((boolean)this.smart.getValue() && this.isInRange(blockPos) && this.logic.getValue() == Logic.HOLE && this.closestTarget.getDistanceSq(blockPos) <= (int)this.smartRange.getValue()) {
                q = blockPos;
            }
            else {
                q = blockPos;
            }
        }
        if (q != null && HoleFiller.mc.player.onGround) {
            HoleFiller.mc.player.inventory.currentItem = (this.webs.getValue() ? ((webSlot == -1) ? ((obbySlot == -1) ? eChestSlot : obbySlot) : webSlot) : ((obbySlot == -1) ? eChestSlot : obbySlot));
            HoleFiller.mc.playerController.updateController();
            this.renderBlocks.put(q, System.currentTimeMillis());
            BlockPlaceUtil.placeBlock(q, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue());
            if (HoleFiller.mc.player.inventory.currentItem != originalSlot) {
                HoleFiller.mc.player.inventory.currentItem = originalSlot;
                HoleFiller.mc.playerController.updateController();
            }
            HoleFiller.mc.player.swingArm(EnumHand.MAIN_HAND);
            HoleFiller.mc.player.inventory.currentItem = originalSlot;
        }
        if (q == null && (boolean)this.autoDisable.getValue() && !(boolean)this.smart.getValue()) {
            this.toggle();
        }
    }
    
    private boolean isHole(final BlockPos pos) {
        final BlockPos boost = pos.add(0, 1, 0);
        final BlockPos boost2 = pos.add(0, 0, 0);
        final BlockPos boost3 = pos.add(0, 0, -1);
        final BlockPos boost4 = pos.add(1, 0, 0);
        final BlockPos boost5 = pos.add(-1, 0, 0);
        final BlockPos boost6 = pos.add(0, 0, 1);
        final BlockPos boost7 = pos.add(0, 2, 0);
        final BlockPos boost8 = pos.add(0.5, 0.5, 0.5);
        final BlockPos boost9 = pos.add(0, -1, 0);
        return HoleFiller.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && HoleFiller.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && HoleFiller.mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && (HoleFiller.mc.world.getBlockState(boost3).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK) && (HoleFiller.mc.world.getBlockState(boost4).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK) && (HoleFiller.mc.world.getBlockState(boost5).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK) && (HoleFiller.mc.world.getBlockState(boost6).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK) && HoleFiller.mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && (HoleFiller.mc.world.getBlockState(boost9).getBlock() == Blocks.OBSIDIAN || HoleFiller.mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK);
    }
    
    private BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleFiller.mc.player.posX), Math.floor(HoleFiller.mc.player.posY), Math.floor(HoleFiller.mc.player.posZ));
    }
    
    private BlockPos getClosestTargetPos() {
        if (this.closestTarget != null) {
            return new BlockPos(Math.floor(this.closestTarget.posX), Math.floor(this.closestTarget.posY), Math.floor(this.closestTarget.posZ));
        }
        return null;
    }
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)HoleFiller.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target != HoleFiller.mc.player && !JordoHack.INSTANCE.getFriendManager().isFriend(target.getName()) && EntityUtil.isLiving((Entity)target)) {
                if (target.getHealth() <= 0.0f) {
                    continue;
                }
                if (this.closestTarget == null) {
                    this.closestTarget = target;
                }
                else {
                    if (HoleFiller.mc.player.getDistance((Entity)target) >= HoleFiller.mc.player.getDistance((Entity)this.closestTarget)) {
                        continue;
                    }
                    this.closestTarget = target;
                }
            }
        }
    }
    
    private boolean isInRange(final BlockPos blockPos) {
        final NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(this.getPlayerPos(), ((Double)this.range.getValue()).floatValue(), ((Double)this.range.getValue()).intValue()).stream().filter((Predicate<? super Object>)this::isHole).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return positions.contains((Object)blockPos);
    }
    
    private List<BlockPos> getPlacePositions() {
        final NonNullList positions = NonNullList.create();
        if ((boolean)this.smart.getValue() && this.closestTarget != null) {
            positions.addAll((Collection)this.getSphere(this.getClosestTargetPos(), (float)this.smartRange.getValue(), ((Double)this.range.getValue()).intValue()).stream().filter((Predicate<? super Object>)this::isHole).filter((Predicate<? super Object>)this::isInRange).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        }
        else if (!(boolean)this.smart.getValue()) {
            positions.addAll((Collection)this.getSphere(this.getPlayerPos(), ((Double)this.range.getValue()).floatValue(), ((Double)this.range.getValue()).intValue()).stream().filter((Predicate<? super Object>)this::isHole).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        }
        return (List<BlockPos>)positions;
    }
    
    private List<BlockPos> getSphere(final BlockPos loc, final float r, final int h) {
        final ArrayList<BlockPos> circleBlocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = cy - (int)r;
                while (true) {
                    final float f = (float)y;
                    final float f2 = cy + r;
                    if (f >= f2) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y);
                    if (dist < r * r) {
                        final BlockPos l = new BlockPos(x, y, z);
                        circleBlocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleBlocks;
    }
    
    private enum Logic
    {
        PLAYER, 
        HOLE;
    }
}
