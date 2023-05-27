//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.entity;

import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import me.hollow.realth.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.hollow.realth.client.events.*;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends MixinEntityPlayer
{
    @Shadow
    protected Minecraft mc;
    
    public void move(final MoverType type, final double x, final double y, final double z) {
        final MoveEvent event = new MoveEvent(x, y, z);
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)event);
        super.move(type, event.getMotionX(), event.getMotionY(), event.getMotionZ());
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") })
    public void onUpdatePre(final CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)new UpdateEvent(0));
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("RETURN") })
    public void onUpdatePost(final CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)new UpdateEvent(1));
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") })
    private void preMotion(final CallbackInfo info) {
        final UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(0);
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)event);
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    private void postMotion(final CallbackInfo info) {
        final UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(1);
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)event);
    }
}
