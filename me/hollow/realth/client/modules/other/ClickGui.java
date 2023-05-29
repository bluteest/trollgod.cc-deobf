//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.gui.GuiScreen
 *  net.minecraft.other.settings.GameSettings$Options
 */
package me.hollow.realth.client.modules.other;

import java.awt.Color;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.TickEvent;
import me.hollow.realth.client.gui.TrollGui;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;

@ModuleManifest(label="ClickGui", category=Module.Category.OTHER, key=157)
public class ClickGui
extends Module {
    public final Setting<String> prefix = this.register(new Setting<String>("Prefix", "-"));
    public final Setting<Boolean> customFov = this.register(new Setting<Boolean>("CustomFov", false));
    public final Setting<Float> fov = this.register(new Setting<Float>("Fov", Float.valueOf(150.0f), Float.valueOf(-180.0f), Float.valueOf(180.0f)));
    public final Setting<Integer> headerRed = this.register(new Setting<Integer>("HeaderRed", 255, 0, 255));
    public final Setting<Integer> headerGreen = this.register(new Setting<Integer>("HeaderGreen", 255, 0, 255));
    public final Setting<Integer> headerBlue = this.register(new Setting<Integer>("HeaderBlue", 255, 0, 255));
    public final Setting<Integer> headerAlpha = this.register(new Setting<Integer>("HeaderAlpha", 255, 0, 255));
    public final Setting<Integer> backRed = this.register(new Setting<Integer>("BackgroundRed", 228, 0, 255));
    public final Setting<Integer> backGreen = this.register(new Setting<Integer>("BackgroundGreen", 171, 0, 255));
    public final Setting<Integer> backBlue = this.register(new Setting<Integer>("BackgroundBlue", 181, 0, 255));
    public final Setting<Integer> backAlpha = this.register(new Setting<Integer>("BackgroundAlpha", 255, 0, 255));
    public final Setting<Integer> disableRed = this.register(new Setting<Integer>("DisabledRed", 255, 0, 255));
    public final Setting<Integer> disableGreen = this.register(new Setting<Integer>("DisabledGreen", 191, 0, 255));
    public final Setting<Integer> disableBlue = this.register(new Setting<Integer>("DisabledBlue", 202, 0, 255));
    public final Setting<Integer> disableAlpha = this.register(new Setting<Integer>("DisabledAlpha", 255, 0, 255));
    public final Setting<Integer> enableRed = this.register(new Setting<Integer>("EnabledRed", 255, 0, 255));
    public final Setting<Integer> enableGreen = this.register(new Setting<Integer>("EnabledGreen", 32, 0, 255));
    public final Setting<Integer> enableBlue = this.register(new Setting<Integer>("EnabledBlue", 32, 0, 255));
    public final Setting<Integer> enableAlpha = this.register(new Setting<Integer>("EnabledAlpha", 255, 0, 255));
    public final Setting<Integer> textEnableRed = this.register(new Setting<Integer>("TextEnabledRed", 0, 0, 255));
    public final Setting<Integer> textEnableGreen = this.register(new Setting<Integer>("TextEnabledGreen", 0, 0, 255));
    public final Setting<Integer> textEnableBlue = this.register(new Setting<Integer>("TextEnabledBlue", 0, 0, 255));
    public final Setting<Integer> textDisableRed = this.register(new Setting<Integer>("TextDisabledRed", 0, 0, 255));
    public final Setting<Integer> textDisableGreen = this.register(new Setting<Integer>("TextDisabledGreen", 0, 0, 255));
    public final Setting<Integer> textDisableBlue = this.register(new Setting<Integer>("TextDisabledBlue", 0, 0, 255));
    public final Setting<Integer> hoverAlpha = this.register(new Setting<Integer>("HoverAlpha", 180, 0, 255));
    private static ClickGui INSTANCE = new ClickGui();

    public ClickGui() {
        INSTANCE = this;
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (event.getStage() == 0 || this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (!(this.mc.currentScreen instanceof TrollGui)) {
            this.setEnabled(false);
        }
        if (this.customFov.getValue().booleanValue()) {
            this.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, this.fov.getValue().floatValue());
        }
    }

    @Override
    public void onEnable() {
        if (this.mc.player != null) {
            this.mc.displayGuiScreen((GuiScreen)new TrollGui());
        }
    }

    @Override
    public void onDisable() {
        if (this.mc.currentScreen instanceof TrollGui) {
            this.mc.displayGuiScreen(null);
        }
    }
}

