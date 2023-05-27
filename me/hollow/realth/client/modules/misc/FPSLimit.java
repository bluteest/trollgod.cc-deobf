//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;

@ModuleManifest(label = "FPSLimit", category = Category.MISC, listen = false)
public class FPSLimit extends Module
{
    public final Setting<Integer> limit;
    private static FPSLimit INSTANCE;
    
    public FPSLimit() {
        this.limit = (Setting<Integer>)this.register(new Setting("Limit", (Object)60, (Object)1, (Object)240));
        FPSLimit.INSTANCE = this;
    }
    
    public static FPSLimit getInstance() {
        return FPSLimit.INSTANCE;
    }
    
    static {
        FPSLimit.INSTANCE = new FPSLimit();
    }
}
