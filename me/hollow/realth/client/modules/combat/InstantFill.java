//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.combat;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.b0at.api.event.*;
import me.hollow.realth.api.util.*;
import java.util.function.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;

@ModuleManifest(label = "InstantFill", category = Category.COMBAT)
public class InstantFill extends Module
{
    private final Setting<Integer> delay;
    private final Setting<Integer> bpt;
    private final Setting<Float> range;
    private final Setting<Float> distance;
    private static final BlockPos[] surroundOffset;
    private final Timer timer;
    private int placeAmount;
    private int blockSlot;
    
    public InstantFill() {
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (Object)50, (Object)0, (Object)300));
        this.bpt = (Setting<Integer>)this.register(new Setting("Blocks/Tick", (Object)10, (Object)1, (Object)20));
        this.range = (Setting<Float>)this.register(new Setting("Range", (Object)5.0f, (Object)1.0f, (Object)6.0f));
        this.distance = (Setting<Float>)this.register(new Setting("Distance", (Object)4.0f, (Object)1.0f, (Object)7.0f));
        this.timer = new Timer();
        this.placeAmount = 0;
        this.blockSlot = -1;
    }
    
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        if (this.check()) {
            final EntityPlayer currentTarget = CombatUtil.getTarget(10.0f);
            if (currentTarget == null) {
                return;
            }
            if (CombatUtil.isInHole(currentTarget)) {
                return;
            }
            final List<BlockPos> holes = this.getHoles(currentTarget);
            if (holes.size() == 0) {
                this.toggle();
                return;
            }
            final int lastSlot = InstantFill.mc.player.inventory.currentItem;
            this.blockSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
            if (this.blockSlot == -1) {
                this.toggle();
                return;
            }
            BlockPos hole = null;
            for (final BlockPos pos : this.getHoles(currentTarget)) {
                if (currentTarget.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()) >= (float)this.distance.getValue()) {
                    continue;
                }
                hole = pos;
            }
            if (hole != null) {
                InstantFill.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(this.blockSlot));
                this.placeBlock(hole);
                InstantFill.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(lastSlot));
            }
        }
    }
    
    private void placeBlock(final BlockPos pos) {
        if ((int)this.bpt.getValue() > this.placeAmount) {
            BlockUtil.placeBlock(pos, false);
            ++this.placeAmount;
        }
    }
    
    private boolean check() {
        if (InstantFill.mc.player == null || InstantFill.mc.world == null) {
            return false;
        }
        this.placeAmount = 0;
        this.blockSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (this.blockSlot == -1) {
            MessageUtil.sendClientMessage("<HoleFiller> No Obsidian, Toggling!", -22221);
            this.toggle();
        }
        return this.timer.hasReached((long)(int)this.delay.getValue());
    }
    
    public final List<BlockPos> getHoles(final EntityPlayer player) {
        final List<BlockPos> holes = this.calcHoles();
        holes.sort(Comparator.comparingDouble((ToDoubleFunction<? super BlockPos>)player::getDistanceSq));
        return holes;
    }
    
    public List<BlockPos> calcHoles() {
        final ArrayList<BlockPos> safeSpots = new ArrayList<BlockPos>();
        final List<BlockPos> positions = (List<BlockPos>)BlockUtil.getSphere((float)this.range.getValue(), false);
        for (int i = 0; i < positions.size(); ++i) {
            final BlockPos pos = positions.get(i);
            if (BlockUtil.isPositionPlaceable(pos, true) != 1 && InstantFill.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && InstantFill.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                if (InstantFill.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    boolean isSafe = true;
                    for (final BlockPos offset : InstantFill.surroundOffset) {
                        final Block block = InstantFill.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                        if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST) {
                            if (block != Blocks.ANVIL) {
                                isSafe = false;
                            }
                        }
                    }
                    if (isSafe) {
                        safeSpots.add(pos);
                    }
                }
            }
        }
        return safeSpots;
    }
    
    static {
        surroundOffset = BlockUtil.toBlockPos(BlockUtil.getOffsets(0, true));
    }
}
