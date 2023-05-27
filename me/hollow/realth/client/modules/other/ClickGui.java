//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.other;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.client.gui.*;
import net.minecraft.client.settings.*;
import net.b0at.api.event.*;
import net.minecraft.client.gui.*;

@ModuleManifest(label = "ClickGui", category = Module.Category.OTHER, key = 157)
public class ClickGui extends Module
{
    public final Setting<String> prefix;
    public final Setting<Boolean> customFov;
    public final Setting<Float> fov;
    public final Setting<Integer> headerRed;
    public final Setting<Integer> headerGreen;
    public final Setting<Integer> headerBlue;
    public final Setting<Integer> headerAlpha;
    public final Setting<Integer> backRed;
    public final Setting<Integer> backGreen;
    public final Setting<Integer> backBlue;
    public final Setting<Integer> backAlpha;
    public final Setting<Integer> disableRed;
    public final Setting<Integer> disableGreen;
    public final Setting<Integer> disableBlue;
    public final Setting<Integer> disableAlpha;
    public final Setting<Integer> enableRed;
    public final Setting<Integer> enableGreen;
    public final Setting<Integer> enableBlue;
    public final Setting<Integer> enableAlpha;
    public final Setting<Integer> textEnableRed;
    public final Setting<Integer> textEnableGreen;
    public final Setting<Integer> textEnableBlue;
    public final Setting<Integer> textDisableRed;
    public final Setting<Integer> textDisableGreen;
    public final Setting<Integer> textDisableBlue;
    public final Setting<Integer> hoverAlpha;
    private static ClickGui INSTANCE;
    
    public ClickGui() {
        this.prefix = (Setting<String>)this.register(new Setting("Prefix", (Object)"-"));
        this.customFov = (Setting<Boolean>)this.register(new Setting("CustomFov", (Object)false));
        this.fov = (Setting<Float>)this.register(new Setting("Fov", (Object)150.0f, (Object)(-180.0f), (Object)180.0f));
        this.headerRed = (Setting<Integer>)this.register(new Setting("HeaderRed", (Object)255, (Object)0, (Object)255));
        this.headerGreen = (Setting<Integer>)this.register(new Setting("HeaderGreen", (Object)255, (Object)0, (Object)255));
        this.headerBlue = (Setting<Integer>)this.register(new Setting("HeaderBlue", (Object)255, (Object)0, (Object)255));
        this.headerAlpha = (Setting<Integer>)this.register(new Setting("HeaderAlpha", (Object)255, (Object)0, (Object)255));
        this.backRed = (Setting<Integer>)this.register(new Setting("BackgroundRed", (Object)228, (Object)0, (Object)255));
        this.backGreen = (Setting<Integer>)this.register(new Setting("BackgroundGreen", (Object)171, (Object)0, (Object)255));
        this.backBlue = (Setting<Integer>)this.register(new Setting("BackgroundBlue", (Object)181, (Object)0, (Object)255));
        this.backAlpha = (Setting<Integer>)this.register(new Setting("BackgroundAlpha", (Object)255, (Object)0, (Object)255));
        this.disableRed = (Setting<Integer>)this.register(new Setting("DisabledRed", (Object)255, (Object)0, (Object)255));
        this.disableGreen = (Setting<Integer>)this.register(new Setting("DisabledGreen", (Object)191, (Object)0, (Object)255));
        this.disableBlue = (Setting<Integer>)this.register(new Setting("DisabledBlue", (Object)202, (Object)0, (Object)255));
        this.disableAlpha = (Setting<Integer>)this.register(new Setting("DisabledAlpha", (Object)255, (Object)0, (Object)255));
        this.enableRed = (Setting<Integer>)this.register(new Setting("EnabledRed", (Object)255, (Object)0, (Object)255));
        this.enableGreen = (Setting<Integer>)this.register(new Setting("EnabledGreen", (Object)32, (Object)0, (Object)255));
        this.enableBlue = (Setting<Integer>)this.register(new Setting("EnabledBlue", (Object)32, (Object)0, (Object)255));
        this.enableAlpha = (Setting<Integer>)this.register(new Setting("EnabledAlpha", (Object)255, (Object)0, (Object)255));
        this.textEnableRed = (Setting<Integer>)this.register(new Setting("TextEnabledRed", (Object)0, (Object)0, (Object)255));
        this.textEnableGreen = (Setting<Integer>)this.register(new Setting("TextEnabledGreen", (Object)0, (Object)0, (Object)255));
        this.textEnableBlue = (Setting<Integer>)this.register(new Setting("TextEnabledBlue", (Object)0, (Object)0, (Object)255));
        this.textDisableRed = (Setting<Integer>)this.register(new Setting("TextDisabledRed", (Object)0, (Object)0, (Object)255));
        this.textDisableGreen = (Setting<Integer>)this.register(new Setting("TextDisabledGreen", (Object)0, (Object)0, (Object)255));
        this.textDisableBlue = (Setting<Integer>)this.register(new Setting("TextDisabledBlue", (Object)0, (Object)0, (Object)255));
        this.hoverAlpha = (Setting<Integer>)this.register(new Setting("HoverAlpha", (Object)180, (Object)0, (Object)255));
        ClickGui.INSTANCE = this;
    }
    
    public static ClickGui getInstance() {
        if (ClickGui.INSTANCE == null) {
            ClickGui.INSTANCE = new ClickGui();
        }
        return ClickGui.INSTANCE;
    }
    
    @EventHandler
    public void onTick(final TickEvent event) {
        if (event.getStage() == 0 || ClickGui.mc.player == null || ClickGui.mc.world == null) {
            return;
        }
        if (!(ClickGui.mc.currentScreen instanceof TrollGui)) {
            this.setEnabled(false);
        }
        if (this.customFov.getValue()) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, (float)this.fov.getValue());
        }
    }
    
    public void onEnable() {
        if (ClickGui.mc.player != null) {
            ClickGui.mc.displayGuiScreen((GuiScreen)new TrollGui());
        }
    }
    
    public void onDisable() {
        if (ClickGui.mc.currentScreen instanceof TrollGui) {
            ClickGui.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    static {
        ClickGui.INSTANCE = new ClickGui();
    }
}
