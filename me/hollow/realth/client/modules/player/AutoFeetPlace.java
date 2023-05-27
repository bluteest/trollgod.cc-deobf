//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import net.minecraft.client.*;
import me.hollow.realth.api.property.*;
import net.minecraft.entity.*;
import me.hollow.realth.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.hollow.realth.client.events.*;
import net.b0at.api.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.hollow.realth.api.util.*;

@ModuleManifest(label = "AutoFeetPlace", category = Module.Category.COMBAT)
public class AutoFeetPlace extends Module
{
    Minecraft mc;
    public Setting<Integer> delay;
    public Setting<Float> offset;
    public Setting<Boolean> floor;
    public Setting<Boolean> smartHelper;
    public Setting<Boolean> antiPedo;
    public Setting<Integer> blocksPerPlace;
    public Setting<Integer> retryes;
    public Setting<Boolean> ignore;
    public Setting<Integer> kmhDisable;
    public Setting<Boolean> center;
    private final Timer retryTimer;
    private final Map<BlockPos, Integer> retries;
    private BlockPos startPos;
    boolean isPlacing;
    boolean didPlace;
    int placements;
    int extenders;
    int obbySlot;
    int lastHotbarSlot;
    int enableY;
    
    public AutoFeetPlace() {
        this.mc = Minecraft.getMinecraft();
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (Object)5, (Object)0, (Object)100));
        this.offset = (Setting<Float>)this.register(new Setting("Y-Change Offset", (Object)2.0f, (Object)0.0f, (Object)4.0f));
        this.floor = (Setting<Boolean>)this.register(new Setting("Floor", (Object)true));
        this.smartHelper = (Setting<Boolean>)this.register(new Setting("Smart Helper", (Object)true));
        this.antiPedo = (Setting<Boolean>)this.register(new Setting("Anti Pedo", (Object)true));
        this.blocksPerPlace = (Setting<Integer>)this.register(new Setting("Blocks Per Place", (Object)10, (Object)2, (Object)20));
        this.retryes = (Setting<Integer>)this.register(new Setting("Retries", (Object)15, (Object)1, (Object)25));
        this.ignore = (Setting<Boolean>)this.register(new Setting("Ignore Retries", (Object)true));
        this.kmhDisable = (Setting<Integer>)this.register(new Setting("Speed Disable", (Object)15, (Object)1, (Object)25));
        this.center = (Setting<Boolean>)this.register(new Setting("TPCenter", (Object)false));
        this.retryTimer = new Timer();
        this.retries = new HashMap<BlockPos, Integer>();
        this.isPlacing = false;
        this.didPlace = false;
        this.placements = 0;
        this.extenders = 1;
        this.obbySlot = -1;
        this.enableY = -1;
    }
    
    public void onEnable() {
        if (fullNullCheck()) {
            this.toggle();
            return;
        }
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)this.mc.player);
        if (this.center.getValue()) {
            JordoHack.positionManager.setPositionPacket(this.startPos.getX() + 0.5, (double)this.startPos.getY(), this.startPos.getZ() + 0.5, true, true, true);
        }
        this.enableY = (int)this.mc.player.posY;
        this.lastHotbarSlot = this.mc.player.inventory.currentItem;
    }
    
    public void onDisable() {
        this.isPlacing = false;
        Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketHeldItemChange(this.lastHotbarSlot));
    }
    
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        this.doFeetPlace();
    }
    
    private void doFeetPlace() {
        if (this.check()) {
            return;
        }
        if (this.mc.player.posY > this.enableY + (float)this.offset.getValue()) {
            MessageUtil.sendClientMessage("<AutoFeetPlace> Above Surround Position, Toggling!", -22221);
            this.toggle();
            return;
        }
        if (JordoHack.INSTANCE.getSpeedManager().getPlayerSpeed((EntityPlayer)this.mc.player) > (int)this.kmhDisable.getValue()) {
            this.toggle();
            return;
        }
        if (!BlockUtil.isSafe((Entity)this.mc.player, 0, (boolean)this.floor.getValue())) {
            this.placeBlocks(this.mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray((Entity)this.mc.player, 0, (boolean)this.floor.getValue()), true, false, false);
        }
        else if (!BlockUtil.isSafe((Entity)this.mc.player, -1, false) && (boolean)this.antiPedo.getValue()) {
            this.placeBlocks(this.mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray((Entity)this.mc.player, -1, false), false, false, true);
        }
    }
    
    private boolean placeBlocks(final Vec3d pos, final Vec3d[] vec3ds, final boolean hasHelpingBlocks, final boolean isHelping, final boolean isExtending) {
        int helpings = 0;
        this.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(this.obbySlot));
        for (final Vec3d vec3d : vec3ds) {
            boolean gotHelp = true;
            ++helpings;
            if (isHelping && !(boolean)this.smartHelper.getValue() && helpings > 1) {
                return false;
            }
            final BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            Label_0289: {
                switch (BlockUtil.isPositionPlaceable(position, true)) {
                    case 1: {
                        if (this.retries.get(position) == null || this.retries.get(position) < (int)this.retryes.getValue()) {
                            this.placeBlock(position);
                            this.retries.put(position, (this.retries.get(position) == null) ? 1 : (this.retries.get(position) + 1));
                            this.retryTimer.reset();
                            break;
                        }
                        break;
                    }
                    case 2: {
                        if (hasHelpingBlocks) {
                            gotHelp = this.placeBlocks(pos, BlockUtil.getHelpingBlocks(vec3d), false, true, true);
                            break Label_0289;
                        }
                        break;
                    }
                    case 3: {
                        if (gotHelp) {
                            this.placeBlock(position);
                        }
                        if (isHelping) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        this.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(this.lastHotbarSlot));
        return false;
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.placements < (int)this.blocksPerPlace.getValue()) {
            this.isPlacing = true;
            BlockUtil.placeBlock(pos, false);
            this.didPlace = true;
            ++this.placements;
        }
    }
    
    private boolean check() {
        if (fullNullCheck()) {
            return true;
        }
        this.isPlacing = false;
        this.didPlace = false;
        this.extenders = 1;
        this.placements = 0;
        this.obbySlot = ItemUtil.getItemSlotInHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (this.retryTimer.hasReached(2500L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        if (this.obbySlot == -1) {
            MessageUtil.sendClientMessage("<AutoFeetPlace> No Obsidian, Toggling!", -22221);
            this.toggle();
            return true;
        }
        if (this.mc.player.inventory.currentItem != this.lastHotbarSlot && this.mc.player.inventory.currentItem != this.obbySlot) {
            this.lastHotbarSlot = this.mc.player.inventory.currentItem;
        }
        return false;
    }
}
