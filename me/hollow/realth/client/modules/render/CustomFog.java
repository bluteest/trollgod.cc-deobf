//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@ModuleManifest(label = "CustomFog", category = Module.Category.RENDER, listen = false)
public class CustomFog extends Module
{
    public final Setting<Float> red;
    public final Setting<Float> green;
    public final Setting<Float> blue;
    
    public CustomFog() {
        this.red = (Setting<Float>)this.register(new Setting("Red", (Object)255.0f, (Object)0.0f, (Object)255.0f));
        this.green = (Setting<Float>)this.register(new Setting("Green", (Object)255.0f, (Object)0.0f, (Object)255.0f));
        this.blue = (Setting<Float>)this.register(new Setting("Blue", (Object)255.0f, (Object)0.0f, (Object)255.0f));
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void setFogColors(final EntityViewRenderEvent.FogColors event) {
        event.setRed((float)this.red.getValue() / 255.0f);
        event.setGreen((float)this.green.getValue() / 255.0f);
        event.setBlue((float)this.blue.getValue() / 255.0f);
    }
}
