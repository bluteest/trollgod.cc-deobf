//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ RenderManager.class })
public interface IRenderManager
{
    @Accessor("renderPosX")
    double getRenderPosX();
    
    @Accessor("renderPosY")
    double getRenderPosY();
    
    @Accessor("renderPosZ")
    double getRenderPosZ();
}
