//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.api.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class InventoryManager implements Util
{
    public int currentPlayerItem;
    private int recoverySlot;
    
    public InventoryManager() {
        this.recoverySlot = -1;
    }
    
    public void update() {
        if (this.recoverySlot != -1) {
            InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange((this.recoverySlot == 8) ? 7 : (this.recoverySlot + 1)));
            InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.recoverySlot));
            InventoryManager.mc.player.inventory.currentItem = this.recoverySlot;
            final int i = InventoryManager.mc.player.inventory.currentItem;
            if (i != this.currentPlayerItem) {
                this.currentPlayerItem = i;
                InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.currentPlayerItem));
            }
            this.recoverySlot = -1;
        }
    }
    
    public void switchToSlot(final int slot) {
        InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        InventoryManager.mc.player.inventory.currentItem = slot;
        InventoryManager.mc.playerController.updateController();
    }
    
    public void switchBack(final int slot) {
        if (slot != -1) {
            InventoryManager.mc.player.inventory.currentItem = slot;
            InventoryManager.mc.playerController.updateController();
        }
    }
    
    public int getItemSlot(final Item item, final boolean hotbar) {
        int itemSlot = -1;
        for (int i = 1; i <= (hotbar ? 45 : 36); ++i) {
            final ItemStack stack = InventoryManager.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(item)) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    public int getItemSlot(final Item item) {
        int itemSlot = -1;
        for (int i = 1; i <= 45; ++i) {
            final ItemStack stack = InventoryManager.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(item)) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    public int getBlockSlot(final Block block) {
        int itemSlot = -1;
        for (int i = 1; i <= 45; ++i) {
            final ItemStack stack = InventoryManager.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(Item.getItemFromBlock(block))) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    public int getBlockFromHotbar(final Block block) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryManager.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(Item.getItemFromBlock(block))) {
                slot = i;
            }
        }
        return slot;
    }
    
    public int getItemFromHotbar(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryManager.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(item)) {
                slot = i;
            }
        }
        return slot;
    }
    
    public void recoverSilent(final int slot) {
        this.recoverySlot = slot;
    }
}
