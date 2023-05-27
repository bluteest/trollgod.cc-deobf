//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import net.minecraft.entity.player.*;
import net.b0at.api.event.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;

@ModuleManifest(label = "ChestSwap", category = Module.Category.PLAYER, listen = false)
public class ChestSwap extends Module
{
    @EventHandler
    public void onEnable() {
        if (ChestSwap.mc.player == null) {
            return;
        }
        final ItemStack itemStack = ChestSwap.mc.player.inventoryContainer.getSlot(6).getStack();
        if (itemStack.isEmpty()) {
            final int n = this.FindChestItem(true);
            if (n != -1) {
                ChestSwap.mc.playerController.windowClick(ChestSwap.mc.player.inventoryContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)ChestSwap.mc.player);
                ChestSwap.mc.playerController.windowClick(ChestSwap.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)ChestSwap.mc.player);
                ChestSwap.mc.playerController.windowClick(ChestSwap.mc.player.inventoryContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)ChestSwap.mc.player);
                ChestSwap.mc.playerController.updateController();
            }
            this.setEnabled(false);
            return;
        }
        final int n = this.FindChestItem(itemStack.getItem() instanceof ItemArmor);
        if (n != -1) {
            ChestSwap.mc.playerController.windowClick(ChestSwap.mc.player.inventoryContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)ChestSwap.mc.player);
            ChestSwap.mc.playerController.windowClick(ChestSwap.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)ChestSwap.mc.player);
            ChestSwap.mc.playerController.windowClick(ChestSwap.mc.player.inventoryContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)ChestSwap.mc.player);
            ChestSwap.mc.playerController.updateController();
        }
        this.setEnabled(false);
    }
    
    private int FindChestItem(final boolean bl) {
        int n = -1;
        float f = 0.0f;
        for (int i = 0; i < ChestSwap.mc.player.inventoryContainer.getInventory().size(); ++i) {
            final ItemStack itemStack;
            if (i != 0 && i != 5 && i != 6 && i != 7 && i != 8 && (itemStack = (ItemStack)ChestSwap.mc.player.inventoryContainer.getInventory().get(i)) != null) {
                if (itemStack.getItem() != Items.AIR) {
                    if (itemStack.getItem() instanceof ItemArmor) {
                        final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
                        if (itemArmor.armorType == EntityEquipmentSlot.CHEST) {
                            final float f2 = (float)(itemArmor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemStack));
                            final boolean bl2 = EnchantmentHelper.hasBindingCurse(itemStack);
                            if (f2 > f) {
                                if (!bl2) {
                                    f = f2;
                                    n = i;
                                }
                            }
                        }
                    }
                    else if (bl) {
                        if (itemStack.getItem() instanceof ItemElytra) {
                            return i;
                        }
                    }
                }
            }
        }
        return n;
    }
}
