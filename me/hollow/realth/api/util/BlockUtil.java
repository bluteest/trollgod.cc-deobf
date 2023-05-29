//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockDeadBush
 *  net.minecraft.block.BlockFire
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockSnow
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.other.CPacketAnimation
 *  net.minecraft.network.play.other.CPacketEntityAction
 *  net.minecraft.network.play.other.CPacketEntityAction$Action
 *  net.minecraft.network.play.other.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package me.hollow.realth.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import me.hollow.realth.api.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

public class BlockUtil
implements Util {

    public static final Minecraft mc = Minecraft.getMinecraft();
    private static boolean unshift = false;
    public static final List<Block> unSolidBlocks = Arrays.asList(new Block[]{Blocks.FLOWING_LAVA, Blocks.FLOWER_POT, Blocks.SNOW, Blocks.CARPET, Blocks.END_ROD, Blocks.SKULL, Blocks.FLOWER_POT, Blocks.TRIPWIRE, Blocks.TRIPWIRE_HOOK, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.STONE_BUTTON, Blocks.LADDER, Blocks.UNPOWERED_COMPARATOR, Blocks.POWERED_COMPARATOR, Blocks.UNPOWERED_REPEATER, Blocks.POWERED_REPEATER, Blocks.UNLIT_REDSTONE_TORCH, Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WIRE, Blocks.AIR, Blocks.PORTAL, Blocks.END_PORTAL, Blocks.WATER, Blocks.FLOWING_WATER, Blocks.LAVA, Blocks.FLOWING_LAVA, Blocks.SAPLING, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, Blocks.REEDS, Blocks.PUMPKIN_STEM, Blocks.MELON_STEM, Blocks.WATERLILY, Blocks.NETHER_WART, Blocks.COCOA, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, Blocks.TALLGRASS, Blocks.DEADBUSH, Blocks.VINE, Blocks.FIRE, Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.TORCH});
    public static final Vec3d[] antiDropOffsetList = new Vec3d[]{new Vec3d(0.0, -2.0, 0.0)};
    public static final Vec3d[] platformOffsetList = new Vec3d[]{new Vec3d(0.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0)};
    public static final Vec3d[] legOffsetList = new Vec3d[]{new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0)};
    public static final Vec3d[] OffsetList = new Vec3d[]{new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, 0.0)};
    public static final Vec3d[] antiStepOffsetList = new Vec3d[]{new Vec3d(-1.0, 2.0, 0.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, -1.0)};
    public static final Vec3d[] antiScaffoldOffsetList = new Vec3d[]{new Vec3d(0.0, 3.0, 0.0)};

    public static BlockPos[] toBlockPos(Vec3d[] vec3ds) {
        BlockPos[] list = new BlockPos[vec3ds.length];
        for (int i = 0; i < vec3ds.length; ++i) {
            list[i] = new BlockPos(vec3ds[i]);
        }
        return list;
    }
    public static boolean placeBlock(final BlockPos pos, boolean entityCheck) {
        final EnumFacing direction = calcSide(pos);
        if (direction == null || !entityCheck(pos)) return false;
        final boolean activated = mc.world.getBlockState(pos).getBlock().onBlockActivated(mc.world, pos, mc.world.getBlockState(pos), mc.player, EnumHand.MAIN_HAND, direction, 0, 0, 0);

        if (activated) mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));

        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos.offset(direction), direction.getOpposite(), EnumHand.MAIN_HAND, 0.5F, 0.5F, 0.5F));
        mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));

        if (activated) mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

        return true;
    }
    public static boolean entityCheck(BlockPos pos) {
        for (final Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos))) {
            if (entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityEnderCrystal)
                continue;
            return false;
        }

        return true;
    }
    public static EnumFacing calcSide(BlockPos pos) {
        for (EnumFacing side : EnumFacing.values()) {
            IBlockState offsetState = BlockUtil.mc.world.getBlockState(pos.offset(side));
            boolean activated = offsetState.getBlock().onBlockActivated((World)BlockUtil.mc.world, pos, offsetState, (EntityPlayer)BlockUtil.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (activated) {
                mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                unshift = true;
            }
            if (!offsetState.getBlock().canCollideCheck(offsetState, false) || offsetState.getMaterial().isReplaceable()) continue;
            return side;
        }
        return null;
    }
    public static boolean canPosBeCrystalled(BlockPos blockPos, boolean bl) {
        return !(!BlockUtil.getState(blockPos).equals(Blocks.OBSIDIAN) && !BlockUtil.getState(blockPos).equals(Blocks.BEDROCK) || !BlockUtil.getState(blockPos.up()).equals(Blocks.AIR) || !BlockUtil.getState(blockPos.up().up()).equals(Blocks.AIR) && !bl);
    }

    public static boolean canPlaceCrystal(BlockPos blockPos, boolean check) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        if (BlockUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && BlockUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        if (BlockUtil.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || BlockUtil.mc.world.getBlockState(boost).getBlock() != Blocks.AIR) {
            return false;
        }
        List entitiesWithinAABB = BlockUtil.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost));
        int size = entitiesWithinAABB.size();
        for (int i = 0; i < size; ++i) {
            Entity entity = (Entity)entitiesWithinAABB.get(i);
            if (entity.isDead || entity instanceof EntityEnderCrystal) continue;
            return false;
        }
        if (check) {
            List withinAABB = BlockUtil.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2));
            int size1 = withinAABB.size();
            for (int i = 0; i < size1; ++i) {
                Entity entity = (Entity)withinAABB.get(i);
                if (entity.isDead || entity instanceof EntityEnderCrystal) continue;
                return false;
            }
        }
        return true;
    }

    public static List<BlockPos> getSphere(float r, boolean ignoreAir) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = (int)BlockUtil.mc.player.posX;
        int cy = (int)BlockUtil.mc.player.posY;
        int cz = (int)BlockUtil.mc.player.posZ;
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = cy - (int)r;
                while ((float)y < (float)cy + r) {
                    if (r * r > (float)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y))) {
                        BlockPos l = new BlockPos(x, y, z);
                        if (!ignoreAir || BlockUtil.mc.world.getBlockState(l).getBlock() != Blocks.AIR) {
                            circleblocks.add(l);
                        }
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static boolean isBlockSolid(BlockPos pos) {
        return !BlockUtil.isBlockUnSolid(pos);
    }

    public static boolean isBlockUnSolid(BlockPos pos) {
        return BlockUtil.isBlockUnSolid(BlockUtil.mc.world.getBlockState(pos).getBlock());
    }

    public static boolean isBlockUnSolid(Block block) {
        return unSolidBlocks.contains((Object)block);
    }

    public static Vec3d[] getHelpingBlocks(Vec3d vec3d) {
        return new Vec3d[]{new Vec3d(vec3d.x, vec3d.y - 1.0, vec3d.z), new Vec3d(vec3d.x != 0.0 ? vec3d.x * 2.0 : vec3d.x, vec3d.y, vec3d.x != 0.0 ? vec3d.z : vec3d.z * 2.0), new Vec3d(vec3d.x == 0.0 ? vec3d.x + 1.0 : vec3d.x, vec3d.y, vec3d.x == 0.0 ? vec3d.z : vec3d.z + 1.0), new Vec3d(vec3d.x == 0.0 ? vec3d.x - 1.0 : vec3d.x, vec3d.y, vec3d.x == 0.0 ? vec3d.z : vec3d.z - 1.0), new Vec3d(vec3d.x, vec3d.y + 1.0, vec3d.z)};
    }

    public static Vec3d[] getUnsafeBlockArray(Entity entity, int height, boolean floor) {
        List<Vec3d> list = BlockUtil.getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
        Vec3d[] array = new Vec3d[list.size()];
        return list.toArray(array);
    }

    private static List<Vec3d> getUnsafeBlocksFromVec3d(Vec3d pos, int height, boolean floor) {
        ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        for (Vec3d vector : BlockUtil.getOffsets(height, floor)) {
            Block block = BlockUtil.mc.world.getBlockState(new BlockPos(pos).add(vector.x, vector.y, vector.z)).getBlock();
            if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) continue;
            vec3ds.add(vector);
        }
        return vec3ds;
    }

    public static int isPositionPlaceable(BlockPos pos, boolean entityCheck) {
        Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow)) {
            return 0;
        }
        if (entityCheck) {
            for (Entity entity : BlockUtil.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos))) {
                if (entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityEnderCrystal) continue;
                return 1;
            }
        }
        List<EnumFacing> possibleSides = BlockUtil.getPossibleSides(pos);
        for (int i = 0; i < possibleSides.size(); ++i) {
            if (!BlockUtil.canBeClicked(pos.offset(possibleSides.get(i)))) continue;
            return 3;
        }
        return 2;
    }

    private static boolean canBeClicked(BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos).getBlock().canCollideCheck(BlockUtil.mc.world.getBlockState(pos), false);
    }

    private static List<EnumFacing> getPossibleSides(BlockPos pos) {
        ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (EnumFacing side : EnumFacing.values()) {
            IBlockState blockState;
            BlockPos neighbour = pos.offset(side);
            if (!BlockUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtil.mc.world.getBlockState(neighbour), false) || (blockState = BlockUtil.mc.world.getBlockState(neighbour)).getMaterial().isReplaceable()) continue;
            facings.add(side);
        }
        return facings;
    }

    public static List<Vec3d> getOffsetList(int y, boolean floor) {
        ArrayList<Vec3d> offsets = new ArrayList<Vec3d>();
        offsets.add(new Vec3d(-1.0, (double)y, 0.0));
        offsets.add(new Vec3d(1.0, (double)y, 0.0));
        offsets.add(new Vec3d(0.0, (double)y, -1.0));
        offsets.add(new Vec3d(0.0, (double)y, 1.0));
        if (floor) {
            offsets.add(new Vec3d(0.0, (double)(y - 1), 0.0));
        }
        return offsets;
    }

    public static Vec3d[] getOffsets(int y, boolean floor) {
        List<Vec3d> offsets = BlockUtil.getOffsetList(y, floor);
        Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }

    public static Vec3d[] getTrapOffsets(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        List<Vec3d> offsets = BlockUtil.getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
        Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }

    public static boolean areVec3dsAligned(Vec3d vec3d1, Vec3d vec3d2) {
        return BlockUtil.areVec3dsAlignedRetarded(vec3d1, vec3d2);
    }

    public static boolean areVec3dsAlignedRetarded(Vec3d vec3d1, Vec3d vec3d2) {
        BlockPos pos1 = new BlockPos(vec3d1);
        BlockPos pos2 = new BlockPos(vec3d2.x, vec3d1.y, vec3d2.z);
        return pos1.equals((Object)pos2);
    }


    public static boolean isTrapped(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        return BlockUtil.getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() == 0;
    }

    public static boolean isTrappedExtended(int extension, EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
        return BlockUtil.getUntrappedBlocksExtended(extension, player, antiScaffold, antiStep, legs, platform, antiDrop, raytrace).size() == 0;
    }

    public static List<Vec3d> getUntrappedBlocksExtended(int extension, EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
        ArrayList<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (extension == 1) {
            placeTargets.addAll(BlockUtil.targets(player.getPositionVector(), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
        } else {
            int extend = 1;
            List<Vec3d> blockBlocks = BlockUtil.getBlockBlocks((Entity)player);
            for (int i = 0; i < blockBlocks.size() && extend <= extension; ++extend, ++i) {
                placeTargets.addAll(BlockUtil.targets(blockBlocks.get(i), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
            }
        }
        ArrayList<Vec3d> removeList = new ArrayList<Vec3d>();
        for (int i = 0; i < placeTargets.size(); ++i) {
            Vec3d vec3d = (Vec3d)placeTargets.get(i);
            if (BlockUtil.isPositionPlaceable(new BlockPos(vec3d), raytrace) != -1) continue;
            removeList.add(vec3d);
        }
        for (Vec3d vec3d : removeList) {
            placeTargets.remove((Object)vec3d);
        }
        return placeTargets;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    public static List<Vec3d> getBlockBlocks(Entity entity) {
        ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        AxisAlignedBB bb = entity.getEntityBoundingBox();
        double y = entity.posY;
        double minX = BlockUtil.round(bb.minX, 0);
        double minZ = BlockUtil.round(bb.minZ, 0);
        double maxX = BlockUtil.round(bb.maxX, 0);
        double maxZ = BlockUtil.round(bb.maxZ, 0);
        if (minX != maxX) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(maxX, y, minZ));
            if (minZ != maxZ) {
                vec3ds.add(new Vec3d(minX, y, maxZ));
                vec3ds.add(new Vec3d(maxX, y, maxZ));
                return vec3ds;
            }
        } else if (minZ != maxZ) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(minX, y, maxZ));
            return vec3ds;
        }
        vec3ds.add(entity.getPositionVector());
        return vec3ds;
    }
    public static List<BlockPos> getBlocksInRadius(final double range, final boolean movePredict, final int predictTicks) {
        final List<BlockPos> posses = new ArrayList<BlockPos>();
        float xRange = (float)Math.round(range);
        float yRange = (float)Math.round(range);
        float zRange = (float)Math.round(range);
        if (movePredict) {
            xRange += (float)(BlockUtil.mc.player.motionX * predictTicks);
            yRange += (float)(BlockUtil.mc.player.motionY * predictTicks);
            zRange += (float)(BlockUtil.mc.player.motionZ * predictTicks);
        }
        for (float x = -xRange; x <= xRange; ++x) {
            for (float y = -yRange; y <= yRange; ++y) {
                for (float z = -zRange; z <= zRange; ++z) {
                    final BlockPos position = BlockUtil.mc.player.getPosition().add((double)x, (double)y, (double)z);
                    if (BlockUtil.mc.player.getDistance(position.getX() + 0.5, (double)(position.getY() + 1), position.getZ() + 0.5) <= range) {
                        posses.add(position);
                    }
                }
            }
        }
        return posses;
    }

    public static List<Vec3d> targets(Vec3d vec3d, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
        ArrayList<Vec3d> placeTargets;
        block11: {
            block10: {
                placeTargets = new ArrayList<Vec3d>();
                if (antiDrop) {
                    Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiDropOffsetList));
                }
                if (platform) {
                    Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, platformOffsetList));
                }
                if (legs) {
                    Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, legOffsetList));
                }
                Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, OffsetList));
                if (!antiStep) break block10;
                Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiStepOffsetList));
                break block11;
            }
            List<Vec3d> vec3ds = BlockUtil.getUnsafeBlocksFromVec3d(vec3d, 2, false);
            if (vec3ds.size() != 4) break block11;
            block5: for (int i = 0; i < vec3ds.size(); ++i) {
                Vec3d vector = vec3ds.get(i);
                BlockPos position = new BlockPos(vec3d).add(vector.x, vector.y, vector.z);
                switch (BlockUtil.isPositionPlaceable(position, raytrace)) {
                    case 0: {
                        break block11;
                    }
                    case -1: 
                    case 1: 
                    case 2: {
                        continue block5;
                    }
                    case 3: {
                        placeTargets.add(vec3d.add(vector));
                    }
                }
            }
        }
        if (antiScaffold) {
            Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList));
        }
        return placeTargets;
    }

    public static Vec3d[] convertVec3ds(Vec3d vec3d, Vec3d[] input) {
        Vec3d[] output = new Vec3d[input.length];
        for (int i = 0; i < input.length; ++i) {
            output[i] = vec3d.add(input[i]);
        }
        return output;
    }
    public static Block getState(BlockPos blockPos) {
        return BlockUtil.mc.world.getBlockState(blockPos).getBlock();
    }

    public static List<Vec3d> getTrapOffsetsList(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        ArrayList<Vec3d> offsets = new ArrayList<Vec3d>(BlockUtil.getOffsetList(1, false));
        offsets.add(new Vec3d(0.0, 2.0, 0.0));
        if (antiScaffold) {
            offsets.add(new Vec3d(0.0, 3.0, 0.0));
        }
        if (antiStep) {
            offsets.addAll(BlockUtil.getOffsetList(2, false));
        }
        if (legs) {
            offsets.addAll(BlockUtil.getOffsetList(0, false));
        }
        if (platform) {
            offsets.addAll(BlockUtil.getOffsetList(-1, false));
            offsets.add(new Vec3d(0.0, -1.0, 0.0));
        }
        if (antiDrop) {
            offsets.add(new Vec3d(0.0, -2.0, 0.0));
        }
        return offsets;
    }

    public static List<Vec3d> getUntrappedBlocks(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        if (!antiStep && BlockUtil.getUnsafeBlocksFromVec3d(player.getPositionVector(), 2, false).size() == 4) {
            vec3ds.addAll(BlockUtil.getUnsafeBlocksFromVec3d(player.getPositionVector(), 2, false));
        }
        for (int i = 0; i < BlockUtil.getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop).length; ++i) {
            Vec3d vector = BlockUtil.getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)[i];
            BlockPos targetPos = new BlockPos(player.getPositionVector()).add(vector.x, vector.y, vector.z);
            Block block = BlockUtil.mc.world.getBlockState(targetPos).getBlock();
            if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) continue;
            vec3ds.add(vector);
        }
        return vec3ds;
    }
    public static boolean canPosBeCrystalledSoon(BlockPos blockPos, boolean bl) {
        return !(!BlockUtil.getState(blockPos).equals(Blocks.OBSIDIAN) && !BlockUtil.getState(blockPos).equals(Blocks.BEDROCK) || !BlockUtil.getState(blockPos.up().up()).equals(Blocks.AIR) && !bl);
    }
    public static boolean isSafe(Entity entity, int height, boolean floor) {
        return getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor).size() == 0;
    }
}

