//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.other.model.ModelBase
 *  net.minecraft.other.renderer.entity.Render
 *  net.minecraft.other.renderer.entity.RenderLivingBase
 *  net.minecraft.other.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package me.hollow.realth.api.mixin.mixins.render;

import javax.annotation.Nullable;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.ColorUtil;
import me.hollow.realth.client.modules.render.EntityESP;
import me.hollow.realth.client.modules.render.Skeleton;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderLivingBase.class})
public class MixinRenderLivingBase<T extends EntityLivingBase>
extends Render<T> {
    @Shadow
    protected ModelBase mainModel;

    @Inject(method={"renderLayers"}, at={@At(value="RETURN")})
    public void renderLayers(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn, CallbackInfo ci) {
        if (Skeleton.getInstance().isEnabled()) {
            Skeleton.getInstance().onRenderModel(this.mainModel, (Entity)entitylivingbaseIn);
        }
        if (entitylivingbaseIn instanceof EntityPlayer && EntityESP.getInstance().isEnabled() && EntityESP.getInstance().players.getValue().booleanValue()) {
            GL11.glPushAttrib((int)1048575);
            GL11.glPolygonMode((int)1032, (int)6913);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)2848);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            ColorUtil.color(JordoHack.INSTANCE.getFriendManager().isFriend(entitylivingbaseIn.getName()) ? -11157267 : EntityESP.getInstance().getColor());
            GL11.glLineWidth((float)0.1f);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleIn);
            GL11.glPopAttrib();
        }
    }

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}

