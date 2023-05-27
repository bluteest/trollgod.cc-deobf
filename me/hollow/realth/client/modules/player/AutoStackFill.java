//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.b0at.api.event.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

@ModuleManifest(label = "AutoStackFill", category = Module.Category.PLAYER)
public class AutoStackFill extends Module
{
    private final Setting<Integer> threshold;
    private final Setting<Integer> tickDelay;
    private int delayStep;
    
    public AutoStackFill() {
        this.threshold = (Setting<Integer>)this.register(new Setting("Threshold", (Object)32, (Object)1, (Object)64));
        this.tickDelay = (Setting<Integer>)this.register(new Setting("Delay", (Object)1, (Object)0, (Object)5));
        this.delayStep = 0;
    }
    
    private Map<Integer, ItemStack> getInventory() {
        return this.getInventorySlots(9, 35);
    }
    
    private Map<Integer, ItemStack> getHotbar() {
        return this.getInventorySlots(36, 44);
    }
    
    private Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)AutoStackFill.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
    
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        if (AutoStackFill.mc.player == null || AutoStackFill.mc.world == null) {
            return;
        }
        if (AutoStackFill.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delayStep < (int)this.tickDelay.getValue()) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        final Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        final int inventorySlot = slots.getKey();
        final int hotbarSlot = slots.getValue();
        AutoStackFill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)AutoStackFill.mc.player);
        AutoStackFill.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoStackFill.mc.player);
        AutoStackFill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)AutoStackFill.mc.player);
    }
    
    private Pair<Integer, Integer> findReplenishableHotbarSlot() {
        Pair<Integer, Integer> returnPair = null;
        for (final Map.Entry<Integer, ItemStack> hotbarSlot : this.getHotbar().entrySet()) {
            final ItemStack stack = hotbarSlot.getValue();
            if (!stack.isEmpty() && stack.getItem() != Items.AIR && stack.isStackable() && stack.getCount() < stack.getMaxStackSize() && stack.getCount() <= (int)this.threshold.getValue()) {
                final int inventorySlot;
                if ((inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) {
                    continue;
                }
                returnPair = new Pair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
            }
        }
        return returnPair;
    }
    
    private int findCompatibleInventorySlot(final ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (final Map.Entry<Integer, ItemStack> entry : this.getInventory().entrySet()) {
            final ItemStack inventoryStack = entry.getValue();
            if (!inventoryStack.isEmpty() && inventoryStack.getItem() != Items.AIR && this.isCompatibleStacks(hotbarStack, inventoryStack)) {
                final int currentStackSize;
                if (smallestStackSize <= (currentStackSize = ((ItemStack)AutoStackFill.mc.player.inventoryContainer.getInventory().get((int)entry.getKey())).getCount())) {
                    continue;
                }
                smallestStackSize = currentStackSize;
                inventorySlot = entry.getKey();
            }
        }
        return inventorySlot;
    }
    
    private boolean isCompatibleStacks(final ItemStack stack1, final ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            final Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            final Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.getMaterial(block1.getBlockState().getBaseState()).equals(block2.getMaterial(block2.getBlockState().getBaseState()))) {
                return false;
            }
        }
        return stack1.getDisplayName().equals(stack2.getDisplayName()) && stack1.getItemDamage() == stack2.getItemDamage();
    }
    
    public class Pair<K, V>
    {
        private final K key;
        private final V value;
        
        public Pair(final K key, final V value) {
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
