//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import me.hollow.realth.api.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;

public class ItemUtil implements Util
{
    public static int getItemFromHotbar(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == item) {
                slot = i;
            }
        }
        return slot;
    }
    
    public static int getItemSlot(final Class clss) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (ItemUtil.mc.player.inventory.getStackInSlot(i).getItem().getClass() == clss) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    public static int getItemSlot(final Item item) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (ItemUtil.mc.player.inventory.getStackInSlot(i).getItem().equals(item)) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    public static int getItemSlotInHotbar(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack is = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (is.getItem() == item) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece == null) {
                return true;
            }
            if (getItemDamage(piece) >= durability) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public static int getItemCount(final Item item) {
        int count = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == item) {
                count += stack.getCount();
            }
        }
        return count;
    }
}
