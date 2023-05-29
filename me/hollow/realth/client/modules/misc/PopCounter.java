/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;

@ModuleManifest(label="PopCounter", category=Module.Category.MISC, listen=false)
public class PopCounter
extends Module {
    private static PopCounter INSTANCE = new PopCounter();

    public PopCounter() {
        this.setInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static PopCounter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PopCounter();
        }
        return INSTANCE;
    }
}

