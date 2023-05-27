//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import me.hollow.realth.client.events.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;

public class MovementUtil
{
    private static final Minecraft mc;
    
    public static double[] directionSpeed(final double speed) {
        float forward = MovementUtil.mc.player.movementInput.moveForward;
        float side = MovementUtil.mc.player.movementInput.moveStrafe;
        float yaw = MovementUtil.mc.player.prevRotationYaw + (MovementUtil.mc.player.rotationYaw - MovementUtil.mc.player.prevRotationYaw) * MovementUtil.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static double[] dirSpeedNew(final double speed) {
        float moveForward = MovementUtil.mc.player.movementInput.moveForward;
        float moveStrafe = MovementUtil.mc.player.movementInput.moveStrafe;
        float rotationYaw = MovementUtil.mc.player.prevRotationYaw + (MovementUtil.mc.player.rotationYaw - MovementUtil.mc.player.prevRotationYaw) * MovementUtil.mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
            }
            else if (moveStrafe < 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double posX = moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        final double posZ = moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[] { posX, posZ };
    }
    
    public static double[] futureCalc1(final double d) {
        final MovementInput movementInput = MovementUtil.mc.player.movementInput;
        double d2 = movementInput.moveForward;
        double d3 = movementInput.moveStrafe;
        float f = MovementUtil.mc.player.rotationYaw;
        double d5;
        double d4;
        if (d2 == 0.0 && d3 == 0.0) {
            d4 = (d5 = 0.0);
        }
        else {
            if (d2 != 0.0) {
                if (d3 > 0.0) {
                    f += ((d2 > 0.0) ? -45 : 45);
                }
                else if (d3 < 0.0) {
                    f += ((d2 > 0.0) ? 45 : -45);
                }
                d3 = 0.0;
                if (d2 > 0.0) {
                    d2 = 1.0;
                }
                else if (d2 < 0.0) {
                    d2 = -1.0;
                }
            }
            d4 = d2 * d * Math.cos(Math.toRadians(f + 90.0f)) + d3 * d * Math.sin(Math.toRadians(f + 90.0f));
            d5 = d2 * d * Math.sin(Math.toRadians(f + 90.0f)) - d3 * d * Math.cos(Math.toRadians(f + 90.0f));
        }
        return new double[] { d4, d5 };
    }
    
    public static void strafe(final MoveEvent event, final double speed) {
        if (isMoving()) {
            final double[] strafe = strafe(speed);
            event.setMotionX(strafe[0]);
            event.setMotionZ(strafe[1]);
        }
        else {
            event.setMotionX(0.0);
            event.setMotionZ(0.0);
        }
    }
    
    public static double[] strafe(final double speed) {
        return strafe((Entity)MovementUtil.mc.player, speed);
    }
    
    public static double[] strafe(final Entity entity, final double speed) {
        return strafe(entity, MovementUtil.mc.player.movementInput, speed);
    }
    
    public static double[] strafe(final Entity entity, final MovementInput movementInput, final double speed) {
        float moveForward = movementInput.moveForward;
        float moveStrafe = movementInput.moveStrafe;
        float rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * MovementUtil.mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
            }
            else if (moveStrafe < 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double posX = moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        final double posZ = moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[] { posX, posZ };
    }
    
    public static boolean isMoving() {
        return MovementUtil.mc.player.moveForward != 0.0 || MovementUtil.mc.player.moveStrafing != 0.0;
    }
    
    public static double getSpeed() {
        return getSpeed(false);
    }
    
    public static double getSpeed(final boolean slowness) {
        double defaultSpeed = 0.2873;
        if (MovementUtil.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(MovementUtil.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (slowness && MovementUtil.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            final int amplifier = Objects.requireNonNull(MovementUtil.mc.player.getActivePotionEffect(MobEffects.SLOWNESS)).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
