//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.util.math.*;
import me.hollow.realth.*;
import java.awt.*;
import me.hollow.realth.client.events.*;
import net.minecraft.init.*;
import me.hollow.realth.api.util.*;
import net.minecraft.network.*;
import me.hollow.realth.api.mixin.accessors.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.b0at.api.event.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

@ModuleManifest(label = "FastBreak", category = Module.Category.PLAYER)
public class FastBreak extends Module
{
    private final Setting<Boolean> reset;
    private final Setting<Boolean> silent;
    private static FastBreak INSTANCE;
    private BlockPos currentPos;
    private final Timer renderTimer;
    
    public FastBreak() {
        this.reset = (Setting<Boolean>)this.register(new Setting("Reset", (Object)true));
        this.silent = (Setting<Boolean>)this.register(new Setting("Silent", (Object)true));
        this.currentPos = null;
        this.renderTimer = new Timer();
        FastBreak.INSTANCE = this;
    }
    
    public static FastBreak getInstance() {
        if (FastBreak.INSTANCE == null) {
            FastBreak.INSTANCE = new FastBreak();
        }
        return FastBreak.INSTANCE;
    }
    
    public void onRender3D() {
        if (this.currentPos != null && FastBreak.mc.world.getBlockState(this.currentPos).getBlock() == Blocks.AIR) {
            this.currentPos = null;
        }
        if (this.currentPos != null) {
            final Color color = new Color(this.renderTimer.hasReached((long)(int)(1500.0f * JordoHack.INSTANCE.getTpsManager().getTpsFactor())) ? 16711680 : 65280);
            RenderUtil.drawProperBoxESP(this.currentPos, color, 1.0f, true, true, 40, 1.0f);
        }
    }
    
    @EventHandler
    public void onClickBlock(final ClickBlockEvent event) {
        final int realSlot = FastBreak.mc.player.inventory.currentItem;
        if (this.silent.getValue()) {
            FastBreak.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(ItemUtil.getItemSlotInHotbar(Items.DIAMOND_PICKAXE)));
        }
        if (this.reset.getValue()) {
            ((IPlayerControllerMP)FastBreak.mc.playerController).setIsHittingBlock(false);
        }
        if (this.canBreak(event.getPos())) {
            FastBreak.mc.player.swingArm(EnumHand.MAIN_HAND);
            FastBreak.mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFacing()));
            FastBreak.mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFacing()));
            if (this.silent.getValue()) {
                FastBreak.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(realSlot));
            }
            this.currentPos = event.getPos();
        }
        event.cancel();
    }
    
    public boolean canBreak(final BlockPos pos) {
        final Block block = FastBreak.mc.world.getBlockState(pos).getBlock();
        return block.getBlockHardness(FastBreak.mc.world.getBlockState(pos), (World)FastBreak.mc.world, pos) != -1.0f;
    }
    
    static {
        FastBreak.INSTANCE = new FastBreak();
    }
}
