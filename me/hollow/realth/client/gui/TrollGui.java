//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.gui.GuiScreen
 *  org.lwjgl.input.Mouse
 */
package me.hollow.realth.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import me.hollow.realth.JordoHack;
import me.hollow.realth.client.gui.components.Component;
import me.hollow.realth.client.gui.components.items.Item;
import me.hollow.realth.client.gui.components.items.buttons.ModuleButton;
import me.hollow.realth.client.modules.Module;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

public class TrollGui
extends GuiScreen {
    private final ArrayList<Component> components = new ArrayList();
    private static TrollGui INSTANCE = new TrollGui();

    public TrollGui() {
        INSTANCE = this;
        this.load();
    }

    public static TrollGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TrollGui();
        }
        return INSTANCE;
    }

    public static TrollGui getClickGui() {
        return TrollGui.getInstance();
    }

    private void load() {
        int x = -100;
        for (final Module.Category category : Module.Category.values()) {
            this.components.add(new Component(category.getLabel(), x += 102, 4, true){

                @Override
                public void setupItems() {
                    JordoHack.INSTANCE.getModuleManager().getModulesByCategory(category).forEach(module -> this.addButton(new ModuleButton((Module)module)));
                }
            });
        }
        this.components.forEach(components -> components.getItems().sort(Comparator.comparing(Item::getName)));
    }

    public void updateModule(Module module) {
        block0: for (int i = 0; i < this.components.size(); ++i) {
            ArrayList<Item> items = this.components.get(i).getItems();
            for (int j = 0; j < items.size(); ++j) {
                Item item = items.get(j);
                if (!(item instanceof ModuleButton)) continue;
                ModuleButton button = (ModuleButton)item;
                Module mod = button.getModule();
                if (module == null || !module.equals(mod)) continue;
                button.initSettings();
                continue block0;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.checkMouseWheel();
        this.drawDefaultBackground();
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
    }

    public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public final ArrayList<Component> getComponents() {
        return this.components;
    }

    public void checkMouseWheel() {
        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.components.forEach(component -> component.setY(component.getY() - 10));
        } else if (dWheel > 0) {
            this.components.forEach(component -> component.setY(component.getY() + 10));
        }
    }

    public int getTextOffset() {
        return -6;
    }

    public Component getComponentByName(String name) {
        for (Component component : this.components) {
            if (!component.getName().equalsIgnoreCase(name)) continue;
            return component;
        }
        return null;
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
    }
}

