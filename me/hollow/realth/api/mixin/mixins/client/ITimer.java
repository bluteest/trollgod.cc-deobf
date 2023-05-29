/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Timer
 */
package me.hollow.realth.api.mixin.mixins.client;

import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface ITimer {
    @Accessor("tickLength")
    float getTickLength();

    @Accessor("tickLength")
    void setTickLength(float tickLength);
}

