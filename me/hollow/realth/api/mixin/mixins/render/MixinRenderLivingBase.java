//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.render;

import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.hollow.realth.client.modules.render.*;
import org.lwjgl.opengl.*;
import me.hollow.realth.*;
import me.hollow.realth.api.util.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import javax.annotation.*;

@Mixin({ RenderLivingBase.class })
public class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    protected ModelBase mainModel;
    
    @Inject(method = { "renderLayers" }, at = { @At("RETURN") })
    public void renderLayers(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn, final CallbackInfo ci) {
        if (Skeleton.getInstance().isEnabled()) {
            Skeleton.getInstance().onRenderModel(this.mainModel, (Entity)entitylivingbaseIn);
        }
        if (entitylivingbaseIn instanceof EntityPlayer && EntityESP.getInstance().isEnabled() && EntityESP.getInstance().players.getValue()) {
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1032, 6913);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            ColorUtil.color(JordoHack.INSTANCE.getFriendManager().isFriend(entitylivingbaseIn.getName()) ? -11157267 : EntityESP.getInstance().getColor());
            GL11.glLineWidth(0.1f);
            this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleIn);
            GL11.glPopAttrib();
        }
    }
    
    protected MixinRenderLivingBase(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Nullable
    protected ResourceLocation getEntityTexture(final T entity) {
        return null;
    }
}
