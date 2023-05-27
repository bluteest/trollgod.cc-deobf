//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import me.hollow.realth.api.*;
import me.hollow.realth.client.modules.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import me.hollow.realth.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;

public class BlockPlaceUtil implements Util
{
    public static void placeBlock(final BlockPos pos, final boolean rotate, final boolean packet, final boolean ignoreEntities) {
        if (Module.fullNullCheck()) {
            return;
        }
        final Optional<ClickLocation> posCL = getClickLocation(pos, ignoreEntities, false);
        if (posCL.isPresent()) {
            final BlockPos currentPos = posCL.get().neighbour;
            final EnumFacing currentFace = posCL.get().opposite;
            final boolean shouldSneak = shouldShiftClick(currentPos);
            if (shouldSneak) {
                BlockPlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockPlaceUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            final Vec3d hitVec = new Vec3d((Vec3i)currentPos).add(0.5, 0.5, 0.5).add(new Vec3d(currentFace.getDirectionVec()).scale(0.5));
            if (rotate) {
                JordoHack.INSTANCE.getRotationManager().lookAtVec3dPacket(hitVec, false, true);
            }
            if (packet) {
                final Vec3d extendedVec = new Vec3d((Vec3i)currentPos).add(0.5, 0.5, 0.5);
                final float x = (float)(extendedVec.x - currentPos.getX());
                final float y = (float)(extendedVec.y - currentPos.getY());
                final float z = (float)(extendedVec.z - currentPos.getZ());
                BlockPlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(currentPos, currentFace, EnumHand.MAIN_HAND, x, y, z));
            }
            else {
                BlockPlaceUtil.mc.playerController.processRightClickBlock(BlockPlaceUtil.mc.player, BlockPlaceUtil.mc.world, currentPos, currentFace, hitVec, EnumHand.MAIN_HAND);
            }
            BlockPlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            if (shouldSneak) {
                BlockPlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockPlaceUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
        }
    }
    
    public static void placeBlock(final BlockPos pos, final boolean rotate, final boolean packet) {
        placeBlock(pos, rotate, packet, false);
    }
    
    public void attackEntity(final Entity entity, final boolean packet, final boolean swing) {
        if (packet) {
            BlockPlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
        }
        else {
            BlockPlaceUtil.mc.playerController.attackEntity((EntityPlayer)BlockPlaceUtil.mc.player, entity);
        }
        if (swing) {
            BlockPlaceUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public static Optional<ClickLocation> getClickLocation(final BlockPos pos, final boolean ignoreEntities, final boolean noPistons) {
        final Block block = BlockPlaceUtil.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return Optional.empty();
        }
        if (!ignoreEntities) {
            for (final Entity entity : BlockPlaceUtil.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityArrow)) {
                    return Optional.empty();
                }
            }
        }
        EnumFacing side = null;
        for (final EnumFacing blockSide : EnumFacing.values()) {
            final BlockPos sidePos = pos.offset(blockSide);
            if (!noPistons || BlockPlaceUtil.mc.world.getBlockState(sidePos).getBlock() != Blocks.PISTON) {
                if (BlockPlaceUtil.mc.world.getBlockState(sidePos).getBlock().canCollideCheck(BlockPlaceUtil.mc.world.getBlockState(sidePos), false)) {
                    final IBlockState blockState = BlockPlaceUtil.mc.world.getBlockState(sidePos);
                    if (!blockState.getMaterial().isReplaceable()) {
                        side = blockSide;
                        break;
                    }
                }
            }
        }
        if (side == null) {
            return Optional.empty();
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockPlaceUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockPlaceUtil.mc.world.getBlockState(neighbour), false)) {
            return Optional.empty();
        }
        return Optional.of(new ClickLocation(neighbour, opposite));
    }
    
    public static boolean shouldShiftClick(final BlockPos pos) {
        final Block block = BlockPlaceUtil.mc.world.getBlockState(pos).getBlock();
        TileEntity tileEntity = null;
        for (final TileEntity entity : BlockPlaceUtil.mc.world.loadedTileEntityList) {
            if (!entity.getPos().equals((Object)pos)) {
                continue;
            }
            tileEntity = entity;
            break;
        }
        return tileEntity != null || block instanceof BlockBed || block instanceof BlockContainer || block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate || block instanceof BlockButton || block instanceof BlockAnvil || block instanceof BlockWorkbench || block instanceof BlockCake || block instanceof BlockRedstoneDiode;
    }
    
    public static class ClickLocation
    {
        public final BlockPos neighbour;
        public final EnumFacing opposite;
        
        public ClickLocation(final BlockPos neighbour, final EnumFacing opposite) {
            this.neighbour = neighbour;
            this.opposite = opposite;
        }
    }
}
