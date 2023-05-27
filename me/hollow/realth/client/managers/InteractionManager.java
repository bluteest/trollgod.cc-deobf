//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import me.hollow.realth.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class InteractionManager
{
    private final Minecraft mc;
    public final List<Block> sneakBlocks;
    public final List<Block> shulkers;
    
    public InteractionManager() {
        this.mc = Minecraft.getMinecraft();
        this.sneakBlocks = Arrays.asList(Blocks.ENDER_CHEST, Blocks.ANVIL, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
        this.shulkers = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
    }
    
    public EnumFacing getFirstEnumFacing(final BlockPos pos) {
        return this.getEnumFacingSides(pos).stream().findFirst().orElse(null);
    }
    
    public ArrayList<EnumFacing> getEnumFacingSides(final BlockPos pos) {
        final ArrayList<EnumFacing> sides = new ArrayList<EnumFacing>();
        final BlockPos pos2;
        final ArrayList<EnumFacing> list;
        Arrays.stream(EnumFacing.values()).forEach(side -> {
            pos2 = pos.offset(side);
            if (this.mc.world.getBlockState(pos2).getBlock().canCollideCheck(this.mc.world.getBlockState(pos2), false) && !this.mc.world.getBlockState(pos2).getMaterial().isReplaceable()) {
                list.add(side);
            }
            return;
        });
        return sides;
    }
    
    public ArrayList<EnumFacing> getVisibleSides(final BlockPos pos) {
        final ArrayList<EnumFacing> sides = new ArrayList<EnumFacing>();
        final Vec3d vec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final Vec3d eye = this.mc.player.getPositionEyes(1.0f);
        final double facingX = eye.x - vec.x;
        final double facingY = eye.y - vec.y;
        final double facingZ = eye.z - vec.z;
        if (facingX < -0.5) {
            sides.add(EnumFacing.WEST);
        }
        else if (facingX > 0.5) {
            sides.add(EnumFacing.EAST);
        }
        else if (this.isVisible(pos)) {
            sides.add(EnumFacing.WEST);
            sides.add(EnumFacing.EAST);
        }
        if (facingY < -0.5) {
            sides.add(EnumFacing.DOWN);
        }
        else if (facingY > 0.5) {
            sides.add(EnumFacing.UP);
        }
        else {
            sides.add(EnumFacing.DOWN);
            sides.add(EnumFacing.UP);
        }
        if (facingZ < -0.5) {
            sides.add(EnumFacing.NORTH);
        }
        else if (facingZ > 0.5) {
            sides.add(EnumFacing.SOUTH);
        }
        else if (this.isVisible(pos)) {
            sides.add(EnumFacing.NORTH);
            sides.add(EnumFacing.SOUTH);
        }
        return sides;
    }
    
    public void interactBlock(final BlockPos pos, final EnumFacing enumFacing) {
        this.mc.playerController.onPlayerDamageBlock(pos, enumFacing);
    }
    
    protected boolean isVisible(final BlockPos pos) {
        return !this.mc.world.getBlockState(pos).isFullBlock() || !this.mc.world.isAirBlock(pos);
    }
    
    public void load() {
        JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
    }
    
    public void unload() {
        JordoHack.INSTANCE.getEventManager().deregisterListener((Object)this);
    }
    
    public EnumFacing closestEnumFacing(final BlockPos pos) {
        final TreeMap<Double, EnumFacing> enumFacingTreeMap = JordoHack.interactionManager.getVisibleSides(pos).stream().collect((Collector<? super Object, ?, TreeMap<Double, EnumFacing>>)Collectors.toMap(enumFacing -> this.getDistanceToFace(pos, enumFacing), enumFacing -> enumFacing, (a, b) -> b, (Supplier<R>)TreeMap::new));
        return enumFacingTreeMap.firstEntry().getValue();
    }
    
    protected double getDistanceToFace(final BlockPos pos, final EnumFacing enumFacing) {
        Vec3i vec3i = new Vec3i(0.5, 0.5, 0.5);
        switch (enumFacing) {
            case NORTH: {
                vec3i = new Vec3i(0.5, 0.5, -1.5);
                break;
            }
            case EAST: {
                vec3i = new Vec3i(1.5, 0.5, 0.5);
                break;
            }
            case SOUTH: {
                vec3i = new Vec3i(0.5, 0.5, 1.5);
                break;
            }
            case WEST: {
                vec3i = new Vec3i(-1.5, 0.5, 0.5);
                break;
            }
            case UP: {
                vec3i = new Vec3i(0.5, 1.5, 0.5);
                break;
            }
            case DOWN: {
                vec3i = new Vec3i(0.5, -1.5, 0.5);
                break;
            }
        }
        return this.mc.player.getDistanceSq(pos.add(vec3i));
    }
    
    public void attemptBreak(final BlockPos pos, final EnumFacing enumFacing) {
        final int slot = JordoHack.inventoryManager.getItemFromHotbar(Items.DIAMOND_PICKAXE);
        if (slot != -1 && this.mc.player != null && this.mc.player.connection != null) {
            final int currentItem = this.mc.player.inventory.currentItem;
            JordoHack.inventoryManager.switchToSlot(slot);
            try {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, enumFacing));
            }
            catch (Exception ex) {}
            JordoHack.inventoryManager.switchBack(currentItem);
        }
    }
    
    public void initiateBreaking(final BlockPos pos, final EnumFacing enumFacing, final boolean swing) {
        if (this.mc.player.connection != null) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, enumFacing));
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, enumFacing));
        }
        if (swing) {
            this.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public void abort(final BlockPos pos, final EnumFacing enumFacing) {
        if (this.mc.player.connection != null) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos, enumFacing));
        }
        this.mc.playerController.isHittingBlock = false;
        this.mc.playerController.curBlockDamageMP = 0.0f;
        this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityId(), pos, -1);
        this.mc.player.resetCooldown();
    }
}
