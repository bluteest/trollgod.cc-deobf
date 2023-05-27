//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.movement;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.b0at.api.event.*;
import me.hollow.realth.client.events.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;

@ModuleManifest(label = "Speed", category = Module.Category.MOVEMENT)
public class Speed extends Module
{
    private final Setting<Boolean> placeHolder;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    
    public Speed() {
        this.placeHolder = (Setting<Boolean>)this.register(new Setting("Dummy", (Object)true));
        this.stage = 1;
    }
    
    public void onEnable() {
        if (Speed.mc.player == null) {
            return;
        }
        this.setSuffix("Strafe");
        this.lastDist = 0.0;
    }
    
    public void onDisable() {
        if (Speed.mc.player == null) {
            return;
        }
    }
    
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        if (this.placeHolder.getValue()) {
            return;
        }
        this.lastDist = Math.sqrt((Speed.mc.player.posX - Speed.mc.player.prevPosX) * (Speed.mc.player.posX - Speed.mc.player.prevPosX) + (Speed.mc.player.posZ - Speed.mc.player.prevPosZ) * (Speed.mc.player.posZ - Speed.mc.player.prevPosZ));
        if (this.lastDist > 5.0) {
            this.lastDist = 0.0;
        }
    }
    
    @EventHandler
    public void onMotion(final MoveEvent event) {
        if (this.placeHolder.getValue()) {
            return;
        }
        double forward = Speed.mc.player.movementInput.moveForward;
        double strafe = Speed.mc.player.movementInput.moveStrafe;
        final double yaw = Speed.mc.player.rotationYaw;
        switch (this.stage) {
            case 0: {
                ++this.stage;
                this.lastDist = 0.0;
                break;
            }
            case 2: {
                double motionY = 0.4;
                if (Speed.mc.player.moveForward == 0.0f && Speed.mc.player.moveStrafing == 0.0f) {
                    break;
                }
                if (!Speed.mc.player.onGround) {
                    break;
                }
                if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    motionY += (Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                }
                event.setMotionY(Speed.mc.player.motionY = motionY);
                this.moveSpeed *= (Speed.mc.player.isPotionActive(MobEffects.SPEED) ? 2.1499 : 2.1499);
                break;
            }
            case 3: {
                this.moveSpeed = this.lastDist - (Speed.mc.player.isPotionActive(MobEffects.SPEED) ? 0.658 : 0.71) * (this.lastDist - this.getBaseMoveSpeed());
                break;
            }
            default: {
                if ((Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().offset(0.0, Speed.mc.player.motionY, 0.0)).size() > 0 || Speed.mc.player.collidedVertically) && this.stage > 0) {
                    this.stage = ((Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f) ? 1 : 0);
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                break;
            }
        }
        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
        if (forward == 0.0 && strafe == 0.0) {
            event.setMotionX(0.0);
            event.setMotionZ(0.0);
        }
        if (forward != 0.0 && strafe != 0.0) {
            forward *= Math.sin(0.7853981633974483);
            strafe *= Math.cos(0.7853981633974483);
        }
        event.setMotionX((forward * this.moveSpeed * -Math.sin(Math.toRadians(yaw)) + strafe * this.moveSpeed * Math.cos(Math.toRadians(yaw))) * 0.99);
        event.setMotionZ((forward * this.moveSpeed * Math.cos(Math.toRadians(yaw)) - strafe * this.moveSpeed * -Math.sin(Math.toRadians(yaw))) * 0.99);
        ++this.stage;
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.272;
        if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Speed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * amplifier;
        }
        return baseSpeed;
    }
}
