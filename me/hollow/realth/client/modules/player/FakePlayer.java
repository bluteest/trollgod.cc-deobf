//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.other.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 */
package me.hollow.realth.client.modules.player;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

@ModuleManifest(label="FakePlayer", category=Module.Category.PLAYER)
public class FakePlayer
extends Module {
    private EntityOtherPlayerMP entityOtherPlayerMP = null;
    public final Setting<Boolean> update = this.register(new Setting<Boolean>("Update", true));

    @Override
    public void onEnable() {
        if (this.mc.player == null) {
            return;
        }
        this.entityOtherPlayerMP = new EntityOtherPlayerMP((World)this.mc.world, new GameProfile(UUID.fromString("cc72ff00-a113-48f4-be18-2dda8db52355"), "whollow"));
        this.entityOtherPlayerMP.copyLocationAndAnglesFrom((Entity)this.mc.player);
        this.entityOtherPlayerMP.inventory = this.mc.player.inventory;
        this.mc.world.spawnEntity((Entity)this.entityOtherPlayerMP);
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.entityOtherPlayerMP != null && this.update.getValue().booleanValue()) {
            this.mc.world.updateEntityWithOptionalForce((Entity)this.entityOtherPlayerMP, true);
        }
    }

    @Override
    public void onDisable() {
        if (this.entityOtherPlayerMP != null) {
            this.mc.world.removeEntity((Entity)this.entityOtherPlayerMP);
        }
    }
}

