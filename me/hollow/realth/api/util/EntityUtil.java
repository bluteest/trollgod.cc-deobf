//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import me.hollow.realth.api.*;
import net.minecraft.entity.player.*;
import me.hollow.realth.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.block.*;

public class EntityUtil implements Util
{
    public static boolean isntValid(final EntityPlayer entity, final double range) {
        return EntityUtil.mc.player.getDistance((Entity)entity) > range || entity.getHealth() <= 0.0f || entity == EntityUtil.mc.player || JordoHack.INSTANCE.getFriendManager().isFriend(entity);
    }
    
    public static boolean isInLiquid() {
        return EntityUtil.mc.player.isInWater() || EntityUtil.mc.player.isInLava();
    }
    
    public static boolean isMoving() {
        return EntityUtil.mc.player.moveForward != 0.0f || EntityUtil.mc.player.moveStrafing != 0.0f;
    }
    
    public static EntityPlayer getClosestPlayer(final float range) {
        EntityPlayer player = null;
        double maxDistance = 999.0;
        for (int size = EntityUtil.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer entityPlayer = EntityUtil.mc.world.playerEntities.get(i);
            if (isntValid(entityPlayer, range)) {
                final double distanceSq = EntityUtil.mc.player.getDistanceSq((Entity)entityPlayer);
                if (maxDistance == 999.0 || distanceSq < maxDistance) {
                    maxDistance = distanceSq;
                    player = entityPlayer;
                }
            }
        }
        return player;
    }
    
    public static BlockPos getRoundedBlockPos(final Entity entity) {
        return new BlockPos(MathUtil.roundVec(entity.getPositionVector(), 0));
    }
    
    public static float getHealth(final EntityPlayer player) {
        return player.getHealth() + player.getAbsorptionAmount();
    }
    
    public static float calculate(final double x, final double y, final double z, final EntityLivingBase base) {
        double distance = base.getDistance(x, y, z) / 12.0;
        if (distance > 1.0) {
            return 0.0f;
        }
        final double densityDistance;
        distance = (densityDistance = (1.0 - distance) * EntityUtil.mc.world.getBlockDensity(new Vec3d(x, y, z), base.getEntityBoundingBox()));
        float damage = getDifficultyMultiplier((float)((densityDistance * densityDistance + distance) / 2.0 * 7.0 * 12.0 + 1.0));
        final DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion((World)EntityUtil.mc.world, (Entity)EntityUtil.mc.player, x, y, z, 6.0f, false, true));
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)base.getTotalArmorValue(), (float)base.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        final int modifierDamage = getEnchantmentModifierDamage(base.getArmorInventoryList(), damageSource);
        if (modifierDamage > 0) {
            damage = CombatRules.getDamageAfterMagicAbsorb(damage, (float)modifierDamage);
        }
        final PotionEffect resistance;
        if ((resistance = base.getActivePotionEffect(MobEffects.RESISTANCE)) != null) {
            damage = damage * (25 - (resistance.getAmplifier() + 1) * 5) / 25.0f;
        }
        return Math.max(damage, 0.0f);
    }
    
    public static int getEnchantmentModifierDamage(final Iterable<ItemStack> stacks, final DamageSource source) {
        int modifier = 0;
        for (final ItemStack stack : stacks) {
            final NBTTagList nbttaglist = stack.getEnchantmentTagList();
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                final Enchantment enchantment = Enchantment.getEnchantmentByID((int)nbttaglist.getCompoundTagAt(i).getShort("id"));
                if (enchantment != null) {
                    modifier += enchantment.calcModifierDamage((int)nbttaglist.getCompoundTagAt(i).getShort("lvl"), source);
                }
            }
        }
        return modifier;
    }
    
    public static float getDifficultyMultiplier(final float distance) {
        switch (EntityUtil.mc.world.getDifficulty()) {
            case HARD: {
                return distance * 3.0f / 2.0f;
            }
            case PEACEFUL: {
                return 0.0f;
            }
            case EASY: {
                return Math.min(distance / 2.0f + 1.0f, distance);
            }
            default: {
                return distance;
            }
        }
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final Vec3d vec) {
        return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final double ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedEyePos(final Entity entity, final double ticks) {
        return getInterpolatedPos(entity, ticks).add(0.0, (double)entity.getEyeHeight(), 0.0);
    }
    
    public static boolean isSafe(final Entity entity, final int height, final boolean floor) {
        return getUnsafeBlocks(entity, height, floor).size() == 0;
    }
    
    public static boolean isSafe(final Entity entity) {
        return isSafe(entity, 0, false);
    }
    
    public static List<Vec3d> getUnsafeBlocks(final Entity entity, final int height, final boolean floor) {
        return getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
    }
    
    public static boolean isLiving(final Entity entity) {
        return entity instanceof EntityLivingBase;
    }
    
    public static List<Vec3d> getUnsafeBlocksFromVec3d(final Vec3d pos, final int height, final boolean floor) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        for (final Vec3d vector : getOffsets(height, floor)) {
            final BlockPos targetPos = new BlockPos(pos).add(vector.x, vector.y, vector.z);
            final Block block = EntityUtil.mc.world.getBlockState(targetPos).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
        }
        return vec3ds;
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
}
