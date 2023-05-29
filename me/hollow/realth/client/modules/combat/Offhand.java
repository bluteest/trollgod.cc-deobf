//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.other.CPacketClickWindow
 */
package me.hollow.realth.client.modules.combat;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.EntityUtil;
import me.hollow.realth.api.util.InventoryUtil;
import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

@ModuleManifest(label="Offhand", category=Module.Category.COMBAT)
public class Offhand
extends Module {
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
        this.mode = (Setting<OffhandItem>)this.register(new Setting("Mode", OffhandItem.TOTEM));
        this.health = (Setting<Float>)this.register(new Setting("Health", Float.valueOf(16.0f), Float.valueOf(0.0f), Float.valueOf(36.0f)));
        this.holeHealth = (Setting<Float>)this.register(new Setting("HoleHealth", Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(36.0f)));
        this.swordGap = (Setting<Boolean>)this.register(new Setting("SwordGap", true));
        this.swordGapHealth = (Setting<Float>)this.register(new Setting("SwordGapMinHP", Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(36.0f), v -> this.swordGap.getValue()));
        this.totemOnLethalCrystal = (Setting<Boolean>)this.register(new Setting("LethalCrystalSwitch", true));
    }


    @EventHandler
    public void onUpdate(UpdateEvent event) {
        int totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
            totems += mc.player.getHeldItemOffhand().getCount();
        final float currentHealth = Offhand.mc.player.getHealth() + Offhand.mc.player.getAbsorptionAmount();
        if (Offhand.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && Offhand.mc.gameSettings.keyBindUseItem.isKeyDown() && currentHealth > this.swordGapHealth.getValue()) {
            this.lastItem = this.offhandItem;
            this.offhandItem = Items.GOLDEN_APPLE;
        }
        else if (EntityUtil.isSafe(Offhand.mc.player)) {
            if (currentHealth <= this.holeHealth.getValue()) {
                this.offhandItem = Items.TOTEM_OF_UNDYING;
            }
            else {
                final String lowerCase = this.mode.currentEnumName().toLowerCase();
                switch (lowerCase) {
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
        else if (currentHealth <= this.health.getValue()) {
            this.offhandItem = Items.TOTEM_OF_UNDYING;
        }
        else {
            final String lowerCase2 = this.mode.currentEnumName().toLowerCase();
            switch (lowerCase2) {
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
        Offhand.mc.playerController.windowClick(0, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer) Offhand.mc.player);
        Offhand.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer) Offhand.mc.player);
        for (int i = 0; i < 45; ++i) {
            if (Offhand.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                returnSlot = i;
                break;
            }
        }
        if (returnSlot != -1) {
            Offhand.mc.playerController.windowClick(0, (returnSlot < 9) ? (returnSlot + 36) : returnSlot, 0, ClickType.PICKUP, (EntityPlayer) Offhand.mc.player);
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

