package me.hollow.realth.client.modules.player;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.*;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.UpdateEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ModuleManifest(label="AutoFeetPlace", category=Module.Category.COMBAT)

public class AutoFeetPlace extends Module {

    Minecraft mc = Minecraft.getMinecraft();
    public Setting<Integer> delay = this.register(new Setting<Integer>("Delay", 5, 0, 100));

    public Setting<Float> offset = this.register(new Setting<Float>("Y-Change Offset", 2f, 0f, 4f));
    public Setting<Boolean> floor = this.register(new Setting<Boolean>("Floor", true));
    public Setting<Boolean> smartHelper = this.register(new Setting<Boolean>("Smart Helper", true));
    public Setting<Boolean> antiPedo = this.register(new Setting<Boolean>("Anti Pedo", true));
    public Setting<Integer> blocksPerPlace = this.register(new Setting<Integer>("Blocks Per Place", 10, 2, 20));
    public Setting<Integer> retryes = this.register(new Setting<Integer>("Retries", 15, 1, 25));
    public Setting<Boolean> ignore = this.register(new Setting<Boolean>("Ignore Retries", true));
    public Setting<Integer> kmhDisable = this.register(new Setting<Integer>("Speed Disable", 15, 1, 25));
    public Setting<Boolean> center = this.register(new Setting<Boolean>("TPCenter", false));
    private final Timer retryTimer = new Timer();
    private final Map<BlockPos, Integer> retries = new HashMap<>();
    private BlockPos startPos;
    boolean isPlacing = false;
    boolean didPlace = false;
    int placements = 0;
    int extenders = 1;
    int obbySlot = -1;
    int lastHotbarSlot;
    int enableY = -1;

    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            toggle();
            return;
        }
        this.startPos = EntityUtil.getRoundedBlockPos(mc.player);
        if (this.center.getValue()) {
            JordoHack.positionManager.setPositionPacket((double) this.startPos.getX() + 0.5, this.startPos.getY(), (double) this.startPos.getZ() + 0.5, true, true, true);
        }
        enableY = (int)mc.player.posY;
        lastHotbarSlot = mc.player.inventory.currentItem;
    }

    @Override
    public void onDisable() {
        isPlacing = false;
        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketHeldItemChange(lastHotbarSlot));
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        doFeetPlace();
    }

    private void doFeetPlace() {
        if (check()) {
            return;
        }
        if (mc.player.posY > enableY + offset.getValue()) {
            MessageUtil.sendClientMessage("<AutoFeetPlace> Above Surround Position, Toggling!", -22221);
            toggle();
            return;
        }

        if (JordoHack.INSTANCE.getSpeedManager().getPlayerSpeed(mc.player) > kmhDisable.getValue()) {
            toggle();
            return;
        }

        if (!BlockUtil.isSafe(mc.player, 0, floor.getValue())) {
            placeBlocks(mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray(mc.player, 0, floor.getValue()), true, false, false);
        } else if (!BlockUtil.isSafe(mc.player, -1, false)) {
            if (antiPedo.getValue()) {
                placeBlocks(mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray(mc.player, -1, false), false, false, true);
            }
        }
    }


    private boolean placeBlocks(Vec3d pos, Vec3d[] vec3ds, boolean hasHelpingBlocks, boolean isHelping, boolean isExtending) {
        int helpings = 0;
        boolean gotHelp;
        mc.getConnection().sendPacket(new CPacketHeldItemChange(obbySlot));
        for (final Vec3d vec3d : vec3ds) {
            gotHelp = true;
            helpings++;
            if (isHelping && !smartHelper.getValue() && helpings > 1) {
                return false;
            }
            final BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            switch (BlockUtil.isPositionPlaceable(position, true)) {
                case -1:
                    continue;
                case 1:
                    if ((retries.get(position) == null || retries.get(position) < retryes.getValue())) {
                        placeBlock(position);
                        retries.put(position, (retries.get(position) == null ? 1 : (retries.get(position) + 1)));
                        retryTimer.reset();
                        continue;
                    }

                    continue;
                case 2:
                    if (hasHelpingBlocks) {
                        gotHelp = placeBlocks(pos, BlockUtil.getHelpingBlocks(vec3d), false, true, true);
                    } else {
                        continue;
                    }
                case 3:
                    if (gotHelp) {
                        placeBlock(position);
                    }
                    if (isHelping) {
                        return true;
                    }
            }
        }
        mc.getConnection().sendPacket(new CPacketHeldItemChange(lastHotbarSlot));
        return false;
    }

    private void placeBlock(BlockPos pos) {
        if (placements < blocksPerPlace.getValue()) {
            isPlacing = true;
            BlockUtil.placeBlock(pos, false);
            didPlace = true;
            placements++;
        }
    }

    private boolean check() {
        if (fullNullCheck()) {
            return true;
        }
        isPlacing = false;
        didPlace = false;
        extenders = 1;
        placements = 0;
        obbySlot = ItemUtil.getItemSlotInHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));

        if (retryTimer.hasReached(2500)) {
            retries.clear();
            retryTimer.reset();
        }

        if (obbySlot == -1) {
            MessageUtil.sendClientMessage("<AutoFeetPlace> No Obsidian, Toggling!", -22221);
            toggle();
            return true;
        }

        if (mc.player.inventory.currentItem != lastHotbarSlot && mc.player.inventory.currentItem != obbySlot) {
            lastHotbarSlot = mc.player.inventory.currentItem;
        }

        return false;
    }

}
