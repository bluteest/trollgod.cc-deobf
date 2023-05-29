//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.renderer.entity.Render
 *  net.minecraft.other.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  org.lwjgl.opengl.GL11
 */
package me.hollow.realth.api.mixin.mixins.render;

import me.hollow.realth.api.util.ColorUtil;
import me.hollow.realth.client.modules.render.EntityESP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={RenderManager.class})
public class MixinRenderManager {
    @Inject(method={"renderEntity"}, at={@At(value="RETURN")}, locals=LocalCapture.CAPTURE_FAILHARD)
    public void onRenderEntity(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci, Render<Entity> render) {
        if (entityIn.getClass() == EntityEnderCrystal.class && EntityESP.getInstance().isEnabled() && EntityESP.getInstance().crystals.getValue().booleanValue()) {
            GL11.glPushAttrib((int)1048575);
            GL11.glPolygonMode((int)1032, (int)6913);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)2848);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            ColorUtil.color(EntityESP.getInstance().getColor());
            GL11.glLineWidth((float)1.0f);
            render.doRender(entityIn, x, y, z, yaw, partialTicks);
            GL11.glPopAttrib();
        }
    }
}

