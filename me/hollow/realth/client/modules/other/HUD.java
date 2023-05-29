//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.other.gui.ScaledResolution
 *  net.minecraft.util.math.MathHelper
 *  org.apache.commons.lang3.StringUtils
 */
package me.hollow.realth.client.modules.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.*;
import me.hollow.realth.client.events.Render2DEvent;
import me.hollow.realth.client.events.TickEvent;
import me.hollow.realth.client.managers.SpeedManager;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.renderer.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.StringUtils;

@ModuleManifest(label="HUD", category=Module.Category.OTHER, listen=false, enabled = true)
public class HUD
        extends Module {
    private static final ItemStack totem;
    private final Setting<Boolean> arrayList = this.register(new Setting<Boolean>("Arraylist", true));
    public Setting<Boolean> colorSync = this.register(new Setting<Boolean>("Sync", false));

    private final Setting<Boolean> lowercase = this.register(new Setting<Boolean>("Lowercase", true));
    private final Setting<Boolean> watermark = this.register(new Setting<Boolean>("Watermark", true));
    private final Setting<Boolean> offsetWatermark = this.register(new Setting<Object>("OffsetWatermark", Boolean.valueOf(false), v -> this.watermark.getValue()));
    private final Setting<Boolean> customWatermark = this.register(new Setting<Object>("Custom", Boolean.valueOf(false), v -> this.watermark.getValue()));
    private final Setting<String> watermarkString = this.register(new Setting<Object>("CustomMark", "JordoHack", v -> this.customWatermark.getValue()));
    private final Setting<Boolean> watermark2 = this.register(new Setting<Boolean>("SecondMark", true));
    private final Setting<Integer> watermark2Y = this.register(new Setting<Object>("SecondMarkY", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(500), v -> this.watermark2.getValue()));
    private final Setting<Integer> watermark2X = this.register(new Setting<Object>("SecondMarkX", Integer.valueOf(60), Integer.valueOf(2), Integer.valueOf(500), v -> this.watermark2.getValue()));
    private final Setting<Boolean> coords = this.register(new Setting<Boolean>("Coordinates", true));
    private final Setting<Boolean> rotations = this.register(new Setting<Boolean>("Rotations", true));
    private final Setting<Boolean> welcomer = this.register(new Setting<Boolean>("Welcomer", true));
    private final Setting<String> welcomeMsg = this.register(new Setting<Object>("WelcomerMsg", "Hello %s :^)", v -> this.welcomer.getValue()));
    private final Setting<Boolean> speed = register(new Setting<Boolean>("Speed", true));
    private final Setting<Boolean> tps = register(new Setting<Boolean>("TPS", true));
    private final Setting<Boolean> fps = register(new Setting<Boolean>("FPS", true));
    private final Setting<Boolean> ping = register(new Setting<Boolean>("Ping", true));
    private final Setting<Boolean> potionEffects = register(new Setting<Boolean>("PotionEffects", true));
    private final Setting<Boolean> armor = register(new Setting<Boolean>("Armour", true));
    public final Setting<Boolean> shadow = register(new Setting<Boolean>("Text Shadow", false));

    private final Setting<Boolean> textRadar = this.register(new Setting<Boolean>("Text Radar" , false));
    private static HUD INSTANCE = new HUD();
    private Map<String, Integer> players = new HashMap<String, Integer>();
    private int color;
    public Map<Integer, Integer> colorMap = new HashMap<Integer, Integer>();

    public HUD() {
        INSTANCE = this;
    }

    public static HUD getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HUD();
        }
        return INSTANCE;
    }


    @Override
    public void onRender2D() {
        this.players = this.getTextRadarPlayers();
        this.color = JordoHack.INSTANCE.getColorManager().getColorAsInt();
        ScaledResolution resolution = new ScaledResolution(this.mc);
        final int height = JordoHack.INSTANCE.getFontManager().scaledHeight;
        final int width = JordoHack.INSTANCE.getFontManager().scaledWidth;
        if (this.watermark.getValue().booleanValue()) {
            final String text = this.customWatermark.getValue() != false ? this.watermarkString.getValue() + " " + JordoHack.VERSION : "TrollGod.CC " + JordoHack.VERSION;
            JordoHack.fontManager.drawString(HUD.getInstance().lowercase.getValue() != false ? text.toLowerCase() : text, 2.0f, this.offsetWatermark.getValue() != false ? 10.0f : 2.0f, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
        }
        if (this.watermark2.getValue().booleanValue()) {
            final String text = "TrollGod.CC";
            JordoHack.fontManager.drawString(HUD.getInstance().lowercase.getValue() != false ? text.toLowerCase() : text, this.watermark2X.getValue(),  this.watermark2Y.getValue(), JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
        }
        if (this.welcomer.getValue().booleanValue()) {
            this.drawCenteredString(StringUtils.replace((String)this.welcomeMsg.getValue(), (String)"%s", (String)this.mc.player.getName()), resolution.getScaledWidth() / 2, 2, JordoHack.INSTANCE.getColorManager().getColorAsInt());
        }
        if (coords.getValue()) {
            String facing;
            switch (mc.getRenderViewEntity().getHorizontalFacing()) {
                case NORTH:
                    facing = " \u00a77[\u00a7f-Z\u00a77]";
                    break;
                case SOUTH:
                    facing = " \u00a77[\u00a7f+Z\u00a77]";
                    break;
                case WEST:
                    facing = " \u00a77[\u00a7f-X\u00a77]";
                    break;
                case EAST:
                    facing = " \u00a77[\u00a7f+X\u00a77]";
                    break;
                default:
                    facing = " \u00a77[\u00a7fWTF\u00a77]";
            }
            JordoHack.fontManager.drawString("XYZ: \u00a7f" + String.format("%.2f", mc.player.posX) + "\u00a77, \u00a7f" + String.format("%.2f", mc.player.posY) + "\u00a77, \u00a7f" + String.format("%.2f", mc.player.posZ) + " \u00a77(\u00a7f" + String.format("%.2f", getDimensionCoord(mc.player.posX)) + "\u00a77, \u00a7f" + String.format("%.2f", getDimensionCoord(mc.player.posZ)) + "\u00a77)" + facing, 2, resolution.getScaledHeight() - 10, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
        }
        if (rotations.getValue()) {
            JordoHack.fontManager.drawString("Pitch:\u00a7f " + String.format("%.2f", MathHelper.wrapDegrees(mc.player.rotationPitch)), 2, resolution.getScaledHeight() - 20, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
            JordoHack.fontManager.drawString("Yaw:\u00a7f " + String.format("%.2f", MathHelper.wrapDegrees(mc.player.rotationYaw)), 2, resolution.getScaledHeight() - 30, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
        }
        int daFunnies = 10;
        if (potionEffects.getValue()) {
            for (PotionEffect effect : mc.player.getActivePotionEffects()) {
                final Potion potion = effect.getPotion();
                String fullName = I18n.format(effect.getPotion().getName());

                if (effect.getAmplifier() == 1) {
                    fullName = fullName + " 2";
                } else if (effect.getAmplifier() == 2) {
                    fullName = fullName + " 3";
                } else if (effect.getAmplifier() == 3) {
                    fullName = fullName + " 4";
                }

                String s = Potion.getPotionDurationString(effect, 1.0F);
                fullName = fullName + ": " + ChatFormatting.GRAY + s;
                JordoHack.fontManager.drawString(fullName, resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(fullName) - 2, resolution.getScaledHeight() - daFunnies, potion.getLiquidColor(), shadow.getValue());
                daFunnies += 10;
            }
        }
        if (textRadar.getValue()) {
            drawTextRadar(8);
        }
        if (speed.getValue()) {
            String speed = "Speed:\u00a7f " + String.format("%.2f", JordoHack.INSTANCE.getSpeedManager().getPlayerSpeed(mc.player)) + "km/h";
            JordoHack.fontManager.drawString(speed, resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(speed) - 2, resolution.getScaledHeight() - daFunnies, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
            daFunnies += 10;
        }
        if (ping.getValue()) {
            final String pingString = "Ping: \u00a7f" + JordoHack.INSTANCE.getTpsManager().getPing();
            JordoHack.fontManager.drawString(pingString, resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(pingString) - 2, resolution.getScaledHeight() - daFunnies, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
            daFunnies +=10;
        }
        if (tps.getValue()) {
            final String tpsString = "TPS: \u00a7f" + String.format("%.2f", TickRate.TPS);
            JordoHack.fontManager.drawString(tpsString, resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(tpsString) - 2, resolution.getScaledHeight() - daFunnies, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
            daFunnies += 10;
        }
        if (fps.getValue()) {
            final String fpsString = "FPS: \u00a7f" + mc.getDebugFPS();
            JordoHack.fontManager.drawString(fpsString, resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(fpsString) - 2, resolution.getScaledHeight() - daFunnies, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
        }

        if (this.arrayList.getValue().booleanValue()) {
            ArrayList<Module> modules = new ArrayList<Module>();
            JordoHack.INSTANCE.getModuleManager().getModules().forEach(m -> {
                if (m.isEnabled() && m.drawn.getValue().booleanValue()) {
                    modules.add((Module)m);
                }
            });
            modules.sort(Comparator.comparingDouble(m -> -JordoHack.fontManager.getStringWidth(this.getFullName((Module)m))));
            int offset = 2;
            int size = modules.size();
            for (int i = 0; i < size; ++i) {
                Module module = (Module)modules.get(i);
                String name = this.getFullName(module);
                float x = resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(name) - 2;
                if (Colours.getInstance().rainbow.getValue()) {
                    this.drawRainbowString(name, x, offset, this.rainbow(x, offset));
                } else {
                    JordoHack.fontManager.drawString(name, resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(name) - 2, offset, JordoHack.INSTANCE.getColorManager().getColorAsInt(), shadow.getValue());
                }
                offset += 10;
            }
        }
        if (this.armor.getValue()) {
            this.renderArmorHUD(true);
        }
    }

    private int rainbow(double x, double y) {
        double scale = 1.0f / (float)Colours.getInstance().factor.getValue().intValue();
        double speed = Colours.getInstance().speed.getValue().intValue();
        double d = -0.2;
        double pos = (x * d + y * d) * scale + (double)System.currentTimeMillis() % speed * 2.0 * Math.PI / speed;
        return 0xFF000000 | MathHelper.clamp((int)MathHelper.floor((double)(255.0 * (0.5 + Math.sin(0.0 + pos)))), (int)0, (int)255) << 16 | MathHelper.clamp((int)MathHelper.floor((double)(255.0 * (0.5 + Math.sin(2.0943951023931953 + pos)))), (int)0, (int)255) << 8 | MathHelper.clamp((int)MathHelper.floor((double)(255.0 * (0.5 + Math.sin(4.1887902047863905 + pos)))), (int)0, (int)255);
    }
    public Map<String, Integer> getTextRadarPlayers() {
        return EntityUtil.getTextRadarPlayers();
    }
    public void drawTextRadar(final int yOffset) {
        if (!this.players.isEmpty()) {
            int y = JordoHack.INSTANCE.getFontManager().getStringHeight() + 7 + yOffset;
            for (final Map.Entry<String, Integer> player : this.players.entrySet()) {
                final String text = player.getKey() + " ";
                final int textheight = JordoHack.INSTANCE.getFontManager().getStringHeight() + 1;
                JordoHack.INSTANCE.getFontManager().drawString(text, 2.0f, (float) y, this.color , true);
                y += textheight;
            }
        }
    }

    public void drawRainbowString(String text, float x, float y, int startColor) {
        Color currentColor = new Color(startColor);
        float hueIncrement = 1.0f / (float)Colours.getInstance().factor.getValue().intValue();
        float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
        float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
        float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        for (int i = 0; i < text.length(); ++i) {
            char currentChar = text.charAt(i);
            char nextChar = text.charAt(MathUtil.clamp(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                shouldRainbow = false;
            }
            if (shouldContinue) {
                shouldContinue = false;
                continue;
            }
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                String escapeString = text.substring(i);
                JordoHack.fontManager.drawString(escapeString, x + (float)currentWidth, y, Color.WHITE.getRGB(), shadow.getValue());
                break;
            }
            JordoHack.fontManager.drawString(String.valueOf(currentChar).equals("\u00a7") ? "" : String.valueOf(currentChar), x + (float)currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB(), shadow.getValue());
            if (String.valueOf(currentChar).equals("\u00a7")) {
                shouldContinue = true;
            }
            currentWidth += JordoHack.fontManager.getStringWidth(String.valueOf(currentChar));
            if (String.valueOf(currentChar).equals(" ")) continue;
            currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
            currentHue += hueIncrement;
        }
    }

    public void drawCenteredString(String text, int x, int y, int color) {
        JordoHack.fontManager.drawString(text, x - JordoHack.fontManager.getStringWidth(text) / 2, y, color, shadow.getValue());
    }
    private String getFullName(Module m) {
        if (m.getSuffix().length() == 0) {
            return m.getLabel();
        }
        return m.getLabel() + (Colours.getInstance().rainbow.getValue() ? "\u00a7r" : ChatFormatting.WHITE) + m.getSuffix();
    }

    public void renderArmorHUD(final boolean percent) {
        ScaledResolution resolution = new ScaledResolution(mc);
        final int width = resolution.getScaledWidth();
        final int height = resolution.getScaledHeight();
        GlStateManager.enableTexture2D();
        final int i = width / 2;
        int iteration = 0;
        final int y = height - 55 - ((HUD.mc.player.isInWater() && HUD.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
        for (final ItemStack is : HUD.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            mc.getRenderItem().zLevel = 200.0f;
            mc.getRenderItem().renderItemAndEffectIntoGUI(is, x, y);
            mc.getRenderItem().renderItemOverlayIntoGUI(HUD.mc.fontRenderer, is, x, y, "");
            mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            JordoHack.fontManager.drawString(s, (float)(x + 19 - 2 - JordoHack.fontManager.getStringWidth(s)), (float)(y + 9), 16777215, shadow.getValue());
            if (!percent) {
                continue;
            }
            int dmg = 0;
            final int itemDurability = is.getMaxDamage() - is.getItemDamage();
            final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
            final float red = 1.0f - green;
            if (percent) {
                dmg = 100 - (int)(red * 100.0f);
            }
            else {
                dmg = itemDurability;
            }
            JordoHack.fontManager.drawString(dmg + "", (float)(x + 8 - JordoHack.fontManager.getStringWidth(dmg + "") / 2), (float)(y - 10), is.getItem().getRGBDurabilityForDisplay(is), shadow.getValue());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    public static enum Mode {
        CATEGORY,
        SYNC,
        RAINBOW;

    }
    static {
        totem = new ItemStack(Items.TOTEM_OF_UNDYING);
    }
    public double getDimensionCoord(double coord) {
        return mc.player.dimension == 0 ? coord / 8 : coord * 8;
    }

}

