//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules;

import net.minecraft.client.*;
import me.hollow.realth.api.property.*;
import java.lang.annotation.*;
import me.hollow.realth.client.gui.*;
import java.util.*;
import me.hollow.realth.*;
import com.mojang.realmsclient.gui.*;
import me.hollow.realth.api.util.*;
import net.minecraft.entity.player.*;

public class Module
{
    public static final Minecraft mc;
    private List<Setting> settings;
    public final Setting<Boolean> drawn;
    public final Setting<Bind> bind;
    public final Setting<Boolean> enabled;
    private String label;
    private String suffix;
    private Category category;
    private boolean persistent;
    private boolean listenable;
    
    public Module() {
        this.settings = new ArrayList<Setting>();
        this.drawn = (Setting<Boolean>)this.register(new Setting("Drawn", (Object)true));
        this.bind = (Setting<Bind>)this.register(new Setting("Bind", (Object)new Bind(-1)));
        this.enabled = (Setting<Boolean>)this.register(new Setting("Enabled", (Object)false));
        this.suffix = "";
        this.listenable = true;
        if (this.getClass().isAnnotationPresent(ModuleManifest.class)) {
            final ModuleManifest moduleManifest = this.getClass().getAnnotation(ModuleManifest.class);
            this.label = moduleManifest.label();
            this.category = moduleManifest.category();
            this.bind.setValue((Object)new Bind(moduleManifest.key()));
            this.persistent = moduleManifest.persistent();
            this.listenable = moduleManifest.listen();
        }
    }
    
    public Setting register(final Setting setting) {
        setting.setModule(this);
        this.settings.add(setting);
        if (Module.mc.currentScreen instanceof TrollGui) {
            TrollGui.getInstance().updateModule(this);
        }
        return setting;
    }
    
    public final List<Setting> getSettings() {
        return this.settings;
    }
    
    public Setting getSettingByName(final String name) {
        Setting setting = null;
        for (final Setting set : this.settings) {
            if (!set.getName().equalsIgnoreCase(name)) {
                continue;
            }
            setting = set;
        }
        return setting;
    }
    
    public void clearSettings() {
        this.settings = new ArrayList<Setting>();
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled.setValue((Object)enabled);
        this.onToggle();
        if (enabled) {
            if (this.listenable) {
                JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
            }
            this.onEnable();
            MessageUtil.sendClientMessage(ChatFormatting.DARK_AQUA + this.getLabel() + ChatFormatting.LIGHT_PURPLE + " was" + ChatFormatting.GREEN + " enabled", -44444);
        }
        else {
            if (this.listenable) {
                JordoHack.INSTANCE.getEventManager().deregisterListener((Object)this);
            }
            this.onDisable();
            MessageUtil.sendClientMessage(ChatFormatting.DARK_AQUA + this.getLabel() + ChatFormatting.LIGHT_PURPLE + " was" + ChatFormatting.RED + " disabled", -44444);
        }
    }
    
    public void setDrawn(final boolean drawn) {
        this.drawn.setValue((Object)drawn);
    }
    
    public void toggle() {
        this.setEnabled(!(boolean)this.enabled.getValue());
    }
    
    public void onRender3D() {
    }
    
    public void onTick() {
    }
    
    public void onFrame(final float partialTicks) {
    }
    
    public String getInfo() {
        return null;
    }
    
    public void onRender2D() {
    }
    
    public void onToggle() {
    }
    
    public void onDeath(final EntityPlayer player) {
    }
    
    public void onEnable() {
    }
    
    public void onUpdate() {
    }
    
    public void onDisable() {
    }
    
    public void onLoad() {
    }
    
    public final int getKey() {
        return ((Bind)this.bind.getValue()).getKey();
    }
    
    public final boolean isEnabled() {
        return (boolean)this.enabled.getValue();
    }
    
    public final boolean isDisabled() {
        return !this.isEnabled();
    }
    
    public final boolean isHidden() {
        return !(boolean)this.drawn.getValue();
    }
    
    public boolean isPersistent() {
        return this.persistent;
    }
    
    public final Category getCategory() {
        return this.category;
    }
    
    public final String getLabel() {
        return this.label;
    }
    
    public void clearSuffix() {
        this.suffix = "";
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = ChatFormatting.WHITE + "[" + suffix + "]";
    }
    
    public final String getSuffix() {
        return this.suffix;
    }
    
    public static boolean fullNullCheck() {
        return Module.mc.player == null || Module.mc.world == null;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public enum Category
    {
        RENDER("Render", 5993528), 
        COMBAT("Combat", 1727329), 
        MISC("Misc", 10197416), 
        MOVEMENT("Movement", 800649), 
        PLAYER("Player", 10914965), 
        OTHER("Other", 10660302);
        
        final int color;
        final String label;
        
        private Category(final String label, final int color) {
            this.color = color;
            this.label = label;
        }
        
        public final int getColor() {
            return this.color;
        }
        
        public final String getLabel() {
            return this.label;
        }
    }
}
