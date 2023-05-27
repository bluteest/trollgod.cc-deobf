//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.api.mixin.accessors.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "FastPlace", category = Module.Category.PLAYER)
public class FastPlace extends Module
{
    public final Setting<Boolean> fastEXP;
    public final Setting<Boolean> blocks;
    
    public FastPlace() {
        this.fastEXP = (Setting<Boolean>)this.register(new Setting("FastEXP", (Object)true));
        this.blocks = (Setting<Boolean>)this.register(new Setting("Blocks", (Object)true));
    }
    
    @EventHandler
    public void onUpdate(final TickEvent event) {
        if (FastPlace.mc.player == null || FastPlace.mc.world == null) {
            return;
        }
        final Item heldItem = FastPlace.mc.player.getHeldItemMainhand().getItem();
        if (heldItem instanceof ItemBlock && (boolean)this.blocks.getValue()) {
            ((IMinecraft)FastPlace.mc).setRightClickDelayTimer(0);
        }
        if (FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE && (boolean)this.fastEXP.getValue()) {
            ((IMinecraft)FastPlace.mc).setRightClickDelayTimer(0);
        }
    }
}
