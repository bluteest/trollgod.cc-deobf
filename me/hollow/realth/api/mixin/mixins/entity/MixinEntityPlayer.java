/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.hollow.realth.api.mixin.mixins.entity;

import me.hollow.realth.api.mixin.mixins.entity.MixinEntity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={EntityPlayer.class})
public abstract class MixinEntityPlayer
extends MixinEntity {
}

