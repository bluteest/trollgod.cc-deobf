package me.hollow.realth.client.managers;

import me.hollow.realth.JordoHack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import me.hollow.realth.api.Util;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class InventoryManager
        implements Util {
    public int currentPlayerItem;
    private int recoverySlot = -1;

    public void update() {
        if (this.recoverySlot != -1) {
            InventoryManager.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.recoverySlot == 8 ? 7 : this.recoverySlot + 1));
            InventoryManager.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.recoverySlot));
            InventoryManager.mc.player.inventory.currentItem = this.recoverySlot;
            int i = InventoryManager.mc.player.inventory.currentItem;
            if (i != this.currentPlayerItem) {
                this.currentPlayerItem = i;
                InventoryManager.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.currentPlayerItem));
            }
            this.recoverySlot = -1;
        }
    }
    public void switchToSlot(final int slot) {
        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        mc.player.inventory.currentItem = slot;
        mc.playerController.updateController();
    }

    public void switchBack(final int slot) {

        if (slot != -1) {
            mc.player.inventory.currentItem = slot;
            mc.playerController.updateController();
        }

    }
    public int getItemSlot(final Item item, final boolean hotbar) {
        int itemSlot = -1;
        for (int i = 1; i <= (hotbar ? 45 : 36); ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
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
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
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
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
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
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(Item.getItemFromBlock(block)))
                slot = i;
        }
        return slot;
    }

    public int getItemFromHotbar(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem().equals(item))
                slot = i;
        }
        return slot;
    }




    public void recoverSilent(int slot) {
        this.recoverySlot = slot;
    }
}
