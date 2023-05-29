//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.MobEffects
 */
package me.hollow.realth.client.modules.movement;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.MoveEvent;
import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;

@ModuleManifest(label="Speed", category=Module.Category.MOVEMENT)
public class Speed
extends Module {
    private final Setting<Boolean> placeHolder = this.register(new Setting<Boolean>("Dummy", true));
    private int stage = 1;
    private double moveSpeed;
    private double lastDist;

    @Override
    public void onEnable() {
        if (this.mc.player == null) {
            return;
        }
        this.setSuffix("Strafe");
        this.lastDist = 0.0;
    }

    @Override
    public void onDisable() {
        if (this.mc.player == null) {
            return;
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (this.placeHolder.getValue().booleanValue()) {
            return;
        }
        this.lastDist = Math.sqrt((this.mc.player.posX - this.mc.player.prevPosX) * (this.mc.player.posX - this.mc.player.prevPosX) + (this.mc.player.posZ - this.mc.player.prevPosZ) * (this.mc.player.posZ - this.mc.player.prevPosZ));
        if (this.lastDist > 5.0) {
            this.lastDist = 0.0;
        }
    }

    @EventHandler
    public void onMotion(MoveEvent event) {
        if (this.placeHolder.getValue().booleanValue()) {
            return;
        }
        double forward = this.mc.player.movementInput.moveForward;
        double strafe = this.mc.player.movementInput.moveStrafe;
        double yaw = this.mc.player.rotationYaw;
        switch (this.stage) {
            case 0: {
                ++this.stage;
                this.lastDist = 0.0;
                break;
            }
            case 2: {
                double motionY = 0.4;
                if (this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f || !this.mc.player.onGround) break;
                if (this.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    motionY += (double)((float)(this.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
                }
                this.mc.player.motionY = motionY;
                event.setMotionY(this.mc.player.motionY);
                this.moveSpeed *= this.mc.player.isPotionActive(MobEffects.SPEED) ? 2.1499 : 2.1499;
                break;
            }
            case 3: {
                this.moveSpeed = this.lastDist - (this.mc.player.isPotionActive(MobEffects.SPEED) ? 0.658 : 0.71) * (this.lastDist - this.getBaseMoveSpeed());
                break;
            }
            default: {
                if ((this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, this.mc.player.motionY, 0.0)).size() > 0 || this.mc.player.collidedVertically) && this.stage > 0) {
                    this.stage = this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f ? 0 : 1;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
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
        if (this.mc.player.isPotionActive(MobEffects.SPEED)) {
            int amplifier = this.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)amplifier;
        }
        return baseSpeed;
    }
}

