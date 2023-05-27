//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.movement;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.api.util.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "InstantSpeed", category = Module.Category.MOVEMENT)
public class InstantSpeed extends Module
{
    Setting<Boolean> noLiquid;
    
    public InstantSpeed() {
        this.noLiquid = (Setting<Boolean>)this.register(new Setting("NoLiquid", (Object)true));
    }
    
    public void onToggle() {
        if (fullNullCheck()) {
            return;
        }
    }
    
    @EventHandler
    public void onMove(final MoveEvent e) {
        if (this.isDisabled() || InstantSpeed.mc.player.isElytraFlying()) {
            return;
        }
        if (((boolean)this.noLiquid.getValue() && EntityUtil.isInLiquid()) || InstantSpeed.mc.player.capabilities.isFlying) {
            return;
        }
        MovementUtil.strafe(e, MovementUtil.getSpeed());
    }
}
