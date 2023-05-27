//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.item.*;
import me.hollow.realth.client.modules.render.*;
import org.lwjgl.opengl.*;
import me.hollow.realth.api.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ RenderManager.class })
public class MixinRenderManager
{
    @Inject(method = { "renderEntity" }, at = { @At("RETURN") }, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onRenderEntity(final Entity entityIn, final double x, final double y, final double z, final float yaw, final float partialTicks, final boolean p_188391_10_, final CallbackInfo ci, final Render<Entity> render) {
        if (entityIn.getClass() == EntityEnderCrystal.class && EntityESP.getInstance().isEnabled() && EntityESP.getInstance().crystals.getValue()) {
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1032, 6913);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            ColorUtil.color(EntityESP.getInstance().getColor());
            GL11.glLineWidth(1.0f);
            render.doRender(entityIn, x, y, z, yaw, partialTicks);
            GL11.glPopAttrib();
        }
    }
}
