//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.hollow.realth.api.mixin.mixins.render.*;
import me.hollow.realth.api.util.*;
import net.minecraft.util.math.*;

@ModuleManifest(label = "EntityESP", category = Module.Category.RENDER, listen = false)
public class EntityESP extends Module
{
    public final Setting<Boolean> crystals;
    public final Setting<Boolean> bottles;
    public final Setting<Boolean> players;
    public final Setting<Boolean> sync;
    public final Setting<Integer> red;
    public final Setting<Integer> green;
    public final Setting<Integer> blue;
    private static EntityESP INSTANCE;
    
    public EntityESP() {
        this.crystals = (Setting<Boolean>)this.register(new Setting("Crystals", (Object)true));
        this.bottles = (Setting<Boolean>)this.register(new Setting("XPBottles", (Object)true));
        this.players = (Setting<Boolean>)this.register(new Setting("Players", (Object)true));
        this.sync = (Setting<Boolean>)this.register(new Setting("Sync", (Object)true));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (Object)255, (Object)0, (Object)255, v -> !(boolean)this.sync.getValue()));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (Object)255, (Object)0, (Object)255, v -> !(boolean)this.sync.getValue()));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (Object)255, (Object)0, (Object)255, v -> !(boolean)this.sync.getValue()));
        EntityESP.INSTANCE = this;
    }
    
    public static EntityESP getInstance() {
        return EntityESP.INSTANCE;
    }
    
    public int getColor() {
        return this.sync.getValue() ? JordoHack.INSTANCE.getColorManager().getColorAsInt() : new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue()).getRGB();
    }
    
    public void onRender3D() {
        if (EntityESP.mc.player == null || EntityESP.mc.world == null) {
            return;
        }
        for (int size = EntityESP.mc.world.loadedEntityList.size(), i = 0; i < size; ++i) {
            final Entity entity = EntityESP.mc.world.loadedEntityList.get(i);
            if (entity instanceof EntityExpBottle) {
                if (this.bottles.getValue()) {
                    final Vec3d vec = this.interpolateEntity(entity, EntityESP.mc.getRenderPartialTicks());
                    final double posX = vec.x - ((IRenderManager)EntityESP.mc.getRenderManager()).getRenderPosX();
                    final double posY = vec.y - ((IRenderManager)EntityESP.mc.getRenderManager()).getRenderPosY();
                    final double posZ = vec.z - ((IRenderManager)EntityESP.mc.getRenderManager()).getRenderPosZ();
                    RenderUtil.drawBBOutline(new AxisAlignedBB(0.0, 0.0, 0.0, (double)entity.width, (double)entity.height, (double)entity.width).offset(posX - entity.width / 2.0f, posY, posZ - entity.width / 2.0f).grow(0.125), ((boolean)this.sync.getValue()) ? new Color(JordoHack.INSTANCE.getColorManager().getColorAsInt()) : new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue()), 1.0f);
                }
            }
        }
    }
    
    private Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }
    
    static {
        EntityESP.INSTANCE = new EntityESP();
    }
}
