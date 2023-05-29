//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.MobEffects
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.CombatRules
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 */
package me.hollow.realth.api.util;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.Util;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import me.hollow.realth.api.util.TextUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtil
implements Util {
    public static boolean isntValid(EntityPlayer entity, double range) {
        return (double)EntityUtil.mc.player.getDistance((Entity)entity) > range || entity.getHealth() <= 0.0f || entity == EntityUtil.mc.player || JordoHack.INSTANCE.getFriendManager().isFriend(entity);
    }
    public static boolean isInLiquid() {
        return EntityUtil.mc.player.isInWater() || EntityUtil.mc.player.isInLava();
    }


    public static boolean isMoving() {
        return (mc.player.moveForward != 0 || mc.player.moveStrafing != 0);
    }

    public static EntityPlayer getClosestPlayer(final float range) {
        EntityPlayer player = null;
        double maxDistance = 999F;
        final int size = mc.world.playerEntities.size();
        for (int i = 0; i < size; ++i) {
            final EntityPlayer entityPlayer = mc.world.playerEntities.get(i);
            if (isntValid(entityPlayer, range)) {
                final double distanceSq = mc.player.getDistanceSq(entityPlayer);
                if (maxDistance == 999F || distanceSq < maxDistance) {
                    maxDistance = distanceSq;
                    player = entityPlayer;
                }
            }
        }
        return player;
    }
    public static BlockPos getRoundedBlockPos(Entity entity) {
        return new BlockPos(MathUtil.roundVec(entity.getPositionVector(), 0));
    }


    public static float getHealth(EntityPlayer player) {
        return player.getHealth() + player.getAbsorptionAmount();
    }

    public static float calculate(double x, double y, double z, EntityLivingBase base) {
        PotionEffect resistance;
        double distance = base.getDistance(x, y, z) / 12.0;
        if (distance > 1.0) {
            return 0.0f;
        }
        double densityDistance = distance = (1.0 - distance) * (double)EntityUtil.mc.world.getBlockDensity(new Vec3d(x, y, z), base.getEntityBoundingBox());
        float damage = EntityUtil.getDifficultyMultiplier((float)((densityDistance * densityDistance + distance) / 2.0 * 7.0 * 12.0 + 1.0));
        DamageSource damageSource = DamageSource.causeExplosionDamage((Explosion)new Explosion((World)EntityUtil.mc.world, (Entity)EntityUtil.mc.player, x, y, z, 6.0f, false, true));
        damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)base.getTotalArmorValue(), (float)((float)base.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
        int modifierDamage = EntityUtil.getEnchantmentModifierDamage(base.getArmorInventoryList(), damageSource);
        if (modifierDamage > 0) {
            damage = CombatRules.getDamageAfterMagicAbsorb((float)damage, (float)modifierDamage);
        }
        if ((resistance = base.getActivePotionEffect(MobEffects.RESISTANCE)) != null) {
            damage = damage * (float)(25 - (resistance.getAmplifier() + 1) * 5) / 25.0f;
        }
        return Math.max(damage, 0.0f);
    }

    public static int getEnchantmentModifierDamage(Iterable<ItemStack> stacks, DamageSource source) {
        int modifier = 0;
        for (ItemStack stack : stacks) {
            NBTTagList nbttaglist = stack.getEnchantmentTagList();
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                Enchantment enchantment = Enchantment.getEnchantmentByID((int)nbttaglist.getCompoundTagAt(i).getShort("id"));
                if (enchantment == null) continue;
                modifier += enchantment.calcModifierDamage((int)nbttaglist.getCompoundTagAt(i).getShort("lvl"), source);
            }
        }
        return modifier;
    }

    public static float getDifficultyMultiplier(float distance) {
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
        }
        return distance;
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
        return EntityUtil.getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return EntityUtil.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedPos(Entity entity, double ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(EntityUtil.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedEyePos(Entity entity, double ticks) {
        return EntityUtil.getInterpolatedPos(entity, ticks).add(0.0, (double)entity.getEyeHeight(), 0.0);
    }
    public static boolean isSafe(Entity entity, int height, boolean floor) {
        return EntityUtil.getUnsafeBlocks(entity, height, floor).size() == 0;
    }

    public static boolean isSafe(Entity entity) {
        return EntityUtil.isSafe(entity, 0, false);
    }
    public static List<Vec3d> getUnsafeBlocks(Entity entity, int height, boolean floor) {
        return EntityUtil.getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
    }
    public static boolean isLiving(Entity entity) {
        return entity instanceof EntityLivingBase;
    }
    public static List<Vec3d> getUnsafeBlocksFromVec3d(Vec3d pos, int height, boolean floor) {
        ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        for (Vec3d vector : EntityUtil.getOffsets(height, floor)) {
            BlockPos targetPos = new BlockPos(pos).add(vector.x, vector.y, vector.z);
            Block block = mc.world.getBlockState(targetPos).getBlock();
            if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow))
                continue;
            vec3ds.add(vector);
        }
        return vec3ds;
    }
    public static List<Vec3d> getOffsetList(int y, boolean floor) {
        ArrayList<Vec3d> offsets = new ArrayList<Vec3d>();
        offsets.add(new Vec3d(-1.0, y, 0.0));
        offsets.add(new Vec3d(1.0, y, 0.0));
        offsets.add(new Vec3d(0.0, y, -1.0));
        offsets.add(new Vec3d(0.0, y, 1.0));
        if (floor) {
            offsets.add(new Vec3d(0.0, y - 1, 0.0));
        }
        return offsets;
    }

    public static Vec3d[] getOffsets(int y, boolean floor) {
        List<Vec3d> offsets = EntityUtil.getOffsetList(y, floor);
        Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }
    public static Map<String, Integer> getTextRadarPlayers() {
        Map<String, Integer> output = new HashMap<String, Integer>();
        final DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.CEILING);
        final DecimalFormat dfDistance = new DecimalFormat("#.#");
        dfDistance.setRoundingMode(RoundingMode.CEILING);
        final StringBuilder healthSB = new StringBuilder();
        final StringBuilder distanceSB = new StringBuilder();
        for (final EntityPlayer player : EntityUtil.mc.world.playerEntities) {
            if (player.getName().equals(EntityUtil.mc.player.getName())) {
                continue;
            }
            final int hpRaw = (int) getHealth(player);
            final String hp = dfHealth.format(hpRaw);
            final int distanceInt = (int) EntityUtil.mc.player.getDistance(player);
            final String distance = dfDistance.format(distanceInt);
            distanceSB.append(distance);
            output.put(healthSB.toString() + " " + (JordoHack.INSTANCE.getFriendManager().isFriend(player) ? "§b" : "§r") + player.getName() + " " + distanceSB.toString() + " "  + JordoHack.INSTANCE.getTotemPopManager().getTotemPopString(player), (int) EntityUtil.mc.player.getDistance(player));
        }
        if (!output.isEmpty()) {
            output = MathUtil.sortByValue(output, false);
        }
        return output;
    }

}

