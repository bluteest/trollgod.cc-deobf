/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.Minecraft
 *  net.minecraft.util.Timer
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 */
package me.hollow.realth.api.mixin.mixins.client;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.mixin.accessors.IMinecraft;
import me.hollow.realth.client.events.ClickBlockEvent;
import me.hollow.realth.client.events.KeyEvent;
import me.hollow.realth.client.events.TickEvent;
import me.hollow.realth.client.modules.misc.MiddleClick;
import me.hollow.realth.client.modules.misc.FPSLimit;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import me.hollow.realth.client.events.InteractEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Minecraft.class})
public abstract class MixinMinecraft
        implements IMinecraft {
    @Override
    @Accessor(value="timer")
    public abstract Timer getTimer();

    @Override
    @Accessor
    public abstract void setRightClickDelayTimer(int var1);

    @Redirect(method={"sendClickBlockToController"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    public boolean handActiveRedirect(EntityPlayerSP entityPlayerSP) {
        InteractEvent interactEvent = new InteractEvent();
        return interactEvent.isCancelled();
    }
    @Inject(method={"runTickKeyboard"}, at={@At(value="INVOKE", target="Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal=0, shift=At.Shift.BEFORE)})
    private void onKeyboard(CallbackInfo callbackInfo) {
        if (Keyboard.getEventKeyState()) {
            JordoHack.INSTANCE.getEventManager().fireEvent(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()));
        }
    }

    @Inject(method={"runTickMouse"}, at={@At(value="INVOKE", target="Lorg/lwjgl/input/Mouse;getEventButton()I", ordinal=0, shift=At.Shift.BEFORE)})
    private void mouseClick(CallbackInfo ci) {
        if (Mouse.getEventButtonState()) {
            MiddleClick.getInstance().run(Mouse.getEventButton());
        }
    }

    @Inject(method={"getLimitFramerate"}, at={@At(value="HEAD")}, cancellable=true)
    public void limitFps(CallbackInfoReturnable<Integer> cir) {
        if (FPSLimit.getInstance().isEnabled() && !Display.isActive()) {
            try {
                cir.setReturnValue(FPSLimit.getInstance().limit.getValue());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Inject(method={"init"}, at={@At(value="RETURN")})
    public void init(CallbackInfo ci) {
        JordoHack.INSTANCE.init();
    }

    @Inject(method={"shutdown"}, at={@At(value="HEAD")})
    public void shutdown(CallbackInfo ci) {
        JordoHack.INSTANCE.getConfigManager().saveConfig(JordoHack.INSTANCE.getConfigManager().config.replaceFirst("TrollGod/", ""));
        JordoHack.INSTANCE.getFriendManager().unload();
    }

    @Inject(method={"runTick"}, at={@At(value="HEAD")})
    public void onTick(CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent(new TickEvent(0));
    }

    @Inject(method={"runTick"}, at={@At(value="RETURN")})
    public void onTickPost(CallbackInfo ci) {
        JordoHack.INSTANCE.getEventManager().fireEvent(new TickEvent(1));
    }
}

