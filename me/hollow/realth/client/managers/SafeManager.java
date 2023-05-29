//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.hollow.realth.client.managers;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.Util;
import me.hollow.realth.api.util.EntityUtil;
import me.hollow.realth.client.events.TickEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;

public class SafeManager
implements Util {
    private boolean safe;

    @EventHandler
    public void onUpdate(TickEvent event) {
        if (SafeManager.mc.player == null || SafeManager.mc.world == null) {
            return;
        }
        this.safe = true;
        float maxDamage = 0.5f;
        for (int i = 0; i < SafeManager.mc.world.loadedEntityList.size(); ++i) {
            float damage;
            Entity entity = (Entity)SafeManager.mc.world.loadedEntityList.get(i);
            if (!(entity instanceof EntityEnderCrystal) || SafeManager.mc.player.getDistance(entity) > 12.0f || (damage = EntityUtil.calculate(entity.posX, entity.posY, entity.posZ, (EntityLivingBase)SafeManager.mc.player)) < maxDamage) continue;
            maxDamage = damage;
            if (damage + 0.5f < EntityUtil.getHealth((EntityPlayer)SafeManager.mc.player)) continue;
            this.safe = false;
        }
    }

    public boolean isSafe() {
        return this.safe;
    }

    public void load() {
        JordoHack.INSTANCE.getEventManager().registerListener(this);
    }

    public void unload() {
        JordoHack.INSTANCE.getEventManager().deregisterListener(this);
    }
}

