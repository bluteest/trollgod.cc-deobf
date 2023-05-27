//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;

@ModuleManifest(label = "Fullbright", category = Module.Category.RENDER, listen = false)
public class Fullbright extends Module
{
    public static final Fullbright INSTANCE;
    private float oldGamma;
    
    public Fullbright() {
        this.oldGamma = -1.0f;
    }
    
    public void onDisable() {
        if (this.oldGamma != -1.0f) {
            Fullbright.mc.gameSettings.gammaSetting = this.oldGamma;
            Fullbright.mc.renderGlobal.loadRenderers();
        }
    }
    
    public void onEnable() {
        this.oldGamma = Fullbright.mc.gameSettings.gammaSetting;
        Fullbright.mc.gameSettings.gammaSetting = 100.0f;
    }
    
    static {
        INSTANCE = new Fullbright();
    }
}
