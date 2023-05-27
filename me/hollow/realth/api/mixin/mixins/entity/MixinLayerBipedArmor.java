//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.entity;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.model.*;
import net.minecraft.inventory.*;
import me.hollow.realth.client.modules.render.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ LayerBipedArmor.class })
public abstract class MixinLayerBipedArmor
{
    @Shadow
    protected abstract void setModelVisible(final ModelBiped p0);
    
    @Overwrite
    protected void setModelSlotVisible(final ModelBiped p_188359_1_, final EntityEquipmentSlot slotIn) {
        this.setModelVisible(p_188359_1_);
        switch (slotIn) {
            case HEAD: {
                p_188359_1_.bipedHead.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().helmet.getValue());
                p_188359_1_.bipedHeadwear.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().helmet.getValue());
                break;
            }
            case CHEST: {
                p_188359_1_.bipedBody.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().chestplate.getValue());
                p_188359_1_.bipedRightArm.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().chestplate.getValue());
                p_188359_1_.bipedLeftArm.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().chestplate.getValue());
                break;
            }
            case LEGS: {
                p_188359_1_.bipedBody.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().leggings.getValue());
                p_188359_1_.bipedRightLeg.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().leggings.getValue());
                p_188359_1_.bipedLeftLeg.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().leggings.getValue());
                break;
            }
            case FEET: {
                p_188359_1_.bipedRightLeg.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().boots.getValue());
                p_188359_1_.bipedLeftLeg.showModel = (NoRender.getInstance().isEnabled() && NoRender.getInstance().boots.getValue());
                break;
            }
        }
    }
}
