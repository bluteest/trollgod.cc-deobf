//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.client.events.*;
import net.minecraft.client.entity.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "FastDrop", category = Module.Category.PLAYER)
public class FastDrop extends Module
{
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        if (FastDrop.mc.player == null || FastDrop.mc.world == null || FastDrop.mc.player.isInWater() || FastDrop.mc.player.isInLava() || FastDrop.mc.player.isOnLadder()) {
            return;
        }
        if (FastDrop.mc.player.onGround) {
            final EntityPlayerSP player = FastDrop.mc.player;
            player.motionY -= 2.0;
        }
    }
}
