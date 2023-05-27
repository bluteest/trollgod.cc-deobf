//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import java.awt.*;

@ModuleManifest(label = "GlintModify", category = Module.Category.RENDER, listen = false)
public class GlintModify extends Module
{
    public final Setting<Integer> red;
    public final Setting<Integer> green;
    public final Setting<Integer> blue;
    private static GlintModify INSTANCE;
    
    public GlintModify() {
        this.red = (Setting<Integer>)this.register(new Setting("Red", (Object)255, (Object)0, (Object)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (Object)255, (Object)0, (Object)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (Object)255, (Object)0, (Object)255));
        GlintModify.INSTANCE = this;
    }
    
    public static GlintModify getINSTANCE() {
        return GlintModify.INSTANCE;
    }
    
    public int getColor() {
        return new Color(new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue()).getRGB()).getRGB();
    }
    
    static {
        GlintModify.INSTANCE = new GlintModify();
    }
}
