//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.api.mixin.mixins.render.*;
import net.minecraft.entity.player.*;
import me.hollow.realth.client.modules.other.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

@ModuleManifest(label = "BlockHighlight", category = Module.Category.RENDER, listen = false)
public class BlockHighlight extends Module
{
    public final Setting<Float> lineWidth;
    
    public BlockHighlight() {
        this.lineWidth = (Setting<Float>)this.register(new Setting("Width", (Object)1.0f, (Object)0.1f, (Object)4.0f));
    }
    
    public void onEnable() {
        ((IEntityRenderer)BlockHighlight.mc.entityRenderer).setDrawBlockOutline(false);
    }
    
    public void onDisable() {
        ((IEntityRenderer)BlockHighlight.mc.entityRenderer).setDrawBlockOutline(true);
    }
    
    public void onRender3D() {
        if (BlockHighlight.mc.objectMouseOver != null && BlockHighlight.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            this.drawSelectionBox((EntityPlayer)BlockHighlight.mc.player, BlockHighlight.mc.objectMouseOver, 0, BlockHighlight.mc.getRenderPartialTicks());
        }
    }
    
    public void drawSelectionBox(final EntityPlayer player, final RayTraceResult movingObjectPositionIn, final int execute, final float partialTicks) {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK) {
            final Color color = new Color((int)Colours.getInstance().red.getValue(), (int)Colours.getInstance().green.getValue(), (int)Colours.getInstance().blue.getValue());
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth((float)this.lineWidth.getValue());
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            final BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            final IBlockState iblockstate = BlockHighlight.mc.world.getBlockState(blockpos);
            if (iblockstate.getMaterial() != Material.AIR) {
                final double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
                final double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
                final double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
                RenderGlobal.drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox((World)BlockHighlight.mc.world, blockpos).grow(0.0020000000949949026).offset(-d3, -d4, -d5), color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
}
