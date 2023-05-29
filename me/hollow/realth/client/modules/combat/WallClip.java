package me.hollow.realth.client.modules.combat;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.MoveEvent;
import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ModuleManifest(label="PacketClip", category= Module.Category.COMBAT)
public class WallClip extends Module {
    public Setting<Integer> clipAmount = this.register(new Setting<Integer>("Amount", 6, 1, 6));
    public Setting<Integer> clipStep = this.register(new Setting<Integer>("Step", 3, 1, 6));
    int disable = 0;

    @EventHandler
    public void onMove(MoveEvent event) {
        if (this.isEnabled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (WallClip.fullNullCheck()) {
            return;
        }
        Vec2i dir = this.getWallDir();
        if (dir == null) {
            this.setEnabled(false);
            return;
        }
        double amt = (double)this.clipAmount.getValue().intValue() * 0.01 - 0.001;
        BlockPos pos = this.getFlooredPos();
        Vec3d center = new Vec3d((double)pos.getX() + 0.5 + 0.2 * (double)dir.x, pos.getY(), (double)pos.getZ() + 0.5 + 0.2 * (double)dir.y);
        Vec3d target = new Vec3d(center.x + amt * (double)dir.x, center.y, center.z + amt * (double)dir.y);
        double x = Math.abs(target.x - WallClip.mc.player.posX);
        double z = Math.abs(target.z - WallClip.mc.player.posZ);
        if (x <= amt + 0.01 && z <= amt + 0.01) {
            WallClip.mc.player.setVelocity(0.0, 0.0, 0.0);
            if (x <= amt + 0.001 && z <= amt + 0.001) {
                if (x <= 0.001 && z <= 0.001) {
                    if (this.disable > 5) {
                        this.setEnabled(false);
                    } else {
                        ++this.disable;
                    }
                    return;
                }
                this.disable = 0;
                double mx = Math.abs(Math.max(0.0, amt - x) + (double)this.clipStep.getValue().intValue() * 0.01);
                double mz = Math.abs(Math.max(0.0, amt - z) + (double)this.clipStep.getValue().intValue() * 0.01);
                target = new Vec3d(center.x + (double)dir.x * Math.min(mx, amt), center.y, center.z + (double)dir.y * Math.min(mz, amt));
                WallClip.mc.player.setPosition(target.x, target.y, target.z);
                mc.getConnection().sendPacket(new CPacketPlayer.Position(WallClip.mc.player.posX, WallClip.mc.player.posY, WallClip.mc.player.posZ, WallClip.mc.player.onGround));
                mc.getConnection().sendPacket(new CPacketPlayer.Position(WallClip.mc.player.posX + 0.1 * (double)dir.y, WallClip.mc.player.posY, WallClip.mc.player.posZ + 0.1 * (double)dir.y, WallClip.mc.player.onGround));
            } else {
                double mx = center.x - WallClip.mc.player.posX;
                double mz = center.z - WallClip.mc.player.posZ;
                WallClip.mc.player.motionX = mx / 3.0;
                WallClip.mc.player.motionZ = mz / 3.0;
            }
        }
    }

    int getBlocks(Vec2i vec2i) {
        int i = 0;
        BlockPos pos = this.getFlooredPos();
        if (vec2i.x != 0) {
            if (this.isBlockHard(pos.add(vec2i.x, 0, 0))) {
                ++i;
            } else {
                return 0;
            }
        }
        if (vec2i.y != 0) {
            if (this.isBlockHard(pos.add(0, 0, vec2i.y))) {
                ++i;
            } else {
                return 0;
            }
        }
        return i;
    }

    boolean isBlockHard(BlockPos pos) {
        Block b = WallClip.mc.world.getBlockState(pos).getBlock();
        return b == Blocks.BEDROCK || b == Blocks.OBSIDIAN;
    }

    Vec2i getWallDir() {
        Vec2i[] vecs = new Vec2i[]{new Vec2i(1, 1), new Vec2i(1, -1), new Vec2i(-1, -1), new Vec2i(-1, 1), new Vec2i(0, 1), new Vec2i(0, -1), new Vec2i(1, 0), new Vec2i(-1, 0)};
        BlockPos pos = this.getFlooredPos();
        double bestv = 0.0;
        Vec2i best = null;
        for (Vec2i vec2i : vecs) {
            double v = (double)(this.getBlocks(vec2i) * 10) - WallClip.mc.player.getDistance((double)pos.getX() + 0.5 + (double)vec2i.x, WallClip.mc.player.posY, (double)pos.getZ() + 0.5 + (double)vec2i.y);
            if (!(v > bestv)) continue;
            bestv = v;
            best = vec2i;
        }
        return best;
    }

    BlockPos getFlooredPos() {
        return new BlockPos(Math.floor(WallClip.mc.player.posX), Math.floor(WallClip.mc.player.posY + 0.2), Math.floor(WallClip.mc.player.posZ));
    }

    class Vec2i {
        public final int x;
        public final int y;

        public Vec2i(int inX, int inY) {
            this.x = inX;
            this.y = inY;
        }

        public String toString() {
            return "Vec2i(" + this.x + "," + this.y + ")";
        }

        public boolean equals(Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            }
            if (!(p_equals_1_ instanceof Vec2i)) {
                return false;
            }
            Vec2i vec3i = (Vec2i)p_equals_1_;
            if (this.x != vec3i.x) {
                return false;
            }
            return this.y == vec3i.y;
        }

        public int hashCode() {
            return new HashCodeBuilder(17, 31).append(this.x).append(this.y).toHashCode();
        }
    }
}
