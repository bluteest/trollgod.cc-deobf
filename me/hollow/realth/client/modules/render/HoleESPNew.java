package me.hollow.realth.client.modules.render;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.*;
import me.hollow.realth.client.managers.ColorManager;
import me.hollow.realth.client.managers.HoleManager;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import java.util.List;
import me.hollow.realth.api.util.RenderUtil2;
import java.util.stream.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.culling.Frustum;
import java.awt.*;
import java.util.Map.Entry;
import java.util.*;

@ModuleManifest(label = "HoleESP", category = Module.Category.RENDER)
public class HoleESPNew extends Module
{
    public Setting<Float> radius = this.register(new Setting<>("Radius", 50f, 1f, 50f));

    public Setting<Float> height = this.register(new Setting<Float>("Height", 1f, 0f, 2f));
    public Setting<Float> lineWidth = this.register(new Setting<Float>("Line Width", 1f, 0.1f, 5f));
    public Setting<Boolean> doubles = this.register(new Setting<Boolean>("Doubles", false));
    public Setting<Animation> animation = this.register(new Setting<>("Animation", Animation.NONE));
    public Setting<Float> growSpeed = this.register(new Setting("Grow Speed", 10f, 0f, 1000f, v-> animation.currentEnumName() == "Grow"));
    public Setting<Float> distanceDivision = this.register(new Setting("Distance Divisor", 20f, 0.1f, 50f, v-> animation.currentEnumName() == "Fade"));
    public int obsidianOutlineAlpha2 = 255;
    public int bedrockOutlineAlpha2 = 255;


    private final Setting<Integer> obsidianRed = this.register(new Setting<Integer>("O-Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    private final Setting<Integer> obsidianGreen = this.register(new Setting<Integer>("O-Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
    private final Setting<Integer> obsidianBlue = this.register(new Setting<Integer>("O-Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));



    private final Setting<Integer> obsidianAlpha = this.register(new Setting<Integer>("O-Alpha", Integer.valueOf(40), Integer.valueOf(0), Integer.valueOf(255)));
    public final Setting<Integer> bedRockRed = this.register(new Setting<Integer>("B-Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
    public final Setting<Integer> bedRockGreen = this.register(new Setting<Integer>("B-Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
    public final Setting<Integer> bedRockBlue = this.register(new Setting<Integer>("B-Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    public final Setting<Integer> bedRockAlpha = this.register(new Setting<Integer>("B-Alpha", 40, 0, 255));
    public HashMap<BlockPos, Long> holePosLongHashMap = new HashMap<BlockPos, Long>();
    public Color obsidianOutline = new Color (255, 0, 0 );
    public Color bedrockOutline = new Color (0, 255, 0);

    public Color bedRock = new Color(bedRockRed.getValue(), bedRockGreen.getValue(), bedRockBlue.getValue(), bedRockAlpha.getValue());
    public Color obsidian = new Color(obsidianRed.getValue(), obsidianGreen.getValue(), obsidianBlue.getValue(), obsidianAlpha.getValue());
    public ICamera camera = (ICamera)new Frustum();
    public enum Animation
    {
        NONE,
        GROW,
        FADE
    }

    @Override
    public void onFrame(final float partialTicks) {
        this.camera.setPosition(Objects.requireNonNull(this.mc.getRenderViewEntity()).posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
        final List<HoleManager.HolePos> holes = JordoHack.INSTANCE.holeManager.holes.stream().filter(holePos -> this.mc.player.getDistanceSq(holePos.pos) < this.radius.getValue() * this.radius.getValue() && (this.doubles.getValue() || holePos.holeType.equals(HoleManager.Type.Bedrock) || holePos.holeType.equals(HoleManager.Type.Obsidian))).collect(Collectors.toList());
        final Long[] n = new Long[1];
        new HashMap<>(this.holePosLongHashMap).entrySet().stream().filter(entry -> holes.stream().noneMatch(holePos -> holePos.pos.equals(entry.getKey()))).forEach(entry -> n[0] = this.holePosLongHashMap.remove(entry.getKey()));
        for (final HoleManager.HolePos holePos2 : holes) {
            AxisAlignedBB bb = this.animation.currentEnumName().equals("Grow") ? new AxisAlignedBB(holePos2.pos).shrink(0.5) : new AxisAlignedBB(holePos2.pos);
            if (this.animation.currentEnumName().equals("Grow")) {
                for (final Map.Entry<BlockPos, Long> entry2 : this.holePosLongHashMap.entrySet()) {
                    if (entry2.getKey().equals(holePos2.pos)) {
                        bb = bb.grow(Math.min((System.currentTimeMillis() - entry2.getValue()) / (1001.0f - this.growSpeed.getValue()), 0.5));
                    }
                }
            }
            final int bedrockAlpha = (int) (bedRockAlpha.getValue() / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.getValue()));
            final int obsidianAlpha2 = (int) ((obsidianAlpha.getValue()) / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.getValue()));
            final int bedrockOutlineAlpha = (int)Math.min(this.bedrockOutlineAlpha2 / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.getValue()), this.bedrockOutlineAlpha2);
            final int obsidianOutlineAlpha = (int)Math.min(this.obsidianOutlineAlpha2 / Math.max(1.0, this.mc.player.getDistanceSq(holePos2.pos) / this.distanceDivision.getValue()), this.obsidianOutlineAlpha2);
            final Color bedrockBoxColor = this.animation.currentEnumName().equals("Fade") ? new Color(this.bedRockRed.getValue() / 255.0f, this.bedRockGreen.getValue() / 255.0f, this.bedRockBlue.getValue() / 255.0f, bedrockAlpha / 255.0f) : bedRock;
            final Color obsidianBoxColor = this.animation.currentEnumName().equals("Fade") ? new Color(this.obsidianRed.getValue() / 255.0f, this.obsidianGreen.getValue() / 255.0f, this.obsidianBlue.getValue() / 255.0f, obsidianAlpha2 / 255.0f) : obsidian;
            final Color bedrockOutlineColor = this.animation.currentEnumName().equals("Fade") ? new Color(this.bedrockOutline.getRed() / 255.0f, this.bedrockOutline.getGreen() / 255.0f, this.bedrockOutline.getBlue() / 255.0f, bedrockOutlineAlpha / 255.0f) : this.bedrockOutline;
            final Color obsidianOutlineColor = this.animation.currentEnumName().equals("Fade") ? new Color(this.obsidianOutline.getRed() / 255.0f, this.obsidianOutline.getGreen() / 255.0f, this.obsidianOutline.getBlue() / 255.0f, obsidianOutlineAlpha / 255.0f) : this.obsidianOutline;
            if (this.camera.isBoundingBoxInFrustum(bb.grow(2.0))) {
                switch (holePos2.holeType) {
                    case Bedrock: {
                        RenderUtil2.drawBoxWithHeight(bb, bedrockBoxColor, this.height.getValue());
                        RenderUtil2.drawBlockOutlineBBWithHeight(bb, bedrockOutlineColor, this.lineWidth.getValue(), this.height.getValue());
                        break;
                    }
                    case Obsidian: {
                        RenderUtil2.drawBoxWithHeight(bb, obsidianBoxColor, this.height.getValue());
                        RenderUtil2.drawBlockOutlineBBWithHeight(bb, obsidianOutlineColor, this.lineWidth.getValue(), this.height.getValue());
                        break;
                    }
                    case DoubleBedrockNorth: {
                        RenderUtil2.drawCustomBB(bedrockBoxColor, bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY - 1.0 + this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY, bb.maxZ), bedrockOutlineColor, this.lineWidth.getValue(), this.height.getValue());
                        break;
                    }
                    case DoubleObsidianNorth: {
                        RenderUtil2.drawCustomBB(obsidianBoxColor, bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY - 1.0 + this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX, bb.minY, bb.minZ - 1.0, bb.maxX, bb.maxY, bb.maxZ), obsidianOutlineColor, this.lineWidth.getValue(), this.height.getValue());
                        break;
                    }
                    case DoubleBedrockWest: {
                        RenderUtil2.drawCustomBB(bedrockBoxColor, bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1.0 + this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ), bedrockOutlineColor, this.lineWidth.getValue(), this.height.getValue());
                        break;
                    }
                    case DoubleObsidianWest: {
                        RenderUtil2.drawCustomBB(obsidianBoxColor, bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1.0 + this.height.getValue(), bb.maxZ);
                        RenderUtil2.drawBlockOutlineBBWithHeight(new AxisAlignedBB(bb.minX - 1.0, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ), obsidianOutlineColor, this.lineWidth.getValue(), this.height.getValue());
                        break;
                    }
                }
            }
            if (!this.holePosLongHashMap.containsKey(holePos2.pos)) {
                this.holePosLongHashMap.put(holePos2.pos, System.currentTimeMillis());
            }
        }
    }
}
