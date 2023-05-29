/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.render;

import java.awt.Color;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;

@ModuleManifest(label="GlintModify", category=Module.Category.RENDER, listen=false)
public class GlintModify
extends Module {
    public final Setting<Integer> red = this.register(new Setting<Object>("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    public final Setting<Integer> green = this.register(new Setting<Object>("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    public final Setting<Integer> blue = this.register(new Setting<Object>("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    private static GlintModify INSTANCE = new GlintModify();

    public GlintModify() {
        INSTANCE = this;
    }

    public static GlintModify getINSTANCE() {
        return INSTANCE;
    }

    public int getColor() {
        return new Color(new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue()).getRGB()).getRGB();
    }
}

