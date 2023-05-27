//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.*;

@ModuleManifest(label = "PopCounter", category = Category.MISC, listen = false)
public class PopCounter extends Module
{
    private static PopCounter INSTANCE;
    
    public PopCounter() {
        this.setInstance();
    }
    
    private void setInstance() {
        PopCounter.INSTANCE = this;
    }
    
    public static PopCounter getInstance() {
        if (PopCounter.INSTANCE == null) {
            PopCounter.INSTANCE = new PopCounter();
        }
        return PopCounter.INSTANCE;
    }
    
    static {
        PopCounter.INSTANCE = new PopCounter();
    }
}
