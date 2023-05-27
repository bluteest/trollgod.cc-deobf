//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.other;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.api.util.*;
import java.awt.*;
import me.hollow.realth.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "Colours", category = Module.Category.OTHER, persistent = true, enabled = true)
public class Colours extends Module
{
    public final Setting<Integer> red;
    public final Setting<Integer> green;
    public final Setting<Integer> blue;
    public final Setting<Boolean> rainbow;
    public final Setting<Integer> factor;
    public final Setting<Float> saturation;
    public final Setting<Integer> speed;
    private static Colours INSTANCE;
    
    public Colours() {
        this.red = (Setting<Integer>)this.register(new Setting("Red", (Object)255, (Object)0, (Object)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (Object)255, (Object)0, (Object)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (Object)255, (Object)0, (Object)255));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", (Object)false));
        this.factor = (Setting<Integer>)this.register(new Setting("Factor", (Object)100, (Object)1, (Object)200, v -> (boolean)this.rainbow.getValue()));
        this.saturation = (Setting<Float>)this.register(new Setting("Saturation", (Object)255.0f, (Object)1.0f, (Object)255.0f, v -> (boolean)this.rainbow.getValue()));
        this.speed = (Setting<Integer>)this.register(new Setting("Speed", (Object)5000, (Object)1, (Object)10000, v -> (boolean)this.rainbow.getValue()));
        Colours.INSTANCE = this;
    }
    
    @EventHandler
    public void onTick(final TickEvent event) {
        if (event.getStage() == 0 || Colours.mc.player == null || Colours.mc.world == null) {
            return;
        }
        final Color color = this.rainbow.getValue() ? new Color(ColorUtil.getRainbow((int)this.speed.getValue(), 0, (float)this.saturation.getValue() / 255.0f)) : new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue());
        JordoHack.INSTANCE.getColorManager().setColor(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }
    
    public static Colours getInstance() {
        if (Colours.INSTANCE == null) {
            Colours.INSTANCE = new Colours();
        }
        return Colours.INSTANCE;
    }
    
    @EventHandler
    public void onDisable() {
        this.setEnabled(true);
    }
    
    static {
        Colours.INSTANCE = new Colours();
    }
}
