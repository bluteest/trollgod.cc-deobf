/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.misc;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;

@ModuleManifest(label="FPSLimit", category=Module.Category.MISC, listen=false)
public class FPSLimit
extends Module {
    public final Setting<Integer> limit = this.register(new Setting<Integer>("Limit", 60, 1, 240));
    private static FPSLimit INSTANCE = new FPSLimit();

    public FPSLimit() {
        INSTANCE = this;
    }

    public static FPSLimit getInstance() {
        return INSTANCE;
    }
}

