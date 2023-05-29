//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 */
package me.hollow.realth.client.modules.player;

import me.hollow.realth.api.mixin.accessors.IMinecraft;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.TickEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

@ModuleManifest(label="FastPlace", category=Module.Category.PLAYER)
public class FastPlace
extends Module {
    public final Setting<Boolean> fastEXP = this.register(new Setting<Boolean>("FastEXP", true));
    public final Setting<Boolean> blocks = this.register(new Setting<Boolean>("Blocks", true));

    @EventHandler
    public void onUpdate(TickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        Item heldItem = mc.player.getHeldItemMainhand().getItem();
        if (heldItem instanceof ItemBlock && this.blocks.getValue().booleanValue()) {
            ((IMinecraft)this.mc).setRightClickDelayTimer(0);
        }
        if (this.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE && this.fastEXP.getValue().booleanValue()) {
            ((IMinecraft)this.mc).setRightClickDelayTimer(0);
        }
    }
}

