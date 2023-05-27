//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.client;

import me.hollow.realth.api.mixin.accessors.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.client.entity.*;
import me.hollow.realth.*;
import org.spongepowered.asm.mixin.injection.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.hollow.realth.client.modules.misc.*;
import org.lwjgl.opengl.*;
import me.hollow.realth.client.events.*;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft implements IMinecraft
{
    @Accessor("timer")
    public abstract Timer getTimer();
    
    @Accessor
    public abstract void setRightClickDelayTimer(final int p0);
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    public boolean handActiveRedirect(final EntityPlayerSP entityPlayerSP) {
        final InteractEvent interactEvent = new InteractEvent();
        return interactEvent.isCancelled();
    }
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE) })
    private void onKeyboard(final CallbackInfo callbackInfo) {
        if (Keyboard.getEventKeyState()) {
            JordoHack.INSTANCE.getEventManager().fireEvent((Object)new KeyEvent((Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey()));
        }
    }
    
    @Inject(method = { "runTickMouse" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButton()I", ordinal = 0, shift = At.Shift.BEFORE) })
    private void mouseClick(final CallbackInfo ci) {
        if (Mouse.getEventButtonState()) {
            MiddleClick.getInstance().run(Mouse.getEventButton());
        }
    }
    
    @Inject(method = { "getLimitFramerate" }, at = { @At("HEAD") }, cancellable = true)
    public void limitFps(final CallbackInfoReturnable<Integer> cir) {
        if (FPSLimit.getInstance().isEnabled() && !Display.isActive()) {
            try {
                cir.setReturnValue((Object)FPSLimit.getInstance().limit.getValue());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Inject(method = { "init" }, at = { @At("RETURN") })
    public void init(final CallbackInfo ci) {
        JordoHack.INSTANCE.init();
    }
    
    @Inject(method = { "shutdown" }, at = { @At("HEAD") })
    public void shutdown(final CallbackInfo ci) {
        JordoHack.INSTANCE.getConfigManager().saveConfig(JordoHack.INSTANCE.getConfigManager().config.replaceFirst("TrollGod/", ""));
        JordoHack.INSTANCE.getFriendManager().unload();
    }
    
    @Inject(method = { "runTick" }, at = { @At("HEAD") })
    public void onTick(final CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)new TickEvent(0));
    }
    
    @Inject(method = { "runTick" }, at = { @At("RETURN") })
    public void onTickPost(final CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent((Object)new TickEvent(1));
    }
}
