//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.other.renderer.GlStateManager
 *  net.minecraft.other.renderer.GlStateManager$DestFactor
 *  net.minecraft.other.renderer.GlStateManager$SourceFactor
 *  net.minecraft.other.renderer.RenderGlobal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package me.hollow.realth.client.modules.render;

import java.awt.Color;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.mixin.mixins.render.IEntityRenderer;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.client.modules.other.Colours;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@ModuleManifest(label="BlockHighlight", category=Module.Category.RENDER, listen=false)
public class BlockHighlight
extends Module {
    public final Setting<Float> lineWidth = this.register(new Setting<Float>("Width", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(4.0f)));

    @Override
    public void onEnable() {
        ((IEntityRenderer)this.mc.entityRenderer).setDrawBlockOutline(false);
    }

    @Override
    public void onDisable() {
        ((IEntityRenderer)this.mc.entityRenderer).setDrawBlockOutline(true);
    }

    @Override
    public void onRender3D() {
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            this.drawSelectionBox((EntityPlayer)this.mc.player, this.mc.objectMouseOver, 0, this.mc.getRenderPartialTicks());
        }
    }

    public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK) {
            Color color = new Color(Colours.getInstance().red.getValue(), Colours.getInstance().green.getValue(), Colours.getInstance().blue.getValue());
            GlStateManager.enableBlend();
            GL11.glEnable((int)2848);
            GL11.glHint((int)3154, (int)4354);
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth((float)this.lineWidth.getValue().floatValue());
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask((boolean)false);
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
            if (iblockstate.getMaterial() != Material.AIR) {
                double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
                RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)iblockstate.getSelectedBoundingBox((World)this.mc.world, blockpos).grow((double)0.002f).offset(-d3, -d4, -d5), (float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)1.0f);
            }
            GL11.glDisable((int)2848);
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
}

