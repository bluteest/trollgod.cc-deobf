//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import me.hollow.realth.api.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.math.*;
import java.util.*;

public class BlockUtil implements Util
{
    public static final Minecraft mc;
    private static boolean unshift;
    public static final List<Block> unSolidBlocks;
    public static final Vec3d[] antiDropOffsetList;
    public static final Vec3d[] platformOffsetList;
    public static final Vec3d[] legOffsetList;
    public static final Vec3d[] OffsetList;
    public static final Vec3d[] antiStepOffsetList;
    public static final Vec3d[] antiScaffoldOffsetList;
    
    public static BlockPos[] toBlockPos(final Vec3d[] vec3ds) {
        final BlockPos[] list = new BlockPos[vec3ds.length];
        for (int i = 0; i < vec3ds.length; ++i) {
            list[i] = new BlockPos(vec3ds[i]);
        }
        return list;
    }
    
    public static boolean placeBlock(final BlockPos pos, final boolean entityCheck) {
        final EnumFacing direction = calcSide(pos);
        if (direction == null || !entityCheck(pos)) {
            return false;
        }
        final boolean activated = BlockUtil.mc.world.getBlockState(pos).getBlock().onBlockActivated((World)BlockUtil.mc.world, pos, BlockUtil.mc.world.getBlockState(pos), (EntityPlayer)BlockUtil.mc.player, EnumHand.MAIN_HAND, direction, 0.0f, 0.0f, 0.0f);
        if (activated) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos.offset(direction), direction.getOpposite(), EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (activated) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        return true;
    }
    
    public static boolean entityCheck(final BlockPos pos) {
        for (final Entity entity : BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public static EnumFacing calcSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final IBlockState offsetState = BlockUtil.mc.world.getBlockState(pos.offset(side));
            final boolean activated = offsetState.getBlock().onBlockActivated((World)BlockUtil.mc.world, pos, offsetState, (EntityPlayer)BlockUtil.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (activated) {
                BlockUtil.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                BlockUtil.unshift = true;
            }
            if (offsetState.getBlock().canCollideCheck(offsetState, false) && !offsetState.getMaterial().isReplaceable()) {
                return side;
            }
        }
        return null;
    }
    
    public static boolean canPosBeCrystalled(final BlockPos blockPos, final boolean bl) {
        return (getState(blockPos).equals(Blocks.OBSIDIAN) || getState(blockPos).equals(Blocks.BEDROCK)) && getState(blockPos.up()).equals(Blocks.AIR) && (getState(blockPos.up().up()).equals(Blocks.AIR) || bl);
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean check) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        if (BlockUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && BlockUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        if (BlockUtil.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || BlockUtil.mc.world.getBlockState(boost).getBlock() != Blocks.AIR) {
            return false;
        }
        final List entitiesWithinAABB = BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost));
        for (int size = entitiesWithinAABB.size(), i = 0; i < size; ++i) {
            final Entity entity = entitiesWithinAABB.get(i);
            if (!entity.isDead && !(entity instanceof EntityEnderCrystal)) {
                return false;
            }
        }
        if (check) {
            final List withinAABB = BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2));
            for (int size2 = withinAABB.size(), j = 0; j < size2; ++j) {
                final Entity entity2 = withinAABB.get(j);
                if (!entity2.isDead && !(entity2 instanceof EntityEnderCrystal)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static List<BlockPos> getSphere(final float r, final boolean ignoreAir) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = (int)BlockUtil.mc.player.posX;
        final int cy = (int)BlockUtil.mc.player.posY;
        final int cz = (int)BlockUtil.mc.player.posZ;
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = cy - (int)r; y < cy + r; ++y) {
                    if (r * r > (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y)) {
                        final BlockPos l = new BlockPos(x, y, z);
                        if (!ignoreAir || BlockUtil.mc.world.getBlockState(l).getBlock() != Blocks.AIR) {
                            circleblocks.add(l);
                        }
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static boolean isBlockSolid(final BlockPos pos) {
        return !isBlockUnSolid(pos);
    }
    
    public static boolean isBlockUnSolid(final BlockPos pos) {
        return isBlockUnSolid(BlockUtil.mc.world.getBlockState(pos).getBlock());
    }
    
    public static boolean isBlockUnSolid(final Block block) {
        return BlockUtil.unSolidBlocks.contains(block);
    }
    
    public static Vec3d[] getHelpingBlocks(final Vec3d vec3d) {
        return new Vec3d[] { new Vec3d(vec3d.x, vec3d.y - 1.0, vec3d.z), new Vec3d((vec3d.x != 0.0) ? (vec3d.x * 2.0) : vec3d.x, vec3d.y, (vec3d.x != 0.0) ? vec3d.z : (vec3d.z * 2.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x + 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z + 1.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x - 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z - 1.0)), new Vec3d(vec3d.x, vec3d.y + 1.0, vec3d.z) };
    }
    
    public static Vec3d[] getUnsafeBlockArray(final Entity entity, final int height, final boolean floor) {
        final List<Vec3d> list = getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
        final Vec3d[] array = new Vec3d[list.size()];
        return list.toArray(array);
    }
    
    private static List<Vec3d> getUnsafeBlocksFromVec3d(final Vec3d pos, final int height, final boolean floor) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        for (final Vec3d vector : getOffsets(height, floor)) {
            final Block block = BlockUtil.mc.world.getBlockState(new BlockPos(pos).add(vector.x, vector.y, vector.z)).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
        }
        return vec3ds;
    }
    
    public static int isPositionPlaceable(final BlockPos pos, final boolean entityCheck) {
        final Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return 0;
        }
        if (entityCheck) {
            for (final Entity entity : BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    if (entity instanceof EntityEnderCrystal) {
                        continue;
                    }
                    return 1;
                }
            }
        }
        final List<EnumFacing> possibleSides = getPossibleSides(pos);
        for (int i = 0; i < possibleSides.size(); ++i) {
            if (canBeClicked(pos.offset((EnumFacing)possibleSides.get(i)))) {
                return 3;
            }
        }
        return 2;
    }
    
    private static boolean canBeClicked(final BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos).getBlock().canCollideCheck(BlockUtil.mc.world.getBlockState(pos), false);
    }
    
    private static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState;
                if (!(blockState = BlockUtil.mc.world.getBlockState(neighbour)).getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static List<Vec3d> getOffsetList(final int y, final boolean floor) {
        final ArrayList<Vec3d> offsets = new ArrayList<Vec3d>();
        offsets.add(new Vec3d(-1.0, (double)y, 0.0));
        offsets.add(new Vec3d(1.0, (double)y, 0.0));
        offsets.add(new Vec3d(0.0, (double)y, -1.0));
        offsets.add(new Vec3d(0.0, (double)y, 1.0));
        if (floor) {
            offsets.add(new Vec3d(0.0, (double)(y - 1), 0.0));
        }
        return offsets;
    }
    
    public static Vec3d[] getOffsets(final int y, final boolean floor) {
        final List<Vec3d> offsets = getOffsetList(y, floor);
        final Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }
    
    public static Vec3d[] getTrapOffsets(final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final List<Vec3d> offsets = getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
        final Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }
    
    public static boolean areVec3dsAligned(final Vec3d vec3d1, final Vec3d vec3d2) {
        return areVec3dsAlignedRetarded(vec3d1, vec3d2);
    }
    
    public static boolean areVec3dsAlignedRetarded(final Vec3d vec3d1, final Vec3d vec3d2) {
        final BlockPos pos1 = new BlockPos(vec3d1);
        final BlockPos pos2 = new BlockPos(vec3d2.x, vec3d1.y, vec3d2.z);
        return pos1.equals((Object)pos2);
    }
    
    public static boolean isTrapped(final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        return getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() == 0;
    }
    
    public static boolean isTrappedExtended(final int extension, final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        return getUntrappedBlocksExtended(extension, player, antiScaffold, antiStep, legs, platform, antiDrop, raytrace).size() == 0;
    }
    
    public static List<Vec3d> getUntrappedBlocksExtended(final int extension, final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        final ArrayList<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (extension == 1) {
            placeTargets.addAll(targets(player.getPositionVector(), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
        }
        else {
            int extend = 1;
            final List<Vec3d> blockBlocks = getBlockBlocks((Entity)player);
            for (int i = 0; i < blockBlocks.size() && extend <= extension; ++extend, ++i) {
                placeTargets.addAll(targets(blockBlocks.get(i), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
            }
        }
        final ArrayList<Vec3d> removeList = new ArrayList<Vec3d>();
        for (int j = 0; j < placeTargets.size(); ++j) {
            final Vec3d vec3d = placeTargets.get(j);
            if (isPositionPlaceable(new BlockPos(vec3d), raytrace) == -1) {
                removeList.add(vec3d);
            }
        }
        final Iterator<Vec3d> iterator = removeList.iterator();
        while (iterator.hasNext()) {
            final Vec3d vec3d = iterator.next();
            placeTargets.remove(vec3d);
        }
        return placeTargets;
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
    
    public static List<Vec3d> getBlockBlocks(final Entity entity) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        final double y = entity.posY;
        final double minX = round(bb.minX, 0);
        final double minZ = round(bb.minZ, 0);
        final double maxX = round(bb.maxX, 0);
        final double maxZ = round(bb.maxZ, 0);
        if (minX != maxX) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(maxX, y, minZ));
            if (minZ != maxZ) {
                vec3ds.add(new Vec3d(minX, y, maxZ));
                vec3ds.add(new Vec3d(maxX, y, maxZ));
                return vec3ds;
            }
        }
        else if (minZ != maxZ) {
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
    
    public static List<Vec3d> targets(final Vec3d vec3d, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        final ArrayList<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (antiDrop) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.antiDropOffsetList));
        }
        if (platform) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.platformOffsetList));
        }
        if (legs) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.legOffsetList));
        }
        Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.OffsetList));
        Label_0244: {
            if (!antiStep) {
                final List<Vec3d> vec3ds = getUnsafeBlocksFromVec3d(vec3d, 2, false);
                if (vec3ds.size() == 4) {
                    for (int i = 0; i < vec3ds.size(); ++i) {
                        final Vec3d vector = vec3ds.get(i);
                        final BlockPos position = new BlockPos(vec3d).add(vector.x, vector.y, vector.z);
                        switch (isPositionPlaceable(position, raytrace)) {
                            case 0: {
                                break Label_0244;
                            }
                            case 3: {
                                placeTargets.add(vec3d.add(vector));
                                break;
                            }
                        }
                    }
                }
            }
            else {
                Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.antiStepOffsetList));
            }
        }
        if (antiScaffold) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.antiScaffoldOffsetList));
        }
        return placeTargets;
    }
    
    public static Vec3d[] convertVec3ds(final Vec3d vec3d, final Vec3d[] input) {
        final Vec3d[] output = new Vec3d[input.length];
        for (int i = 0; i < input.length; ++i) {
            output[i] = vec3d.add(input[i]);
        }
        return output;
    }
    
    public static Block getState(final BlockPos blockPos) {
        return BlockUtil.mc.world.getBlockState(blockPos).getBlock();
    }
    
    public static List<Vec3d> getTrapOffsetsList(final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final ArrayList<Vec3d> offsets = new ArrayList<Vec3d>(getOffsetList(1, false));
        offsets.add(new Vec3d(0.0, 2.0, 0.0));
        if (antiScaffold) {
            offsets.add(new Vec3d(0.0, 3.0, 0.0));
        }
        if (antiStep) {
            offsets.addAll(getOffsetList(2, false));
        }
        if (legs) {
            offsets.addAll(getOffsetList(0, false));
        }
        if (platform) {
            offsets.addAll(getOffsetList(-1, false));
            offsets.add(new Vec3d(0.0, -1.0, 0.0));
        }
        if (antiDrop) {
            offsets.add(new Vec3d(0.0, -2.0, 0.0));
        }
        return offsets;
    }
    
    public static List<Vec3d> getUntrappedBlocks(final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        if (!antiStep && getUnsafeBlocksFromVec3d(player.getPositionVector(), 2, false).size() == 4) {
            vec3ds.addAll(getUnsafeBlocksFromVec3d(player.getPositionVector(), 2, false));
        }
        for (int i = 0; i < getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop).length; ++i) {
            final Vec3d vector = getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)[i];
            final BlockPos targetPos = new BlockPos(player.getPositionVector()).add(vector.x, vector.y, vector.z);
            final Block block = BlockUtil.mc.world.getBlockState(targetPos).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
        }
        return vec3ds;
    }
    
    public static boolean canPosBeCrystalledSoon(final BlockPos blockPos, final boolean bl) {
        return (getState(blockPos).equals(Blocks.OBSIDIAN) || getState(blockPos).equals(Blocks.BEDROCK)) && (getState(blockPos.up().up()).equals(Blocks.AIR) || bl);
    }
    
    public static boolean isSafe(final Entity entity, final int height, final boolean floor) {
        return getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor).size() == 0;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        BlockUtil.unshift = false;
        unSolidBlocks = Arrays.asList((Block)Blocks.FLOWING_LAVA, Blocks.FLOWER_POT, Blocks.SNOW, Blocks.CARPET, Blocks.END_ROD, (Block)Blocks.SKULL, Blocks.FLOWER_POT, Blocks.TRIPWIRE, (Block)Blocks.TRIPWIRE_HOOK, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.STONE_BUTTON, Blocks.LADDER, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.POWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, Blocks.UNLIT_REDSTONE_TORCH, Blocks.REDSTONE_TORCH, (Block)Blocks.REDSTONE_WIRE, Blocks.AIR, (Block)Blocks.PORTAL, Blocks.END_PORTAL, (Block)Blocks.WATER, (Block)Blocks.FLOWING_WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_LAVA, Blocks.SAPLING, (Block)Blocks.RED_FLOWER, (Block)Blocks.YELLOW_FLOWER, (Block)Blocks.BROWN_MUSHROOM, (Block)Blocks.RED_MUSHROOM, Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, (Block)Blocks.REEDS, Blocks.PUMPKIN_STEM, Blocks.MELON_STEM, Blocks.WATERLILY, Blocks.NETHER_WART, Blocks.COCOA, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, (Block)Blocks.TALLGRASS, (Block)Blocks.DEADBUSH, Blocks.VINE, (Block)Blocks.FIRE, Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.TORCH);
        antiDropOffsetList = new Vec3d[] { new Vec3d(0.0, -2.0, 0.0) };
        platformOffsetList = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0) };
        legOffsetList = new Vec3d[] { new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0) };
        OffsetList = new Vec3d[] { new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, 0.0) };
        antiStepOffsetList = new Vec3d[] { new Vec3d(-1.0, 2.0, 0.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, -1.0) };
        antiScaffoldOffsetList = new Vec3d[] { new Vec3d(0.0, 3.0, 0.0) };
    }
}
