//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import java.awt.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.*;
import me.hollow.realth.*;
import me.hollow.realth.client.managers.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import me.hollow.realth.api.util.*;
import java.util.*;

@ModuleManifest(label = "HoleESP", category = Module.Category.RENDER)
public class HoleESPNew extends Module
{
    public Setting<Float> radius;
    public Setting<Float> height;
    public Setting<Float> lineWidth;
    public Setting<Boolean> doubles;
    public Setting<Animation> animation;
    public Setting<Float> growSpeed;
    public Setting<Float> distanceDivision;
    public int obsidianOutlineAlpha2;
    public int bedrockOutlineAlpha2;
    private final Setting<Integer> obsidianRed;
    private final Setting<Integer> obsidianGreen;
    private final Setting<Integer> obsidianBlue;
    private final Setting<Integer> obsidianAlpha;
    public final Setting<Integer> bedRockRed;
    public final Setting<Integer> bedRockGreen;
    public final Setting<Integer> bedRockBlue;
    public final Setting<Integer> bedRockAlpha;
    public HashMap<BlockPos, Long> holePosLongHashMap;
    public Color obsidianOutline;
    public Color bedrockOutline;
    public Color bedRock;
    public Color obsidian;
    public ICamera camera;
    
    public HoleESPNew() {
        this.radius = (Setting<Float>)this.register(new Setting("Radius", (Object)50.0f, (Object)1.0f, (Object)50.0f));
        this.height = (Setting<Float>)this.register(new Setting("Height", (Object)1.0f, (Object)0.0f, (Object)2.0f));
        this.lineWidth = (Setting<Float>)this.register(new Setting("Line Width", (Object)1.0f, (Object)0.1f, (Object)5.0f));
        this.doubles = (Setting<Boolean>)this.register(new Setting("Doubles", (Object)false));
        this.animation = (Setting<Animation>)this.register(new Setting("Animation", (Object)Animation.NONE));
        this.growSpeed = (Setting<Float>)this.register(new Setting("Grow Speed", (Object)10.0f, (Object)0.0f, (Object)1000.0f, v -> this.animation.currentEnumName() == "Grow"));
        this.distanceDivision = (Setting<Float>)this.register(new Setting("Distance Divisor", (Object)20.0f, (Object)0.1f, (Object)50.0f, v -> this.animation.currentEnumName() == "Fade"));
        this.obsidianOutlineAlpha2 = 255;
        this.bedrockOutlineAlpha2 = 255;
        this.obsidianRed = (Setting<Integer>)this.register(new Setting("O-Red", (Object)255, (Object)0, (Object)255));
        this.obsidianGreen = (Setting<Integer>)this.register(new Setting("O-Green", (Object)0, (Object)0, (Object)255));
        this.obsidianBlue = (Setting<Integer>)this.register(new Setting("O-Blue", (Object)0, (Object)0, (Object)255));
        this.obsidianAlpha = (Setting<Integer>)this.register(new Setting("O-Alpha", (Object)40, (Object)0, (Object)255));
        this.bedRockRed = (Setting<Integer>)this.register(new Setting("B-Red", (Object)0, (Object)0, (Object)255));
        this.bedRockGreen = (Setting<Integer>)this.register(new Setting("B-Green", (Object)0, (Object)0, (Object)255));
        this.bedRockBlue = (Setting<Integer>)this.register(new Setting("B-Blue", (Object)255, (Object)0, (Object)255));
        this.bedRockAlpha = (Setting<Integer>)this.register(new Setting("B-Alpha", (Object)40, (Object)0, (Object)255));
        this.holePosLongHashMap = new HashMap<BlockPos, Long>();
        this.obsidianOutline = new Color(255, 0, 0);
        this.bedrockOutline = new Color(0, 255, 0);
        this.bedRock = new Color((int)this.bedRockRed.getValue(), (int)this.bedRockGreen.getValue(), (int)this.bedRockBlue.getValue(), (int)this.bedRockAlpha.getValue());
        this.obsidian = new Color((int)this.obsidianRed.getValue(), (int)this.obsidianGreen.getValue(), (int)this.obsidianBlue.getValue(), (int)this.obsidianAlpha.getValue());
        this.camera = (ICamera)new Frustum();
    }
    
    public void onFrame(final float partialTicks) {
        this.camera.setPosition(Objects.requireNonNull(HoleESPNew.mc.getRenderViewEntity()).posX, HoleESPNew.mc.getRenderViewEntity().posY, HoleESPNew.mc.getRenderViewEntity().posZ);
        final List<HoleManager.HolePos> holes = (List<HoleManager.HolePos>)JordoHack.INSTANCE.holeManager.holes.stream().filter(holePos -> HoleESPNew.mc.player.getDistanceSq(holePos.pos) < (float)this.radius.getValue() * (float)this.radius.getValue() && ((boolean)this.doubles.getValue() || holePos.holeType.equals((Object)HoleManager.Type.Bedrock) || holePos.holeType.equals((Object)HoleManager.Type.Obsidian))).collect(Collectors.toList());
        final Long[] n = { null };
        new HashMap(this.holePosLongHashMap).entrySet().stream().filter(entry -> holes.stream().noneMatch(holePos -> holePos.pos.equals(entry.getKey()))).forEach(entry -> n[0] = this.holePosLongHashMap.remove(entry.getKey()));
        for (final HoleManager.HolePos holePos2 : holes) {
            AxisAlignedBB bb = this.animation.currentEnumName().equals("Grow") ? new AxisAlignedBB(holePos2.pos).shrink(0.5) : new AxisAlignedBB(holePos2.pos);
            if (this.animation.currentEnumName().equals("Grow")) {
                for (final Map.Entry<BlockPos, Long> entry2 : this.holePosLongHashMap.entrySet()) {
                    if (entry2.getKey().equals((Object)holePos2.pos)) {
                        bb = bb.grow(Math.min((System.currentTimeMillis() - entry2.getValue()) / (1001.0f - (float)this.growSpeed.getValue()), 0.5));
                    }
                }
            }
            final int bedrockAlpha = (int)((int)this.bedRockAlpha.getValue() / Math.max(1.0, HoleESPNew.mc.player.getDistanceSq(holePos2.pos) / (float)this.distanceDivision.getValue()));
            final int obsidianAlpha2 = (int)((int)this.obsidianAlpha.getValue() / Math.max(1.0, HoleESPNew.mc.player.getDistanceSq(holePos2.pos) / (float)this.distanceDivision.getValue()));
            final int bedrockOutlineAlpha = (int)Math.min(this.bedrockOutlineAlpha2 / Math.max(1.0, HoleESPNew.mc.player.getDistanceSq(holePos2.pos) / (float)this.distanceDivision.getValue()), this.bedrockOutlineAlpha2);
            final int obsidianOutlineAlpha = (int)Math.min(this.obsidianOutlineAlpha2 / Math.max(1.0, HoleESPNew.mc.player.getDistanceSq(holePos2.pos) / (float)this.distanceDivision.getValue()), this.obsidianOutlineAlpha2);
            final Color bedrockBoxColor = this.animation.currentEnumName().equals("Fade") ? new Color((int)this.bedRockRed.getValue() / 255.0f, (int)this.bedRockGreen.getValue() / 255.0f, (int)this.bedRockBlue.getValue() / 255.0f, bedrockAlpha / 255.0f) : this.bedRock;
            final Color obsidianBoxColor = this.animation.currentEnumName().equals("Fade") ? new Color((int)this.obsidianRed.getValue() / 255.0f, (int)this.obsidianGreen.getValue() / 255.0f, (int)this.obsidianBlue.getValue() / 255.0f, obsidianAlpha2 / 255.0f) : this.obsidian;
            final Color bedrockOutlineColor = this.animation.currentEnumName().equals("Fade") ? new Color(this.bedrockOutline.getRed() / 255.0f, this.bedrockOutline.getGreen() / 255.0f, this.bedrockOutline.getBlue() / 255.0f, bedrockOutlineAlpha / 255.0f) : this.bedrockOutline;
            final Color obsidianOutlineColor = this.animation.currentEnumName().equals("Fade") ? new Color(this.obsidianOutline.getRed() / 255.0f, this.obsidianOutline.getGreen() / 255.0f, this.obsidianOutline.getBlue() / 255.0f, obsidianOutlineAlpha / 255.0f) : this.obsidianOutline;
            if (this.camera.isBoundingBoxInFrustum(bb.grow(2.0))) {
                switch (holePos2.holeType) {
                    case Bedrock: {
                        RenderUtil2.drawBoxWithHeight(bb, bedrockBoxColor, (float)this.height.getValue());
                        RenderUtil2.drawBlockOutlineBBWithHeight(bb, bedrockOutlineColor, (float)this.lineWidth.getValue(), (float)this.height.getValue());
                        break;
                    }
                    case Obsidian: {
                        RenderUtil2.drawBoxWithHeight(bb, obsidianBoxColor, (float)this.height.getValue());
                        RenderUtil2.drawBlockOutlineBBWithHeight(bb, obsidianOutlineColor, (float)this.lineWidth.getValue(), (float)this.height.getValue());
                        break;
                    }
                    case DoubleBedrockNorth: {
                        RenderUtil2.drawCustomBB(bedrockBoxColor, bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY - 1.0 + (float)this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY, bb.maxZ), bedrockOutlineColor, (float)this.lineWidth.getValue(), (float)this.height.getValue());
                        break;
                    }
                    case DoubleObsidianNorth: {
                        RenderUtil2.drawCustomBB(obsidianBoxColor, bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY - 1.0 + (float)this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY, bb.maxZ), obsidianOutlineColor, (float)this.lineWidth.getValue(), (float)this.height.getValue());
                        break;
                    }
                    case DoubleBedrockWest: {
                        RenderUtil2.drawCustomBB(bedrockBoxColor, bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1.0 + (float)this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ), bedrockOutlineColor, (float)this.lineWidth.getValue(), (float)this.height.getValue());
                        break;
                    }
                    case DoubleObsidianWest: {
                        RenderUtil2.drawCustomBB(obsidianBoxColor, bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1.0 + (float)this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ), obsidianOutlineColor, (float)this.lineWidth.getValue(), (float)this.height.getValue());
                        break;
                    }
                }
            }
            if (!this.holePosLongHashMap.containsKey(holePos2.pos)) {
                this.holePosLongHashMap.put(holePos2.pos, System.currentTimeMillis());
            }
        }
    }
    
    public enum Animation
    {
        NONE, 
        GROW, 
        FADE;
    }
}
