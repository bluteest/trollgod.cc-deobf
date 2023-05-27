//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import java.util.*;
import me.hollow.realth.client.events.*;
import net.b0at.api.event.*;
import java.awt.*;
import me.hollow.realth.api.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;

@ModuleManifest(label = "HoleEsp", category = Module.Category.RENDER)
public class HoleESP extends Module
{
    public final Setting<Page> page;
    public final Setting<Float> range;
    public final Setting<Boolean> fade;
    public final Setting<Float> fadeRange;
    protected final Setting<Float> minFade;
    private final Setting<Boolean> outline;
    private final Setting<Boolean> box;
    public final Setting<Boolean> down;
    public final Setting<Boolean> wireframe;
    public final Setting<Float> size;
    private final Setting<Integer> obsidianRed;
    private final Setting<Integer> obsidianGreen;
    private final Setting<Integer> obsidianBlue;
    private final Setting<Integer> obsidianAlpha;
    public final Setting<Integer> bedRockRed;
    public final Setting<Integer> bedRockGreen;
    public final Setting<Integer> bedRockBlue;
    public final Setting<Integer> bedRockAlpha;
    private final BlockPos[] surroundOffset;
    public List<BlockPos> holes;
    
    public HoleESP() {
        this.page = (Setting<Page>)this.register(new Setting("Page", (Object)Page.MISC));
        this.range = (Setting<Float>)this.register(new Setting("Range", (Object)5.0f, (Object)1.0f, (Object)16.0f, v -> this.page.getValue() == Page.MISC));
        this.fade = (Setting<Boolean>)this.register(new Setting("Fade", (Object)false, v -> this.page.getValue() == Page.FADE));
        this.fadeRange = (Setting<Float>)this.register(new Setting("Fade Range", (Object)4.0f, (Object)0.0f, (Object)100.0f, v -> this.page.getValue() == Page.FADE));
        this.minFade = (Setting<Float>)this.register(new Setting("Min Fade", (Object)3.0f, (Object)0.0f, (Object)100.0f, v -> this.page.getValue() == Page.FADE));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (Object)false, v -> this.page.getValue() == Page.MISC));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (Object)false, v -> this.page.getValue() == Page.MISC));
        this.down = (Setting<Boolean>)this.register(new Setting("Down", (Object)false, v -> this.page.getValue() == Page.MISC));
        this.wireframe = (Setting<Boolean>)this.register(new Setting("Wireframe", (Object)true, v -> this.page.getValue() == Page.MISC));
        this.size = (Setting<Float>)this.register(new Setting("Size", (Object)1.0f, (Object)0.0f, (Object)1.0f, v -> this.page.getValue() == Page.MISC));
        this.obsidianRed = (Setting<Integer>)this.register(new Setting("O-Red", (Object)255, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.obsidianGreen = (Setting<Integer>)this.register(new Setting("O-Green", (Object)0, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.obsidianBlue = (Setting<Integer>)this.register(new Setting("O-Blue", (Object)0, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.obsidianAlpha = (Setting<Integer>)this.register(new Setting("O-Alpha", (Object)40, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.bedRockRed = (Setting<Integer>)this.register(new Setting("B-Red", (Object)0, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.bedRockGreen = (Setting<Integer>)this.register(new Setting("B-Green", (Object)0, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.bedRockBlue = (Setting<Integer>)this.register(new Setting("B-Blue", (Object)255, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.bedRockAlpha = (Setting<Integer>)this.register(new Setting("B-Alpha", (Object)40, (Object)0, (Object)255, v -> this.page.getValue() == Page.COLOR));
        this.surroundOffset = BlockUtil.toBlockPos(BlockUtil.getOffsets(0, true));
        this.holes = new ArrayList<BlockPos>();
    }
    
    @EventHandler
    public void onTick(final TickEvent event) {
        if (event.getStage() == 0 || HoleESP.mc.player == null || HoleESP.mc.world == null) {
            return;
        }
        this.holes = this.calcHoles();
    }
    
    public void onRender3D() {
        for (int size = this.holes.size(), i = 0; i < size; ++i) {
            final BlockPos pos = this.holes.get(i);
            final Color color = this.isSafe(pos) ? new Color((int)this.bedRockRed.getValue(), (int)this.bedRockGreen.getValue(), (int)this.bedRockBlue.getValue()) : new Color((int)this.obsidianRed.getValue(), (int)this.obsidianGreen.getValue(), (int)this.obsidianBlue.getValue());
            RenderUtil.drawBoxESP(((boolean)this.down.getValue()) ? pos.down() : pos, color, 1.0f, (boolean)this.outline.getValue(), (boolean)this.box.getValue(), this.isSafe(pos) ? ((int)this.bedRockAlpha.getValue()) : ((int)this.obsidianAlpha.getValue()), (float)this.size.getValue());
            if (this.wireframe.getValue()) {
                RenderUtil.renderCrosses(((boolean)this.down.getValue()) ? pos.down() : pos, this.isSafe(pos) ? new Color((int)this.bedRockRed.getValue(), (int)this.bedRockGreen.getValue(), (int)this.bedRockBlue.getValue()) : new Color((int)this.obsidianRed.getValue(), (int)this.obsidianGreen.getValue(), (int)this.obsidianBlue.getValue()), 1.0f);
            }
        }
    }
    
    public List<BlockPos> calcHoles() {
        final ArrayList<BlockPos> safeSpots = new ArrayList<BlockPos>();
        final List<BlockPos> positions = (List<BlockPos>)BlockUtil.getSphere((float)this.range.getValue(), false);
        for (int size = positions.size(), i = 0; i < size; ++i) {
            final BlockPos pos = positions.get(i);
            if (HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                if (HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    boolean isSafe = true;
                    for (final BlockPos offset : this.surroundOffset) {
                        final Block block = HoleESP.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                        if (block != Blocks.BEDROCK) {
                            if (block != Blocks.OBSIDIAN) {
                                isSafe = false;
                            }
                        }
                    }
                    if (isSafe) {
                        safeSpots.add(pos);
                    }
                }
            }
        }
        return safeSpots;
    }
    
    private boolean isSafe(final BlockPos pos) {
        boolean isSafe = true;
        for (final BlockPos offset : this.surroundOffset) {
            final Block block = HoleESP.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
            if (block != Blocks.BEDROCK) {
                isSafe = false;
                break;
            }
        }
        return isSafe;
    }
    
    public enum Page
    {
        COLOR, 
        MISC, 
        FADE;
    }
}
