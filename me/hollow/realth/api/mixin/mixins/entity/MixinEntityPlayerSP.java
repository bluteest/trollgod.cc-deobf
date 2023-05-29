//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.Minecraft
 *  net.minecraft.other.entity.EntityPlayerSP
 *  net.minecraft.entity.MoverType
 *  net.minecraft.util.math.BlockPos
 */
package me.hollow.realth.api.mixin.mixins.entity;

import me.hollow.realth.JordoHack;
import me.hollow.realth.client.events.MotionUpdateEvent;
import me.hollow.realth.client.events.MoveEvent;
import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.events.UpdateWalkingPlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityPlayerSP.class})
public abstract class MixinEntityPlayerSP
extends MixinEntityPlayer {
    @Shadow
    protected Minecraft mc;

    @Override
    public void move(MoverType type, double x, double y, double z) {
        MoveEvent event = new MoveEvent(x, y, z);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
        super.move(type, event.getMotionX(), event.getMotionY(), event.getMotionZ());
    }

    @Inject(method={"onUpdate"}, at={@At(value="HEAD")})
    public void onUpdatePre(CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent(new UpdateEvent(0));
    }

    @Inject(method={"onUpdate"}, at={@At(value="RETURN")})
    public void onUpdatePost(CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent(new UpdateEvent(1));
    }
    @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At(value = "HEAD")})
    private void preMotion(CallbackInfo info) {
        UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(0);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
    }

    @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At(value = "RETURN")})
    private void postMotion(CallbackInfo info) {
        UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(1);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
    }
    @Inject( method = "onUpdate", at = @At( value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.BEFORE ) )
    public void onPreMotionUpdate(CallbackInfo info) {
        MotionUpdateEvent event = new MotionUpdateEvent(Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch, Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround, Minecraft.getMinecraft().player.noClip, 0);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
    }

    @Inject( method = "onUpdate", at = @At( value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.AFTER ) )
    public void onPostMotionUpdate(CallbackInfo info) {
        MotionUpdateEvent event = new MotionUpdateEvent(Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch,
                Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround, Minecraft.getMinecraft().player.noClip, 1);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
    }

}



