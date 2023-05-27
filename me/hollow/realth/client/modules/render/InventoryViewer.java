//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

@ModuleManifest(label = "InventoryViewer", category = Module.Category.RENDER)
public class InventoryViewer extends Module
{
    private final Setting<Integer> x;
    private final Setting<Integer> y;
    private final RenderItem itemRender;
    
    public InventoryViewer() {
        this.x = (Setting<Integer>)this.register(new Setting("X", (Object)560, (Object)0, (Object)2000));
        this.y = (Setting<Integer>)this.register(new Setting("Y", (Object)466, (Object)0, (Object)1000));
        this.itemRender = InventoryViewer.mc.getRenderItem();
    }
    
    public void onRender2D() {
        GlStateManager.enableBlend();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        for (int size = InventoryViewer.mc.player.inventory.mainInventory.size() - 9, i = 0; i < size; ++i) {
            final int iX = (int)this.x.getValue() + (i % 9 << 4) + 11;
            final int iY = (int)this.y.getValue() + (i / 9 << 4) - 11 + 8;
            final ItemStack itemStack = (ItemStack)InventoryViewer.mc.player.inventory.mainInventory.get(i + 9);
            this.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
            this.itemRender.renderItemOverlayIntoGUI(InventoryViewer.mc.fontRenderer, itemStack, iX, iY, (String)null);
        }
        RenderHelper.disableStandardItemLighting();
        this.itemRender.zLevel = 0.0f;
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }
    
    private static void drawTexturedRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height, final int zLevel) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder BufferBuilder = tessellator.getBuffer();
        BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder.pos((double)x, (double)(y + height), (double)zLevel).tex((double)(textureX * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder.pos((double)(x + width), (double)y, (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        BufferBuilder.pos((double)x, (double)y, (double)zLevel).tex((double)(textureX * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        tessellator.draw();
    }
}
