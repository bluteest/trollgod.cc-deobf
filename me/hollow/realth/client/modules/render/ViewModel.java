//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;

@ModuleManifest(label = "ViewModelChanger", category = Module.Category.RENDER, listen = false)
public class ViewModel extends Module
{
    public final Setting<Float> size;
    public final Setting<Float> offsetX;
    public final Setting<Float> offsetY;
    public final Setting<Float> offsetZ;
    public final Setting<Float> offhandX;
    public final Setting<Float> offhandY;
    public final Setting<Float> offhandZ;
    private static ViewModel INSTANCE;
    
    public ViewModel() {
        this.size = (Setting<Float>)this.register(new Setting("Size", (Object)10.0f, (Object)0.0f, (Object)15.0f));
        this.offsetX = (Setting<Float>)this.register(new Setting("OffsetX", (Object)0.0f, (Object)(-1.0f), (Object)1.0f));
        this.offsetY = (Setting<Float>)this.register(new Setting("OffsetY", (Object)0.0f, (Object)(-1.0f), (Object)1.0f));
        this.offsetZ = (Setting<Float>)this.register(new Setting("OffsetZ", (Object)0.0f, (Object)(-1.0f), (Object)1.0f));
        this.offhandX = (Setting<Float>)this.register(new Setting("OffhandX", (Object)0.0f, (Object)(-1.0f), (Object)1.0f));
        this.offhandY = (Setting<Float>)this.register(new Setting("OffhandY", (Object)0.0f, (Object)(-1.0f), (Object)1.0f));
        this.offhandZ = (Setting<Float>)this.register(new Setting("OffhandZ", (Object)0.0f, (Object)(-1.0f), (Object)1.0f));
        ViewModel.INSTANCE = this;
    }
    
    public static ViewModel getInstance() {
        return ViewModel.INSTANCE;
    }
    
    static {
        ViewModel.INSTANCE = new ViewModel();
    }
}
