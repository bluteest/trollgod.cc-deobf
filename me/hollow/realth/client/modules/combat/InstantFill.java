//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.other.CPacketHeldItemChange
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package me.hollow.realth.client.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.*;
import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

@ModuleManifest(label="InstantFill", category=Module.Category.COMBAT)
public class InstantFill
extends Module {
    private final Setting<Integer> delay = this.register(new Setting<Integer>("Delay", 50, 0, 300));
    private final Setting<Integer> bpt = this.register(new Setting<Integer>("Blocks/Tick", 10, 1, 20));
    private final Setting<Float> range = this.register(new Setting<Float>("Range", Float.valueOf(5.0f), Float.valueOf(1.0f), Float.valueOf(6.0f)));
    private final Setting<Float> distance = this.register(new Setting<Float>("Distance", Float.valueOf(4.0f), Float.valueOf(1.0f), Float.valueOf(7.0f)));
    private static final BlockPos[] surroundOffset = BlockUtil.toBlockPos(BlockUtil.getOffsets(0, true));
    private final Timer timer = new Timer();
    private int placeAmount = 0;
    private int blockSlot = -1;

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (this.check()) {
            EntityPlayer currentTarget = CombatUtil.getTarget(10.0f);
            if (currentTarget == null) {
                return;
            }
            if (CombatUtil.isInHole(currentTarget)) {
                return;
            }
            List<BlockPos> holes = this.getHoles(currentTarget);
            if (holes.size() == 0) {
                this.toggle();
                return;
            }
            int lastSlot = mc.player.inventory.currentItem;
            this.blockSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.OBSIDIAN));
            if (this.blockSlot == -1) {
                this.toggle();
                return;
            }
            BlockPos hole = null;
            for (BlockPos pos : this.getHoles(currentTarget)) {
                if (!(currentTarget.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()) < (double)this.distance.getValue())) continue;
                hole = pos;
            }
            if (hole != null) {
                mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(this.blockSlot));
                this.placeBlock(hole);
                mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(lastSlot));
            }
        }
    }

    private void placeBlock(BlockPos pos) {
        if (this.bpt.getValue() > this.placeAmount) {

                BlockUtil.placeBlock(pos, false);
                ++this.placeAmount;

        }
    }

    private boolean check() {
        if (mc.player == null || mc.world == null) {
            return false;
        }
        this.placeAmount = 0;
        this.blockSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.OBSIDIAN));
        if (this.blockSlot == -1) {
            MessageUtil.sendClientMessage("<HoleFiller> No Obsidian, Toggling!", -22221);
            this.toggle();
        }
        return this.timer.hasReached(delay.getValue());
    }

    public final List<BlockPos> getHoles(EntityPlayer player) {
        List<BlockPos> holes = this.calcHoles();
        holes.sort(Comparator.comparingDouble(((EntityPlayer)player)::getDistanceSq));
        return holes;
    }

    public List<BlockPos> calcHoles() {
        ArrayList<BlockPos> safeSpots = new ArrayList<BlockPos>();
        List<BlockPos> positions = BlockUtil.getSphere(range.getValue(), false);
        for (int i = 0; i < positions.size(); ++i) {
            BlockPos pos = positions.get(i);
            if (BlockUtil.isPositionPlaceable(pos, true) == 1 || !mc.world.getBlockState(pos).getBlock().equals((Object)Blocks.AIR) || !mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals((Object)Blocks.AIR) || !mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals((Object)Blocks.AIR)) continue;
            boolean isSafe = true;
            for (BlockPos offset : surroundOffset) {
                Block block = mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST || block == Blocks.ANVIL) continue;
                isSafe = false;
            }
            if (!isSafe) continue;
            safeSpots.add(pos);
        }
        return safeSpots;
    }
}

