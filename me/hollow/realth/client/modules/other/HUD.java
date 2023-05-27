//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.other;

import me.hollow.realth.client.modules.*;
import net.minecraft.item.*;
import me.hollow.realth.api.property.*;
import net.minecraft.client.gui.*;
import me.hollow.realth.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.math.*;
import net.minecraft.client.resources.*;
import net.minecraft.potion.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import java.util.*;
import java.awt.*;
import me.hollow.realth.api.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;

@ModuleManifest(label = "HUD", category = Module.Category.OTHER, listen = false, enabled = true)
public class HUD extends Module
{
    private static final ItemStack totem;
    private final Setting<Boolean> arrayList;
    private final Setting<Boolean> lowercase;
    private final Setting<Boolean> watermark;
    private final Setting<Boolean> offsetWatermark;
    private final Setting<Boolean> customWatermark;
    private final Setting<String> watermarkString;
    private final Setting<Boolean> watermark2;
    private final Setting<Integer> watermark2Y;
    private final Setting<Integer> watermark2X;
    private final Setting<Boolean> coords;
    private final Setting<Boolean> rotations;
    private final Setting<Boolean> welcomer;
    private final Setting<String> welcomeMsg;
    private final Setting<Boolean> speed;
    private final Setting<Boolean> tps;
    private final Setting<Boolean> fps;
    private final Setting<Boolean> ping;
    private final Setting<Boolean> potionEffects;
    private final Setting<Boolean> armor;
    private static HUD INSTANCE;
    
    public HUD() {
        this.arrayList = (Setting<Boolean>)this.register(new Setting("Arraylist", (Object)true));
        this.lowercase = (Setting<Boolean>)this.register(new Setting("Lowercase", (Object)true));
        this.watermark = (Setting<Boolean>)this.register(new Setting("Watermark", (Object)true));
        this.offsetWatermark = (Setting<Boolean>)this.register(new Setting("OffsetWatermark", (Object)false, v -> (boolean)this.watermark.getValue()));
        this.customWatermark = (Setting<Boolean>)this.register(new Setting("Custom", (Object)false, v -> (boolean)this.watermark.getValue()));
        this.watermarkString = (Setting<String>)this.register(new Setting("CustomMark", (Object)"JordoHack", v -> (boolean)this.customWatermark.getValue()));
        this.watermark2 = (Setting<Boolean>)this.register(new Setting("SecondMark", (Object)true));
        this.watermark2Y = (Setting<Integer>)this.register(new Setting("SecondMarkY", (Object)60, (Object)0, (Object)500, v -> (boolean)this.watermark2.getValue()));
        this.watermark2X = (Setting<Integer>)this.register(new Setting("SecondMarkX", (Object)60, (Object)2, (Object)500, v -> (boolean)this.watermark2.getValue()));
        this.coords = (Setting<Boolean>)this.register(new Setting("Coordinates", (Object)true));
        this.rotations = (Setting<Boolean>)this.register(new Setting("Rotations", (Object)true));
        this.welcomer = (Setting<Boolean>)this.register(new Setting("Welcomer", (Object)true));
        this.welcomeMsg = (Setting<String>)this.register(new Setting("WelcomerMsg", (Object)"Hello %s :^)", v -> (boolean)this.welcomer.getValue()));
        this.speed = (Setting<Boolean>)this.register(new Setting("Speed", (Object)true));
        this.tps = (Setting<Boolean>)this.register(new Setting("TPS", (Object)true));
        this.fps = (Setting<Boolean>)this.register(new Setting("FPS", (Object)true));
        this.ping = (Setting<Boolean>)this.register(new Setting("Ping", (Object)true));
        this.potionEffects = (Setting<Boolean>)this.register(new Setting("PotionEffects", (Object)true));
        this.armor = (Setting<Boolean>)this.register(new Setting("Armour", (Object)true));
        HUD.INSTANCE = this;
    }
    
    public static HUD getInstance() {
        if (HUD.INSTANCE == null) {
            HUD.INSTANCE = new HUD();
        }
        return HUD.INSTANCE;
    }
    
    public void onRender2D() {
        final ScaledResolution resolution = new ScaledResolution(HUD.mc);
        if (this.watermark.getValue()) {
            final String text = this.customWatermark.getValue() ? ((String)this.watermarkString.getValue() + " " + "v2.0") : "TrollGod.CC v2.0";
            JordoHack.fontManager.drawString(((boolean)getInstance().lowercase.getValue()) ? text.toLowerCase() : text, 2.0f, ((boolean)this.offsetWatermark.getValue()) ? 10.0f : 2.0f, JordoHack.INSTANCE.getColorManager().getColorAsInt());
        }
        if (this.watermark2.getValue()) {
            final String text = "TrollGod.CC";
            JordoHack.fontManager.drawString(((boolean)getInstance().lowercase.getValue()) ? "TrollGod.CC".toLowerCase() : "TrollGod.CC", (float)(int)this.watermark2X.getValue(), (float)(int)this.watermark2Y.getValue(), JordoHack.INSTANCE.getColorManager().getColorAsInt());
        }
        if (this.welcomer.getValue()) {
            this.drawCenteredString(StringUtils.replace((String)this.welcomeMsg.getValue(), "%s", HUD.mc.player.getName()), resolution.getScaledWidth() / 2, 2, JordoHack.INSTANCE.getColorManager().getColorAsInt());
        }
        if (this.coords.getValue()) {
            String facing = null;
            switch (HUD.mc.getRenderViewEntity().getHorizontalFacing()) {
                case NORTH: {
                    facing = " §7[§f-Z§7]";
                    break;
                }
                case SOUTH: {
                    facing = " §7[§f+Z§7]";
                    break;
                }
                case WEST: {
                    facing = " §7[§f-X§7]";
                    break;
                }
                case EAST: {
                    facing = " §7[§f+X§7]";
                    break;
                }
                default: {
                    facing = " §7[§fWTF§7]";
                    break;
                }
            }
            JordoHack.fontManager.drawString("XYZ: §f" + String.format("%.2f", HUD.mc.player.posX) + "§7, §f" + String.format("%.2f", HUD.mc.player.posY) + "§7, §f" + String.format("%.2f", HUD.mc.player.posZ) + " §7(§f" + String.format("%.2f", this.getDimensionCoord(HUD.mc.player.posX)) + "§7, §f" + String.format("%.2f", this.getDimensionCoord(HUD.mc.player.posZ)) + "§7)" + facing, 2.0f, (float)(resolution.getScaledHeight() - 10), JordoHack.INSTANCE.getColorManager().getColorAsInt());
        }
        if (this.rotations.getValue()) {
            JordoHack.fontManager.drawString("Pitch:§f " + String.format("%.2f", MathHelper.wrapDegrees(HUD.mc.player.rotationPitch)), 2.0f, (float)(resolution.getScaledHeight() - 20), JordoHack.INSTANCE.getColorManager().getColorAsInt());
            JordoHack.fontManager.drawString("Yaw:§f " + String.format("%.2f", MathHelper.wrapDegrees(HUD.mc.player.rotationYaw)), 2.0f, (float)(resolution.getScaledHeight() - 30), JordoHack.INSTANCE.getColorManager().getColorAsInt());
        }
        int daFunnies = 10;
        if (this.potionEffects.getValue()) {
            for (final PotionEffect effect : HUD.mc.player.getActivePotionEffects()) {
                final Potion potion = effect.getPotion();
                String fullName = I18n.format(effect.getPotion().getName(), new Object[0]);
                if (effect.getAmplifier() == 1) {
                    fullName += " 2";
                }
                else if (effect.getAmplifier() == 2) {
                    fullName += " 3";
                }
                else if (effect.getAmplifier() == 3) {
                    fullName += " 4";
                }
                final String s = Potion.getPotionDurationString(effect, 1.0f);
                fullName = fullName + ": " + ChatFormatting.GRAY + s;
                JordoHack.fontManager.drawString(fullName, (float)(resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(fullName) - 2), (float)(resolution.getScaledHeight() - daFunnies), potion.getLiquidColor());
                daFunnies += 10;
            }
        }
        if (this.speed.getValue()) {
            final String speed = "Speed:§f " + String.format("%.2f", JordoHack.INSTANCE.getSpeedManager().getPlayerSpeed((EntityPlayer)HUD.mc.player)) + "km/h";
            JordoHack.fontManager.drawString(speed, (float)(resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(speed) - 2), (float)(resolution.getScaledHeight() - daFunnies), JordoHack.INSTANCE.getColorManager().getColorAsInt());
            daFunnies += 10;
        }
        if (this.ping.getValue()) {
            final String pingString = "Ping: §f" + JordoHack.INSTANCE.getTpsManager().getPing();
            JordoHack.fontManager.drawString(pingString, (float)(resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(pingString) - 2), (float)(resolution.getScaledHeight() - daFunnies), JordoHack.INSTANCE.getColorManager().getColorAsInt());
            daFunnies += 10;
        }
        if (this.tps.getValue()) {
            final String tpsString = "TPS: §f" + String.format("%.2f", TickRate.TPS);
            JordoHack.fontManager.drawString(tpsString, (float)(resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(tpsString) - 2), (float)(resolution.getScaledHeight() - daFunnies), JordoHack.INSTANCE.getColorManager().getColorAsInt());
            daFunnies += 10;
        }
        if (this.fps.getValue()) {
            final StringBuilder append = new StringBuilder().append("FPS: §f");
            final Minecraft mc = HUD.mc;
            final String fpsString = append.append(Minecraft.getDebugFPS()).toString();
            JordoHack.fontManager.drawString(fpsString, (float)(resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(fpsString) - 2), (float)(resolution.getScaledHeight() - daFunnies), JordoHack.INSTANCE.getColorManager().getColorAsInt());
        }
        if (this.arrayList.getValue()) {
            final ArrayList<Module> modules = new ArrayList<Module>();
            final ArrayList<Module> list;
            JordoHack.INSTANCE.getModuleManager().getModules().forEach(m -> {
                if (m.isEnabled() && (boolean)m.drawn.getValue()) {
                    list.add(m);
                }
                return;
            });
            modules.sort(Comparator.comparingDouble(m -> -JordoHack.fontManager.getStringWidth(this.getFullName(m))));
            int offset = 2;
            for (int size = modules.size(), i = 0; i < size; ++i) {
                final Module module = modules.get(i);
                final String name = this.getFullName(module);
                final float x = (float)(resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(name) - 2);
                if (Colours.getInstance().rainbow.getValue()) {
                    this.drawRainbowString(name, x, (float)offset, this.rainbow(x, offset));
                }
                else {
                    JordoHack.fontManager.drawString(name, (float)(resolution.getScaledWidth() - JordoHack.fontManager.getStringWidth(name) - 2), (float)offset, JordoHack.INSTANCE.getColorManager().getColorAsInt());
                }
                offset += 10;
            }
        }
        if (this.armor.getValue()) {
            this.renderArmorHUD(true);
        }
    }
    
    private int rainbow(final double x, final double y) {
        final double scale = 1.0f / (int)Colours.getInstance().factor.getValue();
        final double speed = (int)Colours.getInstance().speed.getValue();
        final double d = -0.2;
        final double pos = (x * d + y * d) * scale + System.currentTimeMillis() % speed * 2.0 * 3.141592653589793 / speed;
        return 0xFF000000 | MathHelper.clamp(MathHelper.floor(255.0 * (0.5 + Math.sin(0.0 + pos))), 0, 255) << 16 | MathHelper.clamp(MathHelper.floor(255.0 * (0.5 + Math.sin(2.0943951023931953 + pos))), 0, 255) << 8 | MathHelper.clamp(MathHelper.floor(255.0 * (0.5 + Math.sin(4.1887902047863905 + pos))), 0, 255);
    }
    
    public void drawRainbowString(final String text, final float x, final float y, final int startColor) {
        Color currentColor = new Color(startColor);
        final float hueIncrement = 1.0f / (int)Colours.getInstance().factor.getValue();
        float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
        final float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
        final float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        for (int i = 0; i < text.length(); ++i) {
            final char currentChar = text.charAt(i);
            final char nextChar = text.charAt(MathUtil.clamp(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                shouldRainbow = false;
            }
            if (shouldContinue) {
                shouldContinue = false;
            }
            else {
                if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                    final String escapeString = text.substring(i);
                    JordoHack.fontManager.drawString(escapeString, x + currentWidth, y, Color.WHITE.getRGB());
                    break;
                }
                JordoHack.fontManager.drawString(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB());
                if (String.valueOf(currentChar).equals("§")) {
                    shouldContinue = true;
                }
                currentWidth += JordoHack.fontManager.getStringWidth(String.valueOf(currentChar));
                if (!String.valueOf(currentChar).equals(" ")) {
                    currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
                    currentHue += hueIncrement;
                }
            }
        }
    }
    
    public void drawCenteredString(final String text, final int x, final int y, final int color) {
        JordoHack.fontManager.drawString(text, (float)(x - JordoHack.fontManager.getStringWidth(text) / 2), (float)y, color);
    }
    
    private String getFullName(final Module m) {
        if (m.getSuffix().length() == 0) {
            return m.getLabel();
        }
        return m.getLabel() + (((boolean)Colours.getInstance().rainbow.getValue()) ? "§r" : ChatFormatting.WHITE) + m.getSuffix();
    }
    
    public void renderArmorHUD(final boolean percent) {
        final ScaledResolution resolution = new ScaledResolution(HUD.mc);
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
            HUD.mc.getRenderItem().zLevel = 200.0f;
            HUD.mc.getRenderItem().renderItemAndEffectIntoGUI(is, x, y);
            HUD.mc.getRenderItem().renderItemOverlayIntoGUI(HUD.mc.fontRenderer, is, x, y, "");
            HUD.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            JordoHack.fontManager.drawString(s, (float)(x + 19 - 2 - JordoHack.fontManager.getStringWidth(s)), (float)(y + 9), 16777215);
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
            JordoHack.fontManager.drawString(dmg + "", (float)(x + 8 - JordoHack.fontManager.getStringWidth(dmg + "") / 2), (float)(y - 10), is.getItem().getRGBDurabilityForDisplay(is));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
    
    public double getDimensionCoord(final double coord) {
        return (HUD.mc.player.dimension == 0) ? (coord / 8.0) : (coord * 8.0);
    }
    
    static {
        HUD.INSTANCE = new HUD();
        totem = new ItemStack(Items.TOTEM_OF_UNDYING);
    }
    
    public enum Mode
    {
        CATEGORY, 
        SYNC, 
        RAINBOW;
    }
}
