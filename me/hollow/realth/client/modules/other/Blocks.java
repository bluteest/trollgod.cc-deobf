//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.other;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.*;

@ModuleManifest(label = "Blocks", category = Module.Category.OTHER, listen = false, enabled = true)
public class Blocks extends Module
{
    public final Setting<String> font;
    private static Blocks INSTANCE;
    
    public Blocks() {
        this.font = (Setting<String>)this.register(new Setting("Font", (Object)"Verdana"));
        this.setInstance();
    }
    
    private void setInstance() {
        Blocks.INSTANCE = this;
    }
    
    public static Blocks getInstance() {
        if (Blocks.INSTANCE == null) {
            Blocks.INSTANCE = new Blocks();
        }
        return Blocks.INSTANCE;
    }
    
    public void onEnable() {
        JordoHack.fontManager.setCustomFont(true);
    }
    
    public void onDisable() {
        JordoHack.fontManager.setCustomFont(false);
    }
    
    static {
        Blocks.INSTANCE = new Blocks();
    }
}
