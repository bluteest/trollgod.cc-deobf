/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.other;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;

@ModuleManifest(label="Blocks", category=Module.Category.OTHER, listen=false, enabled = true)
public class Blocks
extends Module {
    public final Setting<String> font = this.register(new Setting<String>("Font", "Verdana"));
    private static Blocks INSTANCE = new Blocks();

    public Blocks() {
        this.setInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static Blocks getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Blocks();
        }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        JordoHack.fontManager.setCustomFont(true);
    }

    @Override
    public void onDisable() {
        JordoHack.fontManager.setCustomFont(false);
    }
}

