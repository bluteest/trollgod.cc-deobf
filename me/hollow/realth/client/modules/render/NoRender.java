//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.entity.*;
import java.util.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "NoRender", category = Module.Category.RENDER)
public class NoRender extends Module
{
    public final Setting<Boolean> noBossOverlay;
    public final Setting<Boolean> boxedVines;
    public final Setting<Boolean> scoreboard;
    public final Setting<Boolean> totemAnimation;
    public final Setting<Boolean> potion;
    public final Setting<Boolean> armor;
    public final Setting<Boolean> helmet;
    public final Setting<Boolean> chestplate;
    public final Setting<Boolean> leggings;
    public final Setting<Boolean> boots;
    public final Setting<Boolean> glowing;
    private static NoRender INSTANCE;
    
    public NoRender() {
        this.noBossOverlay = (Setting<Boolean>)this.register(new Setting("BossBar", (Object)true));
        this.boxedVines = (Setting<Boolean>)this.register(new Setting("Vines", (Object)true));
        this.scoreboard = (Setting<Boolean>)this.register(new Setting("Scoreboard", (Object)false));
        this.totemAnimation = (Setting<Boolean>)this.register(new Setting("TotemAnimation", (Object)true));
        this.potion = (Setting<Boolean>)this.register(new Setting("Potions", (Object)false));
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", (Object)false));
        this.helmet = (Setting<Boolean>)this.register(new Setting("Helmet", (Object)true, v -> (boolean)this.armor.getValue()));
        this.chestplate = (Setting<Boolean>)this.register(new Setting("Chestplate", (Object)true, v -> (boolean)this.armor.getValue()));
        this.leggings = (Setting<Boolean>)this.register(new Setting("Leggings", (Object)true, v -> (boolean)this.armor.getValue()));
        this.boots = (Setting<Boolean>)this.register(new Setting("Boots", (Object)true, v -> (boolean)this.armor.getValue()));
        this.glowing = (Setting<Boolean>)this.register(new Setting("GlowingEntities", (Object)true));
        NoRender.INSTANCE = this;
    }
    
    public static NoRender getInstance() {
        return NoRender.INSTANCE;
    }
    
    @EventHandler
    public void onTick() {
        if (!(boolean)this.glowing.getValue()) {
            return;
        }
        for (final Entity entity : NoRender.mc.world.loadedEntityList) {
            if (!entity.isGlowing()) {
                continue;
            }
            entity.setGlowing(false);
        }
    }
    
    static {
        NoRender.INSTANCE = new NoRender();
    }
}
