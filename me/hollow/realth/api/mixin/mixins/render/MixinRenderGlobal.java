//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.render;

import org.spongepowered.asm.mixin.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.hollow.realth.client.events.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderGlobal.class })
public abstract class MixinRenderGlobal implements IRenderGlobal
{
    @Nonnull
    @Accessor("damagedBlocks")
    public abstract Map<Integer, DestroyBlockProgress> getDamagedBlocks();
    
    @Inject(method = { "sendBlockBreakProgress" }, at = { @At("HEAD") })
    public void onSendingBlockBreakProgressPre(final int breakerId, final BlockPos pos, final int progress, final CallbackInfo ci) {
        final DamageBlockEvent event = new DamageBlockEvent(pos, progress, breakerId);
    }
}
