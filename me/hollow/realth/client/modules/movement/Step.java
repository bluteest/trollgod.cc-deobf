//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.movement;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;

@ModuleManifest(label = "Step", category = Module.Category.MOVEMENT)
public class Step extends Module
{
    private final Setting<Boolean> placeHolder;
    
    public Step() {
        this.placeHolder = (Setting<Boolean>)this.register(new Setting("Dummy", (Object)true));
    }
    
    public void onEnable() {
        if (!(boolean)this.placeHolder.getValue()) {
            Step.mc.player.stepHeight = 2.0f;
        }
    }
    
    public void onDisable() {
        if (!(boolean)this.placeHolder.getValue()) {
            Step.mc.player.stepHeight = 0.6f;
        }
    }
}
