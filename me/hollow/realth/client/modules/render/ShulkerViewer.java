//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;

@ModuleManifest(label = "ShulkerViewer", category = Module.Category.RENDER)
public class ShulkerViewer extends Module
{
    private static ShulkerViewer INSTANCE;
    
    public ShulkerViewer() {
        ShulkerViewer.INSTANCE = this;
    }
    
    public static ShulkerViewer getInstance() {
        return ShulkerViewer.INSTANCE;
    }
    
    static {
        ShulkerViewer.INSTANCE = new ShulkerViewer();
    }
}
