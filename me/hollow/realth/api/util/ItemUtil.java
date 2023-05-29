//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemShield
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 */
package me.hollow.realth.api.util;

import me.hollow.realth.api.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class ItemUtil
implements Util {
    public static int getItemFromHotbar(Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() != item) continue;
            slot = i;
        }
        return slot;
    }

    public static int getItemSlot(Class clss) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (ItemUtil.mc.player.inventory.getStackInSlot(i).getItem().getClass() != clss) continue;
            itemSlot = i;
            break;
        }
        return itemSlot;
    }

    public static int getItemSlot(Item item) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (!ItemUtil.mc.player.inventory.getStackInSlot(i).getItem().equals((Object)item)) continue;
            itemSlot = i;
            break;
        }
        return itemSlot;
    }
    public static int getItemSlotInHotbar(Item item) {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            final ItemStack is = mc.player.inventory.getStackInSlot(i);
            if (is.getItem() == item) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static boolean isArmorLow(EntityPlayer player, int durability) {
        for (ItemStack piece : player.inventory.armorInventory) {
            if (piece == null) {
                return true;
            }
            if (ItemUtil.getItemDamage(piece) >= durability) continue;
            return true;
        }
        return false;
    }

    public static int getItemDamage(ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    public static float getDamageInPercent(ItemStack stack) {
        return (float)ItemUtil.getItemDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }

    public static int getRoundedDamage(ItemStack stack) {
        return (int)ItemUtil.getDamageInPercent(stack);
    }

    public static boolean hasDurability(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    public static int getItemCount(final Item item) {
        int count = 0;

        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == item) {
                count += stack.getCount();
            }
        }

        return count;
    }
}

