/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;

@ModuleManifest(label="ShulkerViewer", category=Module.Category.RENDER)
public class ShulkerViewer
extends Module {
    private static ShulkerViewer INSTANCE = new ShulkerViewer();

    public ShulkerViewer() {
        INSTANCE = this;
    }

    public static ShulkerViewer getInstance() {
        return INSTANCE;
    }
}

