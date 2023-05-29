package me.hollow.realth.client.modules.combat;

import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleManifest(label="AutoArmour",  category=Module.Category.COMBAT, listen=false)
public class AutoArmour
        extends Module {
    private int piece = 0;

    @SubscribeEvent
    public void playerUpdate(UpdateEvent event) {
        int bestSlot = -1;
        int bestVal = 0;
        for (int i = 5; i <= 44; ++i) {
            int val;
            Slot slot = AutoArmour.mc.player.inventoryContainer.getSlot(i);
            if (slot == null || !slot.getHasStack() || !(slot.getStack().getItem() instanceof ItemArmor) || (Item.getIdFromItem((Item)slot.getStack().getItem()) - 2) % 4 != this.piece || (val = this.getArmorValue(slot.getStack())) <= bestVal) continue;
            bestVal = val;
            bestSlot = i;
        }
        if (bestSlot != -1) {
            if (bestSlot < 9) {
                ++this.piece;
            } else {
                Slot armorSlot = AutoArmour.mc.player.inventoryContainer.getSlot(5 + this.piece);
                if (armorSlot != null && armorSlot.getHasStack()) {
                    if (this.isInventoryFull()) {
                        AutoArmour.mc.playerController.windowClick(AutoArmour.mc.player.inventoryContainer.windowId, 5 + this.piece, 1, ClickType.THROW, (EntityPlayer) AutoArmour.mc.player);
                    } else {
                        AutoArmour.mc.playerController.windowClick(AutoArmour.mc.player.inventoryContainer.windowId, 5 + this.piece, 0, ClickType.QUICK_MOVE, (EntityPlayer) AutoArmour.mc.player);
                    }
                } else {
                    AutoArmour.mc.playerController.windowClick(AutoArmour.mc.player.inventoryContainer.windowId, bestSlot, 0, ClickType.QUICK_MOVE, (EntityPlayer) AutoArmour.mc.player);
                    ++this.piece;
                }
            }
        } else {
            ++this.piece;
        }
        this.piece %= 4;
    }

    public boolean isInventoryFull() {
        boolean full = true;
        for (int i = 9; i <= 44; ++i) {
            Slot slot = AutoArmour.mc.player.inventoryContainer.getSlot(i);
            if (slot.getHasStack()) continue;
            full = false;
        }
        return full;
    }

    public int getArmorValue(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemArmor)) {
            return 0;
        }
        ItemArmor item = (ItemArmor)stack.getItem();
        int itemValue = 0;
        int id = Item.getIdFromItem((Item)item);
        ItemArmor.ArmorMaterial material = item.getArmorMaterial();
        itemValue = ArmorMaterialValues.getValue(material);
        if (stack.getEnchantmentTagList() == null) {
            return itemValue;
        }
        int enchantValue = 0;
        for (int i = 0; i < stack.getEnchantmentTagList().tagCount(); ++i) {
            int enchantLevel = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("lvl");
            int enchantID = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("id");
            enchantValue += EnchantmentsValues.getValue(Enchantment.getEnchantmentByID((int)enchantID)) * enchantLevel;
        }
        return itemValue + enchantValue;
    }

    public static enum EnchantmentsValues {
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

        private EnchantmentsValues(Enchantment enchantment, int value) {
            this.enchant = enchantment;
            this.value = value;
        }

        public Enchantment getEnchantment() {
            return this.enchant;
        }

        public int getValue() {
            return this.value;
        }

        public static int getValue(Enchantment enchant) {
            for (EnchantmentsValues enchant1 : EnchantmentsValues.values()) {
                if (enchant1.getEnchantment() != enchant) continue;
                return enchant1.getValue();
            }
            return 0;
        }
    }

    public static enum ArmorMaterialValues {
        LEATHER(ItemArmor.ArmorMaterial.LEATHER, 50),
        GOLD(ItemArmor.ArmorMaterial.GOLD, 100),
        CHAIN(ItemArmor.ArmorMaterial.CHAIN, 200),
        IRON(ItemArmor.ArmorMaterial.IRON, 300),
        DIAMOND(ItemArmor.ArmorMaterial.DIAMOND, 400);

        private final ItemArmor.ArmorMaterial material;
        private final int value;

        private ArmorMaterialValues(ItemArmor.ArmorMaterial material, int value) {
            this.value = value;
            this.material = material;
        }

        public ItemArmor.ArmorMaterial getMaterial() {
            return this.material;
        }

        public int getValue() {
            return this.value;
        }

        public static int getValue(ItemArmor.ArmorMaterial material) {
            for (ArmorMaterialValues material1 : ArmorMaterialValues.values()) {
                if (material1.getMaterial() != material) continue;
                return material1.getValue();
            }
            return 0;
        }
    }
}
