//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.other.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 */
package me.hollow.realth.client.modules.player;

import java.util.HashMap;
import java.util.Map;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

@ModuleManifest(label="AutoStackFill", category=Module.Category.PLAYER)
public class AutoStackFill
extends Module {
    private final Setting<Integer> threshold = this.register(new Setting<Integer>("Threshold", 32, 1, 64));
    private final Setting<Integer> tickDelay = this.register(new Setting<Integer>("Delay", 1, 0, 5));
    private int delayStep = 0;

    private Map<Integer, ItemStack> getInventory() {
        return this.getInventorySlots(9, 35);
    }

    private Map<Integer, ItemStack> getHotbar() {
        return this.getInventorySlots(36, 44);
    }

    private Map<Integer, ItemStack> getInventorySlots(int current, int last) {
        HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)this.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delayStep < this.tickDelay.getValue()) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        int inventorySlot = slots.getKey();
        int hotbarSlot = slots.getValue();
        this.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
    }

    private Pair<Integer, Integer> findReplenishableHotbarSlot() {
        Pair<Integer, Integer> returnPair = null;
        for (Map.Entry<Integer, ItemStack> hotbarSlot : this.getHotbar().entrySet()) {
            int inventorySlot;
            ItemStack stack = hotbarSlot.getValue();
            if (stack.isEmpty() || stack.getItem() == Items.AIR || !stack.isStackable() || stack.getCount() >= stack.getMaxStackSize() || stack.getCount() > this.threshold.getValue() || (inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) continue;
            returnPair = new Pair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
        }
        return returnPair;
    }

    private int findCompatibleInventorySlot(ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (Map.Entry<Integer, ItemStack> entry : this.getInventory().entrySet()) {
            int currentStackSize;
            ItemStack inventoryStack = entry.getValue();
            if (inventoryStack.isEmpty() || inventoryStack.getItem() == Items.AIR || !this.isCompatibleStacks(hotbarStack, inventoryStack) || smallestStackSize <= (currentStackSize = ((ItemStack)this.mc.player.inventoryContainer.getInventory().get(entry.getKey().intValue())).getCount())) continue;
            smallestStackSize = currentStackSize;
            inventorySlot = entry.getKey();
        }
        return inventorySlot;
    }

    private boolean isCompatibleStacks(ItemStack stack1, ItemStack stack2) {
        if (!stack1.getItem().equals((Object)stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.getMaterial(block1.getBlockState().getBaseState()).equals((Object)block2.getMaterial(block2.getBlockState().getBaseState()))) {
                return false;
            }
        }
        if (!stack1.getDisplayName().equals(stack2.getDisplayName())) {
            return false;
        }
        return stack1.getItemDamage() == stack2.getItemDamage();
    }

    public class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }
    }
}

