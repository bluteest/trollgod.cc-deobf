//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.entity;

import me.hollow.realth.api.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Entity.class })
public abstract class MixinEntity implements Util
{
    @Shadow
    private int entityId;
    @Shadow
    protected boolean isInWeb;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    
    @Shadow
    public void move(final MoverType type, final double x, final double y, final double z) {
    }
    
    @Shadow
    @Override
    public abstract boolean equals(final Object p0);
    
    @Shadow
    public abstract int getEntityId();
    
    @Inject(method = { "turn" }, at = { @At("HEAD") }, cancellable = true)
    public void onTurnHook(final float yaw, final float pitch, final CallbackInfo info) {
        final TurnEvent event = new TurnEvent(yaw, pitch);
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
