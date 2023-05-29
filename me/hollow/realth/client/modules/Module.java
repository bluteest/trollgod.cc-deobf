//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.other.Minecraft
 */
package me.hollow.realth.client.modules;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.Util;
import me.hollow.realth.api.property.Bind;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.MessageUtil;
import me.hollow.realth.client.events.MoveEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import me.hollow.realth.client.gui.TrollGui;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.client.modules.misc.Announcer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.*;

public class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private List<Setting> settings = new ArrayList<Setting>();
    public final Setting<Boolean> drawn = this.register(new Setting<Boolean>("Drawn", true));
    public final Setting<Bind> bind = this.register(new Setting<Bind>("Bind", new Bind(-1)));
    public final Setting<Boolean> enabled = this.register(new Setting<Boolean>("Enabled", false));
    private String label;
    private String suffix = "";
    private Category category;
    private boolean persistent;
    private boolean listenable = true;

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleManifest.class)) {
            ModuleManifest moduleManifest = this.getClass().getAnnotation(ModuleManifest.class);
            this.label = moduleManifest.label();
            this.category = moduleManifest.category();
            this.bind.setValue(new Bind(moduleManifest.key()));
            this.persistent = moduleManifest.persistent();
            this.listenable = moduleManifest.listen();
        }
    }

    public Setting register(Setting setting) {
        setting.setModule(this);
        this.settings.add(setting);
        if (this.mc.currentScreen instanceof TrollGui) {
            TrollGui.getInstance().updateModule(this);
        }
        return setting;
    }

    public final List<Setting> getSettings() {
        return this.settings;
    }

    public Setting getSettingByName(String name) {
        Setting setting = null;
        for (Setting set : this.settings) {
            if (!set.getName().equalsIgnoreCase(name)) continue;
            setting = set;
        }
        return setting;
    }

    public void clearSettings() {
        this.settings = new ArrayList<Setting>();
    }

    public void setEnabled(boolean enabled){
        this.enabled.setValue(enabled);
        this.onToggle();
        if (enabled) {
            if (this.listenable) {

                JordoHack.INSTANCE.getEventManager().registerListener(this);

            }
            this.onEnable();
            MessageUtil.sendClientMessage((Object)ChatFormatting.DARK_AQUA + this.getLabel() + (Object)ChatFormatting.LIGHT_PURPLE + " was" + (Object)ChatFormatting.GREEN + " enabled", -44444);
        } else {
            if (this.listenable) {
                JordoHack.INSTANCE.getEventManager().deregisterListener(this);
            }
            this.onDisable();
            MessageUtil.sendClientMessage((Object)ChatFormatting.DARK_AQUA + this.getLabel() + (Object)ChatFormatting.LIGHT_PURPLE + " was" + (Object)ChatFormatting.RED + " disabled", -44444);
        }
    }


    public void setDrawn(boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public void toggle() {
        this.setEnabled(this.enabled.getValue() == false);
    }

    public void onRender3D() {
    }
    public void onTick()
    {

    }
    public void onFrame(final float partialTicks) {
    }
    public String getInfo()
    {
        return null;
    }

    public void onRender2D() {
    }

    public void onToggle() {
    }
    public void onDeath(EntityPlayer player) {

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
        return this.bind.getValue().getKey();
    }

    public final boolean isEnabled() {
        return this.enabled.getValue();
    }

    public final boolean isDisabled() {
        return !isEnabled();
    }

    public final boolean isHidden() {
        return this.drawn.getValue() == false;
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

    public void setSuffix(String suffix) {
        this.suffix = ChatFormatting.WHITE + "[" + suffix + "]";
    }

    public final String getSuffix() {
        return this.suffix;
    }

    public static enum Category {
        RENDER("Render", 5993528),
        COMBAT("Combat", 1727329),
        MISC("Misc", 10197416),
        MOVEMENT("Movement", 800649),
        PLAYER("Player", 10914965),
        OTHER("Other", 10660302);


        final int color;
        final String label;

        private Category(String label, int color) {
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
    public static boolean fullNullCheck() {
        return Module.mc.player == null || Module.mc.world == null;
    }
}

