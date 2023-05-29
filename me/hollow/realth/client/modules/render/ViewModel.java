/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.render;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;

@ModuleManifest(label="ViewModelChanger", category=Module.Category.RENDER, listen=false)
public class ViewModel
extends Module {
    public final Setting<Float> size = this.register(new Setting<Float>("Size", Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(15.0f)));
    public final Setting<Float> offsetX = this.register(new Setting<Float>("OffsetX", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offsetY = this.register(new Setting<Float>("OffsetY", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offsetZ = this.register(new Setting<Float>("OffsetZ", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offhandX = this.register(new Setting<Float>("OffhandX", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offhandY = this.register(new Setting<Float>("OffhandY", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offhandZ = this.register(new Setting<Float>("OffhandZ", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    private static ViewModel INSTANCE = new ViewModel();

    public ViewModel() {
        INSTANCE = this;
    }

    public static ViewModel getInstance() {
        return INSTANCE;
    }
}

