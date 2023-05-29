/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.other.event.EntityViewRenderEvent$FogColors
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.hollow.realth.client.modules.render;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleManifest(label="CustomFog", category=Module.Category.RENDER, listen=false)
public class CustomFog
extends Module {
    public final Setting<Float> red = this.register(new Setting<Float>("Red", Float.valueOf(255.0f), Float.valueOf(0.0f), Float.valueOf(255.0f)));
    public final Setting<Float> green = this.register(new Setting<Float>("Green", Float.valueOf(255.0f), Float.valueOf(0.0f), Float.valueOf(255.0f)));
    public final Setting<Float> blue = this.register(new Setting<Float>("Blue", Float.valueOf(255.0f), Float.valueOf(0.0f), Float.valueOf(255.0f)));

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void setFogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed(this.red.getValue().floatValue() / 255.0f);
        event.setGreen(this.green.getValue().floatValue() / 255.0f);
        event.setBlue(this.blue.getValue().floatValue() / 255.0f);
    }
}

