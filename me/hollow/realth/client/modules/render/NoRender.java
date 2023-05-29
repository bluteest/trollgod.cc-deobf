package me.hollow.realth.client.modules.render;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;

@ModuleManifest(label="NoRender", category=Module.Category.RENDER)
public class NoRender
        extends Module {
    public final Setting<Boolean> noBossOverlay = this.register(new Setting<Boolean>("BossBar", true));
    public final Setting<Boolean> boxedVines = this.register(new Setting<Boolean>("Vines", true));
    public final Setting<Boolean> scoreboard = register(new Setting<>("Scoreboard", false));
    public final Setting<Boolean> totemAnimation = register(new Setting<>("TotemAnimation", true));
    public final Setting<Boolean> potion = register(new Setting<>("Potions", false));
    public final Setting<Boolean> armor = register(new Setting<>("Armor", false));
    public final Setting<Boolean> helmet = register(new Setting<>("Helmet", true, v -> this.armor.getValue()));
    public final Setting<Boolean> chestplate = register(new Setting<>("Chestplate", true, v -> this.armor.getValue()));
    public final Setting<Boolean> leggings = register(new Setting<>("Leggings", true, v -> this.armor.getValue()));
    public final Setting<Boolean> boots = register(new Setting<>("Boots", true, v -> this.armor.getValue()));
    public final Setting<Boolean> glowing = register(new Setting<Boolean>("GlowingEntities", true));
    private static NoRender INSTANCE = new NoRender();

    public NoRender() {
        INSTANCE = this;
    }

    public static NoRender getInstance() {
        return INSTANCE;
    }

    @EventHandler
    public void onTick() {
        if (!this.glowing.getValue().booleanValue()) {
            return;
        }
        for (Entity entity : NoRender.mc.world.loadedEntityList) {
            if (!entity.isGlowing()) continue;
            entity.setGlowing(false);
        }
    }
}
