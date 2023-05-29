//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.hollow.realth.client.modules.misc;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.InventoryUtil;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;

@ModuleManifest(label="MiddleClick", category=Module.Category.MISC)
public class MiddleClick
extends Module {
    private static MiddleClick INSTANCE = new MiddleClick();
    private final Setting<Boolean> friends = this.register(new Setting<Boolean>("Friends", true));
    private final Setting<Boolean> pearl = this.register(new Setting<Boolean>("Pearl", true));

    public MiddleClick() {
        INSTANCE = this;
    }

    public static MiddleClick getInstance() {
        return INSTANCE;
    }

    public void run(int mouse) {
        if (mouse == 2 && this.friends.getValue().booleanValue() && this.mc.objectMouseOver.entityHit != null) {
            Entity entity = this.mc.objectMouseOver.entityHit;
            if (!(entity instanceof EntityPlayer)) {
                return;
            }
            if (JordoHack.INSTANCE.getFriendManager().isFriend(entity.getName())) {
                JordoHack.INSTANCE.getFriendManager().removeFriend(entity.getName());
            } else {
                JordoHack.INSTANCE.getFriendManager().addFriend(entity.getName());
            }
            if (this.pearl.getValue()) {
                this.throwPearl();
            }
        }
    }

    private void throwPearl() {
        boolean offhand;
        int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        boolean bl = offhand = MiddleClick.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
        if (pearlSlot != -1 || offhand) {
            int oldslot = MiddleClick.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            MiddleClick.mc.playerController.processRightClick(MiddleClick.mc.player, MiddleClick.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }
}

