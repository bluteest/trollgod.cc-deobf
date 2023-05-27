//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import net.minecraft.client.entity.*;
import me.hollow.realth.api.property.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import me.hollow.realth.client.events.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "FakePlayer", category = Module.Category.PLAYER)
public class FakePlayer extends Module
{
    private EntityOtherPlayerMP entityOtherPlayerMP;
    public final Setting<Boolean> update;
    
    public FakePlayer() {
        this.entityOtherPlayerMP = null;
        this.update = (Setting<Boolean>)this.register(new Setting("Update", (Object)true));
    }
    
    public void onEnable() {
        if (FakePlayer.mc.player == null) {
            return;
        }
        (this.entityOtherPlayerMP = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("cc72ff00-a113-48f4-be18-2dda8db52355"), "whollow"))).copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        this.entityOtherPlayerMP.inventory = FakePlayer.mc.player.inventory;
        FakePlayer.mc.world.spawnEntity((Entity)this.entityOtherPlayerMP);
    }
    
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        if (FakePlayer.mc.player == null || FakePlayer.mc.world == null) {
            return;
        }
        if (this.entityOtherPlayerMP != null && (boolean)this.update.getValue()) {
            FakePlayer.mc.world.updateEntityWithOptionalForce((Entity)this.entityOtherPlayerMP, true);
        }
    }
    
    public void onDisable() {
        if (this.entityOtherPlayerMP != null) {
            FakePlayer.mc.world.removeEntity((Entity)this.entityOtherPlayerMP);
        }
    }
}
