//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.hollow.realth.client.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.scoreboard.*;

@Mixin({ GuiIngame.class })
public class MixinGuiInGame extends Gui
{
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffectsHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (NoRender.getInstance().potion.getValue() && NoRender.getInstance().isEnabled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderScoreboard" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderScoreboardHook(final ScoreObjective objective, final ScaledResolution scaledRes, final CallbackInfo info) {
        if (NoRender.getInstance().scoreboard.getValue() && NoRender.getInstance().isEnabled()) {
            info.cancel();
        }
    }
}
