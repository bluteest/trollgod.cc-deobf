//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.api.mixin.mixins.render.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.player.*;
import me.hollow.realth.*;
import java.awt.*;
import me.hollow.realth.client.modules.other.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.enchantment.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import me.hollow.realth.api.util.*;
import net.minecraft.client.network.*;
import java.util.*;

@ModuleManifest(label = "Nametags", category = Module.Category.RENDER, listen = false)
public class Nametags extends Module
{
    private final Setting<Boolean> armor;
    private final Setting<Boolean> armorenchants;
    private final Setting<Boolean> shortt;
    private final Setting<Boolean> ping;
    private final Setting<Boolean> totemPops;
    private final Setting<Boolean> rect;
    private final Setting<Boolean> outline;
    private final Setting<Boolean> sneak;
    private final Setting<Boolean> gamemode;
    private final Setting<Float> scaling;
    private final Setting<Float> factor;
    private static Nametags INSTANCE;
    private final ICamera camera;
    private final IRenderManager renderManager;
    
    public Nametags() {
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", (Object)true));
        this.armorenchants = (Setting<Boolean>)this.register(new Setting("Enchants", (Object)false, v -> (boolean)this.armor.getValue()));
        this.shortt = (Setting<Boolean>)this.register(new Setting("Short", (Object)false, v -> (boolean)this.armorenchants.getValue() && (boolean)this.armor.getValue()));
        this.ping = (Setting<Boolean>)this.register(new Setting("Ping", (Object)true));
        this.totemPops = (Setting<Boolean>)this.register(new Setting("Pops", (Object)true));
        this.rect = (Setting<Boolean>)this.register(new Setting("Rect", (Object)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (Object)true, v -> (boolean)this.rect.getValue()));
        this.sneak = (Setting<Boolean>)this.register(new Setting("SneakColor", (Object)false));
        this.gamemode = (Setting<Boolean>)this.register(new Setting("Gamemode", (Object)true));
        this.scaling = (Setting<Float>)this.register(new Setting("Size", (Object)5.9f, (Object)0.1f, (Object)20.0f));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (Object)0.5f, (Object)0.1f, (Object)1.0f));
        this.camera = (ICamera)new Frustum();
        this.renderManager = (IRenderManager)Nametags.mc.getRenderManager();
        Nametags.INSTANCE = this;
    }
    
    public static Nametags getInstance() {
        return Nametags.INSTANCE;
    }
    
    public void onRender3D() {
        if (Nametags.mc.getRenderViewEntity() == null) {
            return;
        }
        this.camera.setPosition(Nametags.mc.getRenderViewEntity().posX, Nametags.mc.getRenderViewEntity().posY, Nametags.mc.getRenderViewEntity().posZ);
        final float partialTicks = Nametags.mc.getRenderPartialTicks();
        for (int size = Nametags.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = Nametags.mc.world.playerEntities.get(i);
            if (this.camera.isBoundingBoxInFrustum(player.getEntityBoundingBox()) && player != Nametags.mc.player && !player.isDead && player.getHealth() > 0.0f) {
                final double x = interpolate(player.lastTickPosX, player.posX, partialTicks) - this.renderManager.getRenderPosX();
                final double y = interpolate(player.lastTickPosY, player.posY, partialTicks) - this.renderManager.getRenderPosY();
                final double z = interpolate(player.lastTickPosZ, player.posZ, partialTicks) - this.renderManager.getRenderPosZ();
                double tempY = y;
                tempY += (player.isSneaking() ? 0.5 : 0.7);
                final Entity camera = Nametags.mc.getRenderViewEntity();
                final double originalPositionX = camera.posX;
                final double originalPositionY = camera.posY;
                final double originalPositionZ = camera.posZ;
                camera.posX = interpolate(camera.prevPosX, camera.posX, partialTicks);
                camera.posY = interpolate(camera.prevPosY, camera.posY, partialTicks);
                camera.posZ = interpolate(camera.prevPosZ, camera.posZ, partialTicks);
                final String displayTag = this.getDisplayTag(player);
                final double distance = camera.getDistance(x + Nametags.mc.getRenderManager().viewerPosX, y + Nametags.mc.getRenderManager().viewerPosY, z + Nametags.mc.getRenderManager().viewerPosZ);
                final JordoHack instance = JordoHack.INSTANCE;
                final int width = JordoHack.fontManager.getStringWidth(displayTag) >> 1;
                double scale = (0.0018 + (float)this.scaling.getValue() * (distance * (float)this.factor.getValue())) / 1000.0;
                if (distance <= 8.0) {
                    scale = 0.0245;
                }
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.disableLighting();
                GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
                GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(Nametags.mc.getRenderManager().playerViewX, (Nametags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
                GlStateManager.scale(-scale, -scale, scale);
                GlStateManager.disableDepth();
                RenderUtil.drawRect((float)(-width - 2), -10.0f, width + 2.0f, 1.5f, 0);
                if (this.rect.getValue()) {
                    RenderUtil.drawRect((float)(-width - 2), -10.0f, width + 2.0f, 1.5f, 1610612736);
                    this.drawOutlineRect((float)(-width - 2), -10.0f, width + 2.0f, 1.5f, JordoHack.INSTANCE.getFriendManager().isFriend(player) ? -9633795 : Color.BLACK.getRGB());
                }
                if ((boolean)this.rect.getValue() && (boolean)this.outline.getValue()) {
                    final int outlineColor = new Color((int)Colours.getInstance().red.getValue(), (int)Colours.getInstance().green.getValue(), (int)Colours.getInstance().blue.getValue()).getRGB();
                    this.drawOutlineRect((float)(-width - 2), -10.0f, width + 2.0f, 1.5f, JordoHack.INSTANCE.getFriendManager().isFriend(player) ? -9633795 : outlineColor);
                }
                if (this.armor.getValue()) {
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
                    for (int k = invsize - 1; k > -1; --k) {
                        this.renderItemStack(((ItemStack)player.inventory.armorInventory.get(k)).copy(), xOffset);
                        xOffset += 16;
                    }
                    this.renderItemStack(player.getHeldItemOffhand().copy(), xOffset);
                    GlStateManager.popMatrix();
                }
                JordoHack.fontManager.drawString(displayTag, (float)(-width), -8.0f, this.getDisplayColour(player));
                camera.posX = originalPositionX;
                camera.posY = originalPositionY;
                camera.posZ = originalPositionZ;
                GlStateManager.enableDepth();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
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
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    private void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        Nametags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        Nametags.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -26);
        Nametags.mc.getRenderItem().renderItemOverlays(Nametags.mc.fontRenderer, stack, x, -26);
        Nametags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchantmentText(stack, x);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private void renderEnchantmentText(final ItemStack stack, final int x) {
        int enchantmentY = -34;
        final NBTTagList enchants = stack.getEnchantmentTagList();
        for (int tagCount = enchants.tagCount(), index = 0; index < tagCount; ++index) {
            final short id = enchants.getCompoundTagAt(index).getShort("id");
            final short level = enchants.getCompoundTagAt(index).getShort("lvl");
            final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
            if ((boolean)this.armorenchants.getValue() && enc != null) {
                if ((boolean)this.armorenchants.getValue() && (boolean)this.shortt.getValue()) {
                    if (enc.getName().contains("fall")) {
                        continue;
                    }
                    if (!enc.getName().contains("all") && !enc.getName().contains("explosion")) {
                        continue;
                    }
                }
                JordoHack.fontManager.drawString(enc.isCurse() ? (ChatFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 2).toLowerCase() + level) : (enc.getTranslatedName((int)level).substring(0, 2).toLowerCase() + level), (float)(x * 2), (float)enchantmentY, -1);
                enchantmentY -= 8;
            }
        }
        if (hasDurability(stack)) {
            final int percent = getRoundedDamage(stack);
            String color;
            if (percent >= 65) {
                color = ChatFormatting.GREEN.toString();
            }
            else if (percent >= 30) {
                color = ChatFormatting.YELLOW.toString();
            }
            else {
                color = ChatFormatting.RED.toString();
            }
            JordoHack.fontManager.drawString(color + percent + "%", (float)(x << 1), (float)enchantmentY, -1);
        }
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    private String getDisplayTag(final EntityPlayer player) {
        final float health = EntityUtil.getHealth(player);
        String color;
        if (health > 18.0f) {
            color = ChatFormatting.GREEN.toString();
        }
        else if (health > 16.0f) {
            color = ChatFormatting.DARK_GREEN.toString();
        }
        else if (health > 12.0f) {
            color = ChatFormatting.YELLOW.toString();
        }
        else if (health > 8.0f) {
            color = ChatFormatting.GOLD.toString();
        }
        else if (health > 5.0f) {
            color = ChatFormatting.RED.toString();
        }
        else {
            color = ChatFormatting.DARK_RED.toString();
        }
        String gamemodeStr = "";
        if (this.gamemode.getValue()) {
            if (player.isCreative()) {
                gamemodeStr += "[C] ";
            }
            else if (player.isSpectator() || player.isInvisible()) {
                gamemodeStr += "[SP] ";
            }
            else {
                gamemodeStr += "[S] ";
            }
        }
        String pingStr = "";
        if (this.ping.getValue()) {
            try {
                final int responseTime = Objects.requireNonNull(Nametags.mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                pingStr = pingStr + responseTime + "ms";
            }
            catch (Exception ignored) {
                pingStr += "-1ms";
            }
        }
        String popStr = "";
        if (this.totemPops.getValue()) {
            final Map<String, Integer> registry = (Map<String, Integer>)JordoHack.INSTANCE.getPopManager().getPopMap();
            popStr += (registry.containsKey(player.getName()) ? (" -" + registry.get(player.getName())) : "");
        }
        return player.getName() + " " + gamemodeStr + pingStr + " " + color + (int)health + ChatFormatting.RED + popStr;
    }
    
    private int getDisplayColour(final EntityPlayer player) {
        if (JordoHack.INSTANCE.getFriendManager().isFriend(player)) {
            return -9633795;
        }
        if (player.isSneaking() && (boolean)this.sneak.getValue()) {
            return 3355494;
        }
        return -1;
    }
    
    private static double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
    
    static {
        Nametags.INSTANCE = new Nametags();
    }
}
