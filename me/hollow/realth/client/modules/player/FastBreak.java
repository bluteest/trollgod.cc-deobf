package me.hollow.realth.client.modules.player;

import java.awt.Color;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.mixin.accessors.IPlayerControllerMP;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.RenderUtil;
import me.hollow.realth.api.util.Timer;
import me.hollow.realth.api.util.ItemUtil;
import me.hollow.realth.client.events.ClickBlockEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@ModuleManifest(label="FastBreak", category=Module.Category.PLAYER)
public class FastBreak
        extends Module {
    private final Setting<Boolean> reset = this.register(new Setting<Boolean>("Reset", true));
    private final Setting<Boolean> silent = this.register(new Setting<Boolean>("Silent", true));
    private static FastBreak INSTANCE = new FastBreak();
    private BlockPos currentPos = null;

    private final Timer renderTimer = new Timer();

    public FastBreak() {
        INSTANCE = this;
    }

    public static FastBreak getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FastBreak();
        }
        return INSTANCE;
    }

    @Override
    public void onRender3D() {
        if (currentPos != null && mc.world.getBlockState(currentPos).getBlock() == Blocks.AIR) {
            currentPos = null;
        }

        if (this.currentPos != null) {
            Color color = new Color(this.renderTimer.hasReached((int) (1500.0f * JordoHack.INSTANCE.getTpsManager().getTpsFactor())) ? 0xFF0000 : 0x00FF00);
            RenderUtil.drawProperBoxESP(currentPos, color, 1, true, true, 40, 1);
        }
    }

    @EventHandler
    public void onClickBlock(ClickBlockEvent event) {
        int realSlot = mc.player.inventory.currentItem;
        if (silent.getValue().booleanValue()) {
            mc.getConnection().sendPacket(new CPacketHeldItemChange(ItemUtil.getItemSlotInHotbar(Items.DIAMOND_PICKAXE)));
        }
        if (this.reset.getValue().booleanValue()) {
            ((IPlayerControllerMP) this.mc.playerController).setIsHittingBlock(false);
        }
        if (this.canBreak(event.getPos())) {
            this.mc.player.swingArm(EnumHand.MAIN_HAND);
            this.mc.getConnection().sendPacket((Packet) new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFacing()));
            this.mc.getConnection().sendPacket((Packet) new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFacing()));
            if (silent.getValue().booleanValue()) {
                mc.getConnection().sendPacket(new CPacketHeldItemChange(realSlot));
            }
            this.currentPos = event.getPos();
        }
        event.cancel();
    }

    public boolean canBreak (BlockPos pos){
        Block block = this.mc.world.getBlockState(pos).getBlock();
        return block.getBlockHardness(this.mc.world.getBlockState(pos), (World) this.mc.world, pos) != -1.0f;
    }
}