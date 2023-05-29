package me.hollow.realth.client.modules.render;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.Render2DEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleManifest(label="InventoryViewer", category= Module.Category.RENDER)
public class InventoryViewer extends Module {
    private final Setting<Integer> x = this.register(new Setting<Integer>("X", 560, 0, 2000));
    private final Setting<Integer> y = this.register(new Setting<Integer>("Y", 466, 0, 1000));

    private final RenderItem itemRender = mc.getRenderItem();

    @Override
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
        final int size = mc.player.inventory.mainInventory.size() - 9;
        for (int i = 0; i < size; i++) {
            final int iX = x.getValue() + ((i % 9) << 4) + 11;
            final int iY = y.getValue() + ((i / 9) << 4) - 11 + 8;
            final ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);
            itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
            itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, iX, iY, null);
        }
        RenderHelper.disableStandardItemLighting();
        this.itemRender.zLevel = 0.0F;
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }

    private static void drawTexturedRect(int x, int y, int textureX, int textureY, int width, int height, int zLevel) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder BufferBuilder = tessellator.getBuffer();
        BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder.pos(x, y + height, zLevel).tex((float) (textureX) * 0.00390625F, (float) (textureY + height) * 0.00390625F).endVertex();
        BufferBuilder.pos(x + width, y + height, zLevel).tex((float) (textureX + width) * 0.00390625F, (float) (textureY + height) * 0.00390625F).endVertex();
        BufferBuilder.pos(x + width, y, zLevel).tex((float) (textureX + width) * 0.00390625F, (float) (textureY) * 0.00390625F).endVertex();
        BufferBuilder.pos(x, y, zLevel).tex((float) (textureX) * 0.00390625F, (float) (textureY) * 0.00390625F).endVertex();
        tessellator.draw();
    }
}
