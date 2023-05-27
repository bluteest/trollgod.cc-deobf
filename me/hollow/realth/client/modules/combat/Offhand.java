//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.combat;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import net.minecraft.init.*;
import java.util.function.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.b0at.api.event.*;
import net.minecraft.client.gui.inventory.*;
import me.hollow.realth.api.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

@ModuleManifest(label = "Offhand", category = Category.COMBAT)
public class Offhand extends Module
{
    public Setting<OffhandItem> mode;
    public Setting<Float> health;
    public Setting<Float> holeHealth;
    public Setting<Boolean> swordGap;
    public Setting<Float> swordGapHealth;
    public Setting<Boolean> totemOnLethalCrystal;
    public Item offhandItem;
    public Item holdingItem;
    public Item lastItem;
    public int crystals;
    public int gapples;
    public int totems;
    
    public Offhand() {
        this.mode = (Setting<OffhandItem>)this.register(new Setting("Mode", (Object)OffhandItem.TOTEM));
        this.health = (Setting<Float>)this.register(new Setting("Health", (Object)16.0f, (Object)0.0f, (Object)36.0f));
        this.holeHealth = (Setting<Float>)this.register(new Setting("HoleHealth", (Object)6.0f, (Object)0.1f, (Object)36.0f));
        this.swordGap = (Setting<Boolean>)this.register(new Setting("SwordGap", (Object)true));
        this.swordGapHealth = (Setting<Float>)this.register(new Setting("SwordGapMinHP", (Object)6.0f, (Object)0.1f, (Object)36.0f, v -> (boolean)this.swordGap.getValue()));
        this.totemOnLethalCrystal = (Setting<Boolean>)this.register(new Setting("LethalCrystalSwitch", (Object)true));
    }
    
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        int totems = Offhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems += Offhand.mc.player.getHeldItemOffhand().getCount();
        }
        final float currentHealth = Offhand.mc.player.getHealth() + Offhand.mc.player.getAbsorptionAmount();
        if (Offhand.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && Offhand.mc.gameSettings.keyBindUseItem.isKeyDown() && currentHealth > (float)this.swordGapHealth.getValue()) {
            this.lastItem = this.offhandItem;
            this.offhandItem = Items.GOLDEN_APPLE;
        }
        else if (EntityUtil.isSafe((Entity)Offhand.mc.player)) {
            if (currentHealth <= (float)this.holeHealth.getValue()) {
                this.offhandItem = Items.TOTEM_OF_UNDYING;
            }
            else {
                final String lowerCase3;
                final String lowerCase = lowerCase3 = this.mode.currentEnumName().toLowerCase();
                switch (lowerCase3) {
                    case "totem": {
                        this.offhandItem = Items.TOTEM_OF_UNDYING;
                        break;
                    }
                    case "crystal": {
                        this.offhandItem = Items.END_CRYSTAL;
                        break;
                    }
                    case "gapple": {
                        this.offhandItem = Items.GOLDEN_APPLE;
                        break;
                    }
                }
            }
        }
        else if (currentHealth <= (float)this.health.getValue()) {
            this.offhandItem = Items.TOTEM_OF_UNDYING;
        }
        else {
            final String lowerCase4;
            final String lowerCase2 = lowerCase4 = this.mode.currentEnumName().toLowerCase();
            switch (lowerCase4) {
                case "totem": {
                    this.offhandItem = Items.TOTEM_OF_UNDYING;
                    break;
                }
                case "crystal": {
                    this.offhandItem = Items.END_CRYSTAL;
                    break;
                }
                case "gapple": {
                    this.offhandItem = Items.GOLDEN_APPLE;
                    break;
                }
            }
        }
        this.setSuffix(this.mode.currentEnumName());
        this.doSwitch();
    }
    
    private void doSwitch() {
        this.holdingItem = Offhand.mc.player.getHeldItemOffhand().getItem();
        this.crystals = Offhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        this.gapples = Offhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        this.totems = Offhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (this.holdingItem.equals(Items.END_CRYSTAL)) {
            this.crystals += Offhand.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        }
        else if (this.holdingItem.equals(Items.GOLDEN_APPLE)) {
            this.gapples += Offhand.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        }
        else if (this.holdingItem.equals(Items.TOTEM_OF_UNDYING)) {
            this.totems += Offhand.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        }
        if (Offhand.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        final int slot = InventoryUtil.findItemInventorySlot(this.offhandItem, false);
        if (slot != -1) {
            if (this.holdingItem == this.offhandItem) {
                return;
            }
            if (this.offhandItem.equals(Items.END_CRYSTAL) && this.crystals > 0) {
                this.switchItem(slot);
            }
            if (this.offhandItem.equals(Items.GOLDEN_APPLE) && this.gapples > 0) {
                this.switchItem(slot);
            }
            if (this.offhandItem.equals(Items.TOTEM_OF_UNDYING) && this.totems > 0) {
                this.switchItem(slot);
            }
        }
    }
    
    private void switchItem(final int slot) {
        int returnSlot = -1;
        if (slot == -1) {
            return;
        }
        Offhand.mc.playerController.windowClick(0, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        Offhand.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        for (int i = 0; i < 45; ++i) {
            if (Offhand.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                returnSlot = i;
                break;
            }
        }
        if (returnSlot != -1) {
            Offhand.mc.playerController.windowClick(0, (returnSlot < 9) ? (returnSlot + 36) : returnSlot, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        }
        Offhand.mc.playerController.updateController();
    }
    
    public enum OffhandItem
    {
        TOTEM, 
        CRYSTAL, 
        GAPPLE;
    }
}
