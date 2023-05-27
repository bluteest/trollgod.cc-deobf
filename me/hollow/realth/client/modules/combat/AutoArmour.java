//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.combat;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.client.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;

@ModuleManifest(label = "AutoArmour", category = Category.COMBAT, listen = false)
public class AutoArmour extends Module
{
    private int piece;
    
    public AutoArmour() {
        this.piece = 0;
    }
    
    @SubscribeEvent
    public void playerUpdate(final UpdateEvent event) {
        int bestSlot = -1;
        int bestVal = 0;
        for (int i = 5; i <= 44; ++i) {
            final Slot slot = AutoArmour.mc.player.inventoryContainer.getSlot(i);
            if (slot != null && slot.getHasStack() && slot.getStack().getItem() instanceof ItemArmor && (Item.getIdFromItem(slot.getStack().getItem()) - 2) % 4 == this.piece) {
                final int val;
                if ((val = this.getArmorValue(slot.getStack())) > bestVal) {
                    bestVal = val;
                    bestSlot = i;
                }
            }
        }
        if (bestSlot != -1) {
            if (bestSlot < 9) {
                ++this.piece;
            }
            else {
                final Slot armorSlot = AutoArmour.mc.player.inventoryContainer.getSlot(5 + this.piece);
                if (armorSlot != null && armorSlot.getHasStack()) {
                    if (this.isInventoryFull()) {
                        AutoArmour.mc.playerController.windowClick(AutoArmour.mc.player.inventoryContainer.windowId, 5 + this.piece, 1, ClickType.THROW, (EntityPlayer)AutoArmour.mc.player);
                    }
                    else {
                        AutoArmour.mc.playerController.windowClick(AutoArmour.mc.player.inventoryContainer.windowId, 5 + this.piece, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmour.mc.player);
                    }
                }
                else {
                    AutoArmour.mc.playerController.windowClick(AutoArmour.mc.player.inventoryContainer.windowId, bestSlot, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmour.mc.player);
                    ++this.piece;
                }
            }
        }
        else {
            ++this.piece;
        }
        this.piece %= 4;
    }
    
    public boolean isInventoryFull() {
        boolean full = true;
        for (int i = 9; i <= 44; ++i) {
            final Slot slot = AutoArmour.mc.player.inventoryContainer.getSlot(i);
            if (!slot.getHasStack()) {
                full = false;
            }
        }
        return full;
    }
    
    public int getArmorValue(final ItemStack stack) {
        if (!(stack.getItem() instanceof ItemArmor)) {
            return 0;
        }
        final ItemArmor item = (ItemArmor)stack.getItem();
        int itemValue = 0;
        final int id = Item.getIdFromItem((Item)item);
        final ItemArmor.ArmorMaterial material = item.getArmorMaterial();
        itemValue = ArmorMaterialValues.getValue(material);
        if (stack.getEnchantmentTagList() == null) {
            return itemValue;
        }
        int enchantValue = 0;
        for (int i = 0; i < stack.getEnchantmentTagList().tagCount(); ++i) {
            final int enchantLevel = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("lvl");
            final int enchantID = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("id");
            enchantValue += EnchantmentsValues.getValue(Enchantment.getEnchantmentByID(enchantID)) * enchantLevel;
        }
        return itemValue + enchantValue;
    }
    
    public enum EnchantmentsValues
    {
        UNBREAKING(Enchantments.UNBREAKING, 25), 
        EFFICIENCY(Enchantments.EFFICIENCY, 50), 
        SILKTOUCH(Enchantments.SILK_TOUCH, 25), 
        FORTUNE(Enchantments.FORTUNE, 25), 
        PROTECTION(Enchantments.PROTECTION, 100), 
        FIREPROTECTION(Enchantments.FIRE_PROTECTION, 50), 
        BLASTPROTECTION(Enchantments.BLAST_PROTECTION, 30), 
        PROJECTILEPROTECTION(Enchantments.PROJECTILE_PROTECTION, 30), 
        RESPIRATION(Enchantments.RESPIRATION, 20), 
        AQUAAFFINITY(Enchantments.AQUA_AFFINITY, 30), 
        FEATHERFALLING(Enchantments.FEATHER_FALLING, 50), 
        THORNS(Enchantments.THORNS, 40), 
        DEPTHSTRIDER(Enchantments.DEPTH_STRIDER, 30), 
        SHARPNESS(Enchantments.SHARPNESS, 100), 
        SMITE(Enchantments.SMITE, 50), 
        BANEOFARTHROPODS(Enchantments.BANE_OF_ARTHROPODS, 20), 
        KNOCKBACK(Enchantments.KNOCKBACK, 75), 
        FIREASPECT(Enchantments.FIRE_ASPECT, 80), 
        LOOTING(Enchantments.LOOTING, 40);
        
        private final Enchantment enchant;
        private final int value;
        
        private EnchantmentsValues(final Enchantment enchantment, final int value) {
            this.enchant = enchantment;
            this.value = value;
        }
        
        public Enchantment getEnchantment() {
            return this.enchant;
        }
        
        public int getValue() {
            return this.value;
        }
        
        public static int getValue(final Enchantment enchant) {
            for (final EnchantmentsValues enchant2 : values()) {
                if (enchant2.getEnchantment() == enchant) {
                    return enchant2.getValue();
                }
            }
            return 0;
        }
    }
    
    public enum ArmorMaterialValues
    {
        LEATHER(ItemArmor.ArmorMaterial.LEATHER, 50), 
        GOLD(ItemArmor.ArmorMaterial.GOLD, 100), 
        CHAIN(ItemArmor.ArmorMaterial.CHAIN, 200), 
        IRON(ItemArmor.ArmorMaterial.IRON, 300), 
        DIAMOND(ItemArmor.ArmorMaterial.DIAMOND, 400);
        
        private final ItemArmor.ArmorMaterial material;
        private final int value;
        
        private ArmorMaterialValues(final ItemArmor.ArmorMaterial material, final int value) {
            this.value = value;
            this.material = material;
        }
        
        public ItemArmor.ArmorMaterial getMaterial() {
            return this.material;
        }
        
        public int getValue() {
            return this.value;
        }
        
        public static int getValue(final ItemArmor.ArmorMaterial material) {
            for (final ArmorMaterialValues material2 : values()) {
                if (material2.getMaterial() == material) {
                    return material2.getValue();
                }
            }
            return 0;
        }
    }
}
