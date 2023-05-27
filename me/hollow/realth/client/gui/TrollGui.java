//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.gui;

import net.minecraft.client.gui.*;
import me.hollow.realth.client.gui.components.*;
import me.hollow.realth.client.modules.*;
import me.hollow.realth.*;
import me.hollow.realth.client.gui.components.items.buttons.*;
import me.hollow.realth.client.gui.components.items.*;
import java.util.function.*;
import org.lwjgl.input.*;
import java.util.*;
import java.io.*;

public class TrollGui extends GuiScreen
{
    private final ArrayList<Component> components;
    private static TrollGui INSTANCE;
    
    public TrollGui() {
        this.components = new ArrayList<Component>();
        (TrollGui.INSTANCE = this).load();
    }
    
    public static TrollGui getInstance() {
        if (TrollGui.INSTANCE == null) {
            TrollGui.INSTANCE = new TrollGui();
        }
        return TrollGui.INSTANCE;
    }
    
    public static TrollGui getClickGui() {
        return getInstance();
    }
    
    private void load() {
        int x = -100;
        for (final Module.Category category : Module.Category.values()) {
            final ArrayList<Component> components2 = this.components;
            final String label = category.getLabel();
            x += 102;
            components2.add(new Component(label, x, 4, true) {
                public void setupItems() {
                    JordoHack.INSTANCE.getModuleManager().getModulesByCategory(category).forEach(module -> this.addButton((Button)new ModuleButton(module)));
                }
            });
        }
        this.components.forEach(components -> components.getItems().sort(Comparator.comparing((Function<? super E, ? extends Comparable>)Item::getName)));
    }
    
    public void updateModule(final Module module) {
        for (int i = 0; i < this.components.size(); ++i) {
            final ArrayList<Item> items = (ArrayList<Item>)this.components.get(i).getItems();
            for (int j = 0; j < items.size(); ++j) {
                final Item item = items.get(j);
                if (item instanceof ModuleButton) {
                    final ModuleButton button = (ModuleButton)item;
                    final Module mod = button.getModule();
                    if (module != null) {
                        if (module.equals(mod)) {
                            button.initSettings();
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.checkMouseWheel();
        this.drawDefaultBackground();
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public final ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.components.forEach(component -> component.setY(component.getY() - 10));
        }
        else if (dWheel > 0) {
            this.components.forEach(component -> component.setY(component.getY() + 10));
        }
    }
    
    public int getTextOffset() {
        return -6;
    }
    
    public Component getComponentByName(final String name) {
        for (final Component component : this.components) {
            if (!component.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return component;
        }
        return null;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
    }
    
    static {
        TrollGui.INSTANCE = new TrollGui();
    }
}
