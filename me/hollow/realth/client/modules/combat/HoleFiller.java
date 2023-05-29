package me.hollow.realth.client.modules.combat;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.*;
import me.hollow.realth.api.util.BlockPlaceUtil;
import me.hollow.realth.client.events.PacketEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.api.property.Setting;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static net.minecraft.util.EnumHand.MAIN_HAND;

@ModuleManifest(label="HoleFiller", category=Module.Category.COMBAT)

public class HoleFiller extends Module {

    private final Setting<Boolean> rotate = this.register(new Setting<>("Rotate", false));
    private final Setting<Boolean> packet = this.register(new Setting<>("Packet", false, v -> rotate.getValue()));
    private final Setting<Boolean> webs = this.register(new Setting<>("Webs", false));
    private final Setting<Boolean> autoDisable = this.register(new Setting<>("AutoDisable", true));
    private final Setting<Double> range = this.register(new Setting<>("Radius", 4.0, 0.0, 6));
    private final Setting<Boolean> smart = this.register(new Setting<>("Smart", false));
    private final Setting<Logic> logic = this.register(new Setting<>("Logic", Logic.PLAYER, v -> smart.getValue()));
    private final Setting<Integer> smartRange = this.register(new Setting<>("EnemyRange", 4, 0, 6, v -> smart.getValue()));
    private final Map<BlockPos, Long> renderBlocks = new ConcurrentHashMap<>();


    private EntityPlayer closestTarget;
    private enum Logic {
        PLAYER,
        HOLE
    }

    @Override
    public void onDisable() {
        closestTarget = null;
        JordoHack.INSTANCE.getRotationManager().resetRotationsPacket();
    }

    @Override
    public String getInfo() {
        if (smart.getValue()) {
            return JordoHack.INSTANCE.getFontManager().normalizeCases(logic.getValue());

        } else {
            return "Normal";
        }
    }

    @Override
    public void onUpdate() {
        if (mc.world == null) {
            return;
        }
        if (smart.getValue()) {
            findClosestTarget();
        }
        List<BlockPos> blocks = getPlacePositions();
        BlockPos q = null;

        int obbySlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.OBSIDIAN));
        int eChestSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.ENDER_CHEST));
        int webSlot = ItemUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.WEB));

        if (obbySlot == -1 && eChestSlot == -1) {
            MessageUtil.sendClientMessage("<HoleFiller> No Obsidian or Ender Chests, Toggling!", -22221);
            this.toggle();
            return;

        }

        int originalSlot = mc.player.inventory.currentItem;

        for (BlockPos blockPos : blocks) {
            if (!mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) continue;
            if (smart.getValue() && isInRange(blockPos)) {
                q = blockPos;
                continue;
            } else if (smart.getValue() && isInRange(blockPos) && logic.getValue() == Logic.HOLE && closestTarget.getDistanceSq(blockPos) <= smartRange.getValue()) {
                q = blockPos;
                continue;
            }
            q = blockPos;
        }

        if (q != null && mc.player.onGround) {

            mc.player.inventory.currentItem = webs.getValue() ? (webSlot == -1 ? (obbySlot == -1 ? eChestSlot : obbySlot) : webSlot) : (obbySlot == -1 ? eChestSlot : obbySlot);

            mc.playerController.updateController();
            renderBlocks.put(q, System.currentTimeMillis());

            BlockPlaceUtil.placeBlock(q, rotate.getValue(), packet.getValue());

            if (mc.player.inventory.currentItem != originalSlot) {
                mc.player.inventory.currentItem = originalSlot;
                mc.playerController.updateController();
            }
            mc.player.swingArm(MAIN_HAND);
            mc.player.inventory.currentItem = originalSlot;
        }
        if (q == null && autoDisable.getValue() && !smart.getValue()) {
            toggle();
        }
    }

    private boolean isHole(BlockPos pos) {
        BlockPos boost = pos.add(0, 1, 0);
        BlockPos boost2 = pos.add(0, 0, 0);
        BlockPos boost3 = pos.add(0, 0, -1);
        BlockPos boost4 = pos.add(1, 0, 0);
        BlockPos boost5 = pos.add(-1, 0, 0);
        BlockPos boost6 = pos.add(0, 0, 1);
        BlockPos boost7 = pos.add(0, 2, 0);
        BlockPos boost8 = pos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = pos.add(0, -1, 0);
        return !(mc.world.getBlockState(boost).getBlock() != Blocks.AIR || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || mc.world.getBlockState(boost7).getBlock() != Blocks.AIR || mc.world.getBlockState(boost3).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost3).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost4).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost4).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost5).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost5).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost6).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost6).getBlock() != Blocks.BEDROCK || mc.world.getBlockState(boost8).getBlock() != Blocks.AIR || mc.world.getBlockState(boost9).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(boost9).getBlock() != Blocks.BEDROCK);
    }

    private BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private BlockPos getClosestTargetPos() {
        if (closestTarget != null) {
            return new BlockPos(Math.floor(closestTarget.posX), Math.floor(closestTarget.posY), Math.floor(closestTarget.posZ));
        }
        return null;
    }

    private void findClosestTarget() {
        List<EntityPlayer> playerList = mc.world.playerEntities;

        closestTarget = null;

        for (EntityPlayer target : playerList) {
            if (target == mc.player || JordoHack.INSTANCE.getFriendManager().isFriend(target.getName()) || !EntityUtil.isLiving(target) || target.getHealth() <= 0.0f) continue;
            if (closestTarget == null) {
                closestTarget = target;
                continue;
            }

            if (!(mc.player.getDistance(target) < mc.player.getDistance(closestTarget))) continue;
            closestTarget = target;
        }
    }

    private boolean isInRange(BlockPos blockPos) {
        NonNullList positions = NonNullList.create();

        positions.addAll(getSphere(getPlayerPos(), range.getValue().floatValue(), range.getValue().intValue()).stream().filter(this::isHole).collect(Collectors.toList()));
        return positions.contains(blockPos);
    }

    private List<BlockPos> getPlacePositions() {
        NonNullList positions = NonNullList.create();

        if (smart.getValue() && closestTarget != null) {
            positions.addAll(getSphere(getClosestTargetPos(), smartRange.getValue().floatValue(), range.getValue().intValue()).stream().filter(this::isHole).filter(this::isInRange).collect(Collectors.toList()));
        } else if (!smart.getValue()) {
            positions.addAll(getSphere(getPlayerPos(), range.getValue().floatValue(), range.getValue().intValue()).stream().filter(this::isHole).collect(Collectors.toList()));
        }
        return positions;
    }

    private List<BlockPos> getSphere(BlockPos loc, float r, int h) {

        ArrayList<BlockPos> circleBlocks = new ArrayList<>();

        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;

        while ((float)x <= (float)cx + r) {

            int z = cz - (int)r;

            while ((float)z <= (float)cz + r) {

                int y = cy - (int)r;

                while (true) {

                    float f = y;
                    float f2 = (float)cy + r;

                    if (!(f < f2)) break;

                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

                    if (dist < (double) (r * r)) {
                        BlockPos l = new BlockPos(x, y, z);
                        circleBlocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleBlocks;
    }
}