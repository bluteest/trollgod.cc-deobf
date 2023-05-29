//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package me.hollow.realth.client.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.BlockUtil;
import me.hollow.realth.api.util.CombatUtil;
import me.hollow.realth.api.util.RenderUtil;
import me.hollow.realth.client.events.TickEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

@ModuleManifest(label="HoleEsp", category=Module.Category.RENDER)
public class HoleESP
extends Module {
    public final Setting<Page> page = this.register(new Setting<Page>("Page", Page.MISC));
    public final Setting<Float> range = this.register(new Setting<Float>("Range", Float.valueOf(5.0f), Float.valueOf(1.0f), Float.valueOf(16.0f), v -> this.page.getValue() == Page.MISC));
    public final Setting<Boolean> fade = this.register(new Setting<Boolean>("Fade" , false, v-> this.page.getValue() == Page.FADE));
    public final Setting<Float> fadeRange = this.register(new Setting<Float>("Fade Range", 4.0f, 0.0f, 100.0f, v-> this.page.getValue() == Page.FADE));
    protected final Setting<Float> minFade = this.register(new Setting<Float>("Min Fade", 3.0f, 0.0f, 100.0f, v-> this.page.getValue() == Page.FADE));
    private final Setting<Boolean> outline = register(new Setting<>("Outline", false, v -> page.getValue() == Page.MISC));
    private final Setting<Boolean> box = register(new Setting<>("Box", false, v -> page.getValue() == Page.MISC));
    public final Setting<Boolean> down = this.register(new Setting<Boolean>("Down", Boolean.valueOf(false), v -> this.page.getValue() == Page.MISC));
    public final Setting<Boolean> wireframe = this.register(new Setting<Boolean>("Wireframe", Boolean.valueOf(true), v -> this.page.getValue() == Page.MISC));
    public final Setting<Float> size = this.register(new Setting<Float>("Size", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), v -> this.page.getValue() == Page.MISC));
    private final Setting<Integer> obsidianRed = this.register(new Setting<Integer>("O-Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    private final Setting<Integer> obsidianGreen = this.register(new Setting<Integer>("O-Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    private final Setting<Integer> obsidianBlue = this.register(new Setting<Integer>("O-Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    private final Setting<Integer> obsidianAlpha = this.register(new Setting<Integer>("O-Alpha", Integer.valueOf(40), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    public final Setting<Integer> bedRockRed = this.register(new Setting<Integer>("B-Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    public final Setting<Integer> bedRockGreen = this.register(new Setting<Integer>("B-Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    public final Setting<Integer> bedRockBlue = this.register(new Setting<Integer>("B-Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    public final Setting<Integer> bedRockAlpha = this.register(new Setting<Integer>("B-Alpha", Integer.valueOf(40), Integer.valueOf(0), Integer.valueOf(255), v -> this.page.getValue() == Page.COLOR));
    private final BlockPos[] surroundOffset = BlockUtil.toBlockPos(BlockUtil.getOffsets(0, true));
    public List<BlockPos> holes = new ArrayList<BlockPos>();

    @EventHandler
    public void onTick(TickEvent event) {
        if (event.getStage() == 0 || this.mc.player == null || this.mc.world == null) {
            return;
        }
        this.holes = this.calcHoles();
    }

    @Override
    public void onRender3D() {
        int size = this.holes.size();
        for (int i = 0; i < size; ++i) {
            BlockPos pos = this.holes.get(i);
            final Color color = isSafe(pos) ? new Color(bedRockRed.getValue(), bedRockGreen.getValue(), bedRockBlue.getValue()) : new Color(obsidianRed.getValue(), obsidianGreen.getValue(), obsidianBlue.getValue());
            RenderUtil.drawBoxESP(this.down.getValue() != false ? pos.down() : pos, color, 1, outline.getValue(), box.getValue(), this.isSafe(pos) ? bedRockAlpha.getValue() : obsidianAlpha.getValue(), this.size.getValue().floatValue());
            if (!this.wireframe.getValue().booleanValue()) continue;
            RenderUtil.renderCrosses(this.down.getValue() != false ? pos.down() : pos, this.isSafe(pos) ? new Color(this.bedRockRed.getValue(), this.bedRockGreen.getValue(), this.bedRockBlue.getValue()) : new Color(this.obsidianRed.getValue(), this.obsidianGreen.getValue(), this.obsidianBlue.getValue()), 1.0f);
        }
    }

    public List<BlockPos> calcHoles() {
        ArrayList<BlockPos> safeSpots = new ArrayList<BlockPos>();
        List<BlockPos> positions = BlockUtil.getSphere(this.range.getValue().floatValue(), false);
        int size = positions.size();
        for (int i = 0; i < size; ++i) {
            BlockPos pos = positions.get(i);
            if (!this.mc.world.getBlockState(pos).getBlock().equals((Object)Blocks.AIR) || !this.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals((Object)Blocks.AIR) || !this.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals((Object)Blocks.AIR)) continue;
            boolean isSafe = true;
            for (BlockPos offset : this.surroundOffset) {
                Block block = this.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN) continue;
                isSafe = false;
            }
            if (!isSafe) continue;
            safeSpots.add(pos);
        }
        return safeSpots;
    }

    private boolean isSafe(BlockPos pos) {
        boolean isSafe = true;
        for (BlockPos offset : this.surroundOffset) {
            Block block = mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
            if (block == Blocks.BEDROCK) continue;
            isSafe = false;
            break;
        }
        return isSafe;
    }

    public enum Page {
        COLOR,
        MISC,
        FADE

    }
}

