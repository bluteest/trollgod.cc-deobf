//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.other;

import java.awt.Color;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.ColorUtil;
import me.hollow.realth.client.events.TickEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;

@ModuleManifest(label="Colours", category=Module.Category.OTHER, persistent=true, enabled=true)
public class Colours
extends Module {
    public final Setting<Integer> red = this.register(new Setting<Integer>("Red", 255, 0, 255));
    public final Setting<Integer> green = this.register(new Setting<Integer>("Green", 255, 0, 255));
    public final Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 255, 0, 255));
    public final Setting<Boolean> rainbow = this.register(new Setting<Boolean>("Rainbow", false));
    public final Setting<Integer> factor = this.register(new Setting<Object>("Factor", Integer.valueOf(100), Integer.valueOf(1), Integer.valueOf(200), v -> this.rainbow.getValue()));
    public final Setting<Float> saturation = this.register(new Setting<Object>("Saturation", Float.valueOf(255.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> this.rainbow.getValue()));
    public final Setting<Integer> speed = this.register(new Setting<Object>("Speed", Integer.valueOf(5000), Integer.valueOf(1), Integer.valueOf(10000), v -> this.rainbow.getValue()));
    private static Colours INSTANCE = new Colours();

    public Colours() {
        INSTANCE = this;
    }

    @EventHandler
    public void onTick(TickEvent event) {
        if (event.getStage() == 0 || this.mc.player == null || this.mc.world == null) {
            return;
        }
        Color color = this.rainbow.getValue() != false ? new Color(ColorUtil.getRainbow(this.speed.getValue(), 0, this.saturation.getValue().floatValue() / 255.0f)) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue());
        JordoHack.INSTANCE.getColorManager().setColor(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }

    public static Colours getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Colours();
        }
        return INSTANCE;
    }

    @EventHandler
    public void onDisable() {
        this.setEnabled(true);
    }
}

