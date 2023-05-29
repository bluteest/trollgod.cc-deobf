package me.hollow.realth.api.mixin.mixins.entity;

import me.hollow.realth.client.modules.render.NoRender;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerBipedArmor.class)
public abstract class MixinLayerBipedArmor {

    @Shadow
    protected abstract void setModelVisible(ModelBiped modelVisible);

    @Overwrite
    protected void setModelSlotVisible(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn) {
        setModelVisible(p_188359_1_);

        switch (slotIn) {
            case HEAD:
                p_188359_1_.bipedHead.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().helmet.getValue();
                p_188359_1_.bipedHeadwear.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().helmet.getValue();
                break;
            case CHEST:
                p_188359_1_.bipedBody.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().chestplate.getValue();
                p_188359_1_.bipedRightArm.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().chestplate.getValue();
                p_188359_1_.bipedLeftArm.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().chestplate.getValue();
                break;
            case LEGS:
                p_188359_1_.bipedBody.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().leggings.getValue();
                p_188359_1_.bipedRightLeg.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().leggings.getValue();
                p_188359_1_.bipedLeftLeg.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().leggings.getValue();
                break;
            case FEET:
                p_188359_1_.bipedRightLeg.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().boots.getValue();
                p_188359_1_.bipedLeftLeg.showModel = NoRender.getInstance().isEnabled() && NoRender.getInstance().boots.getValue();
        }
    }

}
