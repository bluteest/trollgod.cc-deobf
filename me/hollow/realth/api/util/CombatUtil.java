//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 */
package me.hollow.realth.api.util;

import me.hollow.realth.api.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class CombatUtil
implements Util {
    public static EntityPlayer getTarget(float range) {
        EntityPlayer currentTarget = null;
        for (int i = 0; i < CombatUtil.mc.world.playerEntities.size(); ++i) {
            EntityPlayer player = (EntityPlayer)CombatUtil.mc.world.playerEntities.get(i);
            if (EntityUtil.isntValid(player, range)) continue;
            if (currentTarget == null) {
                currentTarget = player;
                continue;
            }
            if (!(CombatUtil.mc.player.getDistanceSq((Entity)player) < CombatUtil.mc.player.getDistanceSq((Entity)currentTarget))) continue;
            currentTarget = player;
        }
        return currentTarget;
    }

    public static boolean is2x1(BlockPos pos) {
        return false;
    }

    public static boolean isInHole(EntityPlayer entity) {
        return CombatUtil.isBlockValid(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }

    public static int isInHoleInt(EntityPlayer entity) {
        BlockPos playerPos = new BlockPos(entity.getPositionVector());
        if (CombatUtil.isBedrockHole(playerPos)) {
            return 1;
        }
        if (CombatUtil.isObbyHole(playerPos)) {
            return 2;
        }
        return 0;
    }

    public static boolean isBlockValid(BlockPos blockPos) {
        return CombatUtil.isBedrockHole(blockPos) || CombatUtil.isObbyHole(blockPos);
    }

    public static boolean isObbyHole(BlockPos blockPos) {
        BlockPos[] touchingBlocks;
        for (BlockPos pos : touchingBlocks = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState touchingState = CombatUtil.mc.world.getBlockState(pos);
            if (touchingState.getBlock() != Blocks.AIR && (touchingState.getBlock() == Blocks.OBSIDIAN || touchingState.getBlock() == Blocks.BEDROCK) && touchingState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        BlockPos[] touchingBlocks;
        for (BlockPos pos : touchingBlocks = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState touchingState = CombatUtil.mc.world.getBlockState(pos);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }
}

