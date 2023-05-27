//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.api.*;
import me.hollow.realth.client.events.*;
import net.minecraft.entity.item.*;
import me.hollow.realth.api.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.b0at.api.event.*;
import me.hollow.realth.*;

public class SafeManager implements Util
{
    private boolean safe;
    
    @EventHandler
    public void onUpdate(final TickEvent event) {
        if (SafeManager.mc.player == null || SafeManager.mc.world == null) {
            return;
        }
        this.safe = true;
        float maxDamage = 0.5f;
        for (int i = 0; i < SafeManager.mc.world.loadedEntityList.size(); ++i) {
            final Entity entity = SafeManager.mc.world.loadedEntityList.get(i);
            if (entity instanceof EntityEnderCrystal && SafeManager.mc.player.getDistance(entity) <= 12.0f) {
                final float damage;
                if ((damage = EntityUtil.calculate(entity.posX, entity.posY, entity.posZ, (EntityLivingBase)SafeManager.mc.player)) >= maxDamage) {
                    maxDamage = damage;
                    if (damage + 0.5f >= EntityUtil.getHealth((EntityPlayer)SafeManager.mc.player)) {
                        this.safe = false;
                    }
                }
            }
        }
    }
    
    public boolean isSafe() {
        return this.safe;
    }
    
    public void load() {
        JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
    }
    
    public void unload() {
        JordoHack.INSTANCE.getEventManager().deregisterListener((Object)this);
    }
}
