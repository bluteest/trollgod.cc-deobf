//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.entity.player.*;
import me.hollow.realth.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import me.hollow.realth.api.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

@ModuleManifest(label = "MiddleClick", category = Category.MISC)
public class MiddleClick extends Module
{
    private static MiddleClick INSTANCE;
    private final Setting<Boolean> friends;
    private final Setting<Boolean> pearl;
    
    public MiddleClick() {
        this.friends = (Setting<Boolean>)this.register(new Setting("Friends", (Object)true));
        this.pearl = (Setting<Boolean>)this.register(new Setting("Pearl", (Object)true));
        MiddleClick.INSTANCE = this;
    }
    
    public static MiddleClick getInstance() {
        return MiddleClick.INSTANCE;
    }
    
    public void run(final int mouse) {
        if (mouse == 2 && (boolean)this.friends.getValue() && MiddleClick.mc.objectMouseOver.entityHit != null) {
            final Entity entity = MiddleClick.mc.objectMouseOver.entityHit;
            if (!(entity instanceof EntityPlayer)) {
                return;
            }
            if (JordoHack.INSTANCE.getFriendManager().isFriend(entity.getName())) {
                JordoHack.INSTANCE.getFriendManager().removeFriend(entity.getName());
            }
            else {
                JordoHack.INSTANCE.getFriendManager().addFriend(entity.getName());
            }
            if (this.pearl.getValue()) {
                this.throwPearl();
            }
        }
    }
    
    private void throwPearl() {
        final int pearlSlot = InventoryUtil.findHotbarBlock((Class)ItemEnderPearl.class);
        final boolean bl;
        final boolean offhand = bl = (MiddleClick.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL);
        if (pearlSlot != -1 || offhand) {
            final int oldslot = MiddleClick.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }
    
    static {
        MiddleClick.INSTANCE = new MiddleClick();
    }
}
