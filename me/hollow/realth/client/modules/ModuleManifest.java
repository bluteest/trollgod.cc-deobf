//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ModuleManifest {
    String label() default "";
    
    Module.Category category();
    
    int key() default 0;
    
    boolean persistent() default false;
    
    boolean enabled() default false;
    
    boolean listen() default true;
}
