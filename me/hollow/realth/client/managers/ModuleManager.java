//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.client.modules.*;
import java.util.*;
import me.hollow.realth.client.modules.other.*;
import me.hollow.realth.client.modules.combat.*;
import me.hollow.realth.client.modules.misc.*;
import me.hollow.realth.client.modules.movement.*;
import me.hollow.realth.client.modules.player.*;
import me.hollow.realth.client.modules.render.*;
import me.hollow.realth.*;
import me.hollow.realth.client.events.*;
import net.b0at.api.event.*;

public class ModuleManager
{
    private final List<Module> modules;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
    }
    
    public void init() {
        this.register(new ClickGui());
        this.register(new Colours());
        this.register(new Blocks());
        this.register(new HUD());
        this.register(new AutoArmour());
        this.register(new Criticals());
        this.register(new HoleFiller());
        this.register(new InstantFill());
        this.register(new Offhand());
        this.register(new AutoFeetPlace());
        this.register(new WallClip());
        this.register(new Announcer());
        this.register(new AutoReply());
        this.register(new ExtraTab());
        this.register(new FPSLimit());
        this.register(new MiddleClick());
        this.register(new PopCounter());
        this.register(new PvPInfo());
        this.register(new Speed());
        this.register(new Step());
        this.register(new Velocity());
        this.register(new Sprint());
        this.register(new InstantSpeed());
        this.register(new AutoStackFill());
        this.register(new FakePlayer());
        this.register(new FastPlace());
        this.register(new FastDrop());
        this.register(new FastBreak());
        this.register(new AutoCity());
        this.register(new ChestSwap());
        this.register(new BetterChat());
        this.register(new BlockHighlight());
        this.register(new EntityESP());
        this.register(new Fullbright());
        this.register(new GlintModify());
        this.register(new HoleESPNew());
        this.register(new HoleESP());
        this.register(new Nametags());
        this.register(new NoRender());
        this.register(new CustomFog());
        this.register(new ShulkerViewer());
        this.register(new Skeleton());
        this.register(new Starlink());
        this.register(new ViewModel());
        this.register(new InventoryViewer());
        JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
        this.modules.forEach(Module::onLoad);
    }
    
    @EventHandler
    public void onKey(final KeyEvent event) {
        this.modules.forEach(m -> {
            if (m.getKey() == event.getKey()) {
                m.toggle();
            }
        });
    }
    
    public void onRender2D() {
        this.modules.forEach(m -> {
            if (m.isEnabled()) {
                m.onRender2D();
            }
        });
    }
    
    public void onRender3D() {
        this.modules.forEach(m -> {
            if (m.isEnabled()) {
                m.onRender3D();
            }
        });
    }
    
    private void register(final Module module) {
        this.modules.add(module);
    }
    
    public final List<Module> getModules() {
        return this.modules;
    }
    
    public final Module getModuleByClass(final Class<?> clazz) {
        Module module = null;
        for (int i = 0; i < this.modules.size(); ++i) {
            final Module m = this.modules.get(i);
            if (m.getClass() == clazz) {
                module = m;
            }
        }
        return module;
    }
    
    public final List<Module> getModulesByCategory(final Module.Category category) {
        final ArrayList<Module> list = new ArrayList<Module>();
        final ArrayList<Module> list2;
        this.modules.forEach(module -> {
            if (module.getCategory().equals(category)) {
                list2.add(module);
            }
            return;
        });
        return list;
    }
    
    public final Module getModuleByLabel(final String label) {
        Module module = null;
        for (int i = 0; i < this.modules.size(); ++i) {
            final Module m = this.modules.get(i);
            if (m.getLabel().equalsIgnoreCase(label)) {
                module = m;
            }
        }
        return module;
    }
}
