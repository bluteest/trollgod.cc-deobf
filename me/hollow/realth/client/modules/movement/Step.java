//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.movement;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;

@ModuleManifest(label="Step", category=Module.Category.MOVEMENT)
public class Step
extends Module {
    private final Setting<Boolean> placeHolder = this.register(new Setting<Boolean>("Dummy", true));

    @Override
    public void onEnable() {
        if (!this.placeHolder.getValue().booleanValue()) {
            this.mc.player.stepHeight = 2.0f;
        }
    }

    @Override
    public void onDisable() {
        if (!this.placeHolder.getValue().booleanValue()) {
            this.mc.player.stepHeight = 0.6f;
        }
    }
}

