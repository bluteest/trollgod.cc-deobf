/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.managers;

import java.util.ArrayList;
import java.util.List;
import me.hollow.realth.JordoHack;
import me.hollow.realth.client.events.KeyEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.other.*;
import me.hollow.realth.client.modules.combat.*;
import me.hollow.realth.client.modules.misc.*;
import me.hollow.realth.client.modules.movement.*;
import me.hollow.realth.client.modules.player.*;
import me.hollow.realth.client.modules.render.*;
import net.b0at.api.event.EventHandler;
import scala.tools.reflect.quasiquotes.Holes;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<Module>();

    public void init() {
        // other
        this.register(new ClickGui());
        this.register(new Colours());
        this.register(new Blocks());
        this.register(new HUD());

        // combat
        this.register(new AutoArmour());
        this.register(new Criticals());
        this.register(new HoleFiller());
        this.register(new InstantFill());
        this.register(new Offhand());
        this.register(new AutoFeetPlace());
        this.register(new WallClip());

        // misc
        this.register(new Announcer());
        this.register(new AutoReply());
        this.register(new ExtraTab());
        this.register(new FPSLimit());
        this.register(new MiddleClick());
        this.register(new PopCounter());
        this.register(new PvPInfo());

        // movement
        this.register(new Speed());
        this.register(new Step());
        this.register(new Velocity());
        this.register(new Sprint());
        this.register(new InstantSpeed());

        // player
        this.register(new AutoStackFill());
        this.register(new FakePlayer());
        this.register(new FastPlace());
        this.register(new FastDrop());
        this.register(new FastBreak());
        this.register(new AutoCity());
        this.register(new ChestSwap());

        // render
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
        JordoHack.INSTANCE.getEventManager().registerListener(this);
        this.modules.forEach(Module::onLoad);
    }

    @EventHandler
    public void onKey(KeyEvent event) {
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

    private void register(Module module) {
        this.modules.add(module);
    }

    public final List<Module> getModules() {
        return this.modules;
    }

    public final Module getModuleByClass(Class<?> clazz) {
        Module module = null;
        for (int i = 0; i < this.modules.size(); ++i) {
            Module m = this.modules.get(i);
            if (m.getClass() != clazz) continue;
            module = m;
        }
        return module;
    }

    public final List<Module> getModulesByCategory(Module.Category category) {
        ArrayList<Module> list = new ArrayList<Module>();
        this.modules.forEach(module -> {
            if (module.getCategory().equals((Object)category)) {
                list.add((Module)module);
            }
        });
        return list;
    }

    public final Module getModuleByLabel(String label) {
        Module module = null;
        for (int i = 0; i < this.modules.size(); ++i) {
            Module m = this.modules.get(i);
            if (!m.getLabel().equalsIgnoreCase(label)) continue;
            module = m;
        }
        return module;
    }
}

