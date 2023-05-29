//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.other.renderer.GlStateManager
 *  net.minecraft.other.renderer.RenderHelper
 *  net.minecraft.other.renderer.culling.Frustum
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemShield
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.text.TextFormatting
 */
package me.hollow.realth.client.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.realth.client.modules.other.Colours;
import net.minecraft.client.renderer.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.mixin.mixins.render.IRenderManager;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.EntityUtil;
import me.hollow.realth.api.util.RenderUtil;
import me.hollow.realth.client.events.Render3DEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

@ModuleManifest(label="Nametags", category=Module.Category.RENDER, listen=false)
public class Nametags
extends Module {
    private final Setting<Boolean> armor = register(new Setting<>("Armor", true));
    private final Setting<Boolean> armorenchants = register(new Setting<>("Enchants", false, v -> this.armor.getValue()));
    private final Setting<Boolean> shortt = register(new Setting<>("Short", false, v -> this.armorenchants.getValue() && this.armor.getValue()));
    private final Setting<Boolean> ping = register(new Setting<>("Ping", true));
    private final Setting<Boolean> totemPops = register(new Setting<>("Pops", true));
    private final Setting<Boolean> rect = register(new Setting<>("Rect", true));
    private final Setting<Boolean> outline = register(new Setting<>("Outline", true, v -> this.rect.getValue()));
    private final Setting<Boolean> sneak = this.register(new Setting<Boolean>("SneakColor", false));
    private final Setting<Boolean> gamemode = this.register(new Setting<Boolean>("Gamemode", true));
    private final Setting<Float> scaling = register(new Setting<>("Size", 5.9f, 0.1f, 20.0f));
    private final Setting<Float> factor = register(new Setting<>("Factor", 0.5f, 0.1f, 1.0f));
    private static Nametags INSTANCE = new Nametags();

    private final ICamera camera = new Frustum();
    private final IRenderManager renderManager = (IRenderManager) mc.getRenderManager();

    public Nametags() {
        INSTANCE = this;
    }

    public static Nametags getInstance() {
        return INSTANCE;
    }

    @Override
    public void onRender3D() {
        if (mc.getRenderViewEntity() == null) {
            return;
        }
        camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);
        float partialTicks = mc.getRenderPartialTicks();
        final int size = mc.world.playerEntities.size();
        for (int i = 0; i < size; ++i) {
            final EntityPlayer player = mc.world.playerEntities.get(i);
            if (camera.isBoundingBoxInFrustum(player.getEntityBoundingBox())) {
                if (player != mc.player && !player.isDead && player.getHealth() > 0) {
                    final double x = interpolate(player.lastTickPosX, player.posX, partialTicks) - renderManager.getRenderPosX();
                    final double y = interpolate(player.lastTickPosY, player.posY, partialTicks) - renderManager.getRenderPosY();
                    final double z = interpolate(player.lastTickPosZ, player.posZ, partialTicks) - renderManager.getRenderPosZ();

                    double tempY = y;
                    tempY += player.isSneaking() ? 0.5D : 0.7D;
                    final Entity camera = mc.getRenderViewEntity();
                    final double originalPositionX = camera.posX;
                    final double originalPositionY = camera.posY;
                    final double originalPositionZ = camera.posZ;
                    camera.posX = interpolate(camera.prevPosX, camera.posX, partialTicks);
                    camera.posY = interpolate(camera.prevPosY, camera.posY, partialTicks);
                    camera.posZ = interpolate(camera.prevPosZ, camera.posZ, partialTicks);

                    final String displayTag = getDisplayTag(player);
                    final double distance = camera.getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z + mc.getRenderManager().viewerPosZ);
                    final int width = JordoHack.INSTANCE.fontManager.getStringWidth(displayTag) >> 1; // >> 1 is the same as / 2 but way faster
                    double scale = (0.0018 + scaling.getValue() * (distance * factor.getValue())) / 1000.0;

                    if (distance <= 8) {
                        scale = 0.0245D;
                    }

                    GlStateManager.pushMatrix();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.disableLighting();
                    GlStateManager.translate((float) x, (float) tempY + 1.4F, (float) z);
                    GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(-scale, -scale, scale);
                    GlStateManager.disableDepth();
                    RenderUtil.drawRect(-width - 2, -10, width + 2F, 1.5F, 0x00000000);

                    if (rect.getValue()) {
                        RenderUtil.drawRect(-width - 2, -10, width + 2F, 1.5F, 0x60000000);
                        this.drawOutlineRect(-width - 2, -10, width + 2F, 1.5F, (JordoHack.INSTANCE.getFriendManager().isFriend(player) ? 0xFF6CFFFD : Color.BLACK.getRGB()));
                    }

                    if (rect.getValue() == true && outline.getValue()) {
                        int outlineColor = new Color(Colours.getInstance().red.getValue(), Colours.getInstance().green.getValue(), Colours.getInstance().blue.getValue()).getRGB();
                        this.drawOutlineRect(-width - 2, -10, width + 2F, 1.5F, (JordoHack.INSTANCE.getFriendManager().isFriend(player) ? 0xFF6CFFFD : outlineColor));
                    }

                    if (armor.getValue()) {
                        GlStateManager.pushMatrix();
                        int xOffset = -8;
                        final int invsize = player.inventory.armorInventory.size();
                        for (int j = 0; j < invsize; ++j) {
                            xOffset -= 8;
                        }

                        xOffset -= 8;
                        final ItemStack renderOffhand = player.getHeldItemMainhand().copy();

                        this.renderItemStack(renderOffhand, xOffset - 2);
                        xOffset += 16;

                        for (int j = invsize - 1; j > -1; j--) {
                            this.renderItemStack(player.inventory.armorInventory.get(j).copy(), xOffset);
                            xOffset += 16;
                        }

                        this.renderItemStack(player.getHeldItemOffhand().copy(), xOffset);

                        GlStateManager.popMatrix();
                    }


                    JordoHack.fontManager.drawString(displayTag, -width, -8, this.getDisplayColour(player), false);


                    camera.posX = originalPositionX;
                    camera.posY = originalPositionY;
                    camera.posZ = originalPositionZ;
                    GlStateManager.enableDepth();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public void drawOutlineRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(1.4f);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) x, (double) h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double) w, (double) h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double) w, (double) y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private void renderItemStack(ItemStack stack, int x) {
        GlStateManager.depthMask(true);
        GlStateManager.clear(GL11.GL_ACCUM);

        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -150.0F;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();

        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -26);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, -26);

        mc.getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();

        GlStateManager.enableCull();
        GlStateManager.enableAlpha();

        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.disableDepth();
        renderEnchantmentText(stack, x);
        GlStateManager.enableDepth();
        GlStateManager.scale(2F, 2F, 2F);
    }

    private void renderEnchantmentText(ItemStack stack, int x) {
        int enchantmentY = -26 - 8;

        final NBTTagList enchants = stack.getEnchantmentTagList();
        final int tagCount = enchants.tagCount();
        for (int index = 0; index < tagCount; ++index) {
            final short id = enchants.getCompoundTagAt(index).getShort("id");
            final short level = enchants.getCompoundTagAt(index).getShort("lvl");
            final Enchantment enc = Enchantment.getEnchantmentByID(id);
            if (this.armorenchants.getValue()) {
                if (enc != null) {
                    if (this.armorenchants.getValue() && this.shortt.getValue()) {
                        if (enc.getName().contains("fall") || !(enc.getName().contains("all") || enc.getName().contains("explosion")))
                            continue;
                    }
                    JordoHack.fontManager.drawString(enc.isCurse() ? ChatFormatting.RED + enc.getTranslatedName(level).substring(11).substring(0, 2).toLowerCase() + level : enc.getTranslatedName(level).substring(0, 2).toLowerCase() + level, x * 2, enchantmentY, -1, false);
                    enchantmentY -= 8;
                }
            }
        }

        if (hasDurability(stack)) {
            final int percent = getRoundedDamage(stack);
            String color;
            if(percent >= 65) {
                color = ChatFormatting.GREEN.toString();
            } else if(percent >= 30) {
                color = ChatFormatting.YELLOW.toString();
            } else {
                color = ChatFormatting.RED.toString();
            }
            JordoHack.fontManager.drawString(color + percent + "%", x << 1 /*bit shift instead of multiplying by 2*/, enchantmentY, 0xFFFFFFFF, false);
        }
    }

    public static int getItemDamage(ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    public static float getDamageInPercent(ItemStack stack) {
        return (getItemDamage(stack) / (float)stack.getMaxDamage()) * 100;
    }

    public static int getRoundedDamage(ItemStack stack) {
        return (int) getDamageInPercent(stack);
    }

    public static boolean hasDurability(ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }


    private String getDisplayTag(final EntityPlayer player) {
        final float health = EntityUtil.getHealth(player);
        String color;

        if (health > 18) {
            color = ChatFormatting.GREEN.toString();
        } else if (health > 16) {
            color = ChatFormatting.DARK_GREEN.toString();
        } else if (health > 12) {
            color = ChatFormatting.YELLOW.toString();
        } else if (health > 8) {
            color = ChatFormatting.GOLD.toString();
        } else if (health > 5) {
            color = ChatFormatting.RED.toString();
        } else {
            color = ChatFormatting.DARK_RED.toString();
        }

        String gamemodeStr = "";
        if (gamemode.getValue()) {
            if (player.isCreative()) {
                gamemodeStr += "[C] ";
            } else if (player.isSpectator() || player.isInvisible()) {
                gamemodeStr += "[SP] ";
            } else {
                gamemodeStr += "[S] ";
            }
        }

        String pingStr = "";
        if (ping.getValue()) {
            try {
                final int responseTime = Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                pingStr += responseTime + "ms";
            } catch (Exception ignored) {
                pingStr += "-1ms";
            }
        }

        String popStr = "";
        if (totemPops.getValue()) {
            final Map<String, Integer> registry = JordoHack.INSTANCE.getPopManager().getPopMap();
            popStr += registry.containsKey(player.getName()) ? " -" + registry.get(player.getName()) : "";
        }

        return player.getName() + " " + gamemodeStr + pingStr + " " + color + (int) health + ChatFormatting.RED + popStr;
    }

    private int getDisplayColour (EntityPlayer player){
        if (JordoHack.INSTANCE.getFriendManager().isFriend(player)) {
            return 0xFF6CFFFD;
        }
        if (player.isSneaking() && this.sneak.getValue()) {
            return 3355494;
        }
        return 0xFFFFFFFF;
    }

    private static double interpolate ( double previous, double current, float delta){
        return (previous + (current - previous) * delta);
    }
}


