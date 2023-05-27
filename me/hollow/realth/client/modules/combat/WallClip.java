//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.combat;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.b0at.api.event.*;
import me.hollow.realth.client.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import org.apache.commons.lang3.builder.*;

@ModuleManifest(label = "PacketClip", category = Category.COMBAT)
public class WallClip extends Module
{
    public Setting<Integer> clipAmount;
    public Setting<Integer> clipStep;
    int disable;
    
    public WallClip() {
        this.clipAmount = (Setting<Integer>)this.register(new Setting("Amount", (Object)6, (Object)1, (Object)6));
        this.clipStep = (Setting<Integer>)this.register(new Setting("Step", (Object)3, (Object)1, (Object)6));
        this.disable = 0;
    }
    
    @EventHandler
    public void onMove(final MoveEvent event) {
        if (this.isEnabled()) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onUpdate(final UpdateEvent event) {
        if (fullNullCheck()) {
            return;
        }
        final Vec2i dir = this.getWallDir();
        if (dir == null) {
            this.setEnabled(false);
            return;
        }
        final double amt = (int)this.clipAmount.getValue() * 0.01 - 0.001;
        final BlockPos pos = this.getFlooredPos();
        final Vec3d center = new Vec3d(pos.getX() + 0.5 + 0.2 * dir.x, (double)pos.getY(), pos.getZ() + 0.5 + 0.2 * dir.y);
        Vec3d target = new Vec3d(center.x + amt * dir.x, center.y, center.z + amt * dir.y);
        final double x = Math.abs(target.x - WallClip.mc.player.posX);
        final double z = Math.abs(target.z - WallClip.mc.player.posZ);
        if (x <= amt + 0.01 && z <= amt + 0.01) {
            WallClip.mc.player.setVelocity(0.0, 0.0, 0.0);
            if (x <= amt + 0.001 && z <= amt + 0.001) {
                if (x <= 0.001 && z <= 0.001) {
                    if (this.disable > 5) {
                        this.setEnabled(false);
                    }
                    else {
                        ++this.disable;
                    }
                    return;
                }
                this.disable = 0;
                final double mx = Math.abs(Math.max(0.0, amt - x) + (int)this.clipStep.getValue() * 0.01);
                final double mz = Math.abs(Math.max(0.0, amt - z) + (int)this.clipStep.getValue() * 0.01);
                target = new Vec3d(center.x + dir.x * Math.min(mx, amt), center.y, center.z + dir.y * Math.min(mz, amt));
                WallClip.mc.player.setPosition(target.x, target.y, target.z);
                WallClip.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(WallClip.mc.player.posX, WallClip.mc.player.posY, WallClip.mc.player.posZ, WallClip.mc.player.onGround));
                WallClip.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(WallClip.mc.player.posX + 0.1 * dir.y, WallClip.mc.player.posY, WallClip.mc.player.posZ + 0.1 * dir.y, WallClip.mc.player.onGround));
            }
            else {
                final double mx = center.x - WallClip.mc.player.posX;
                final double mz = center.z - WallClip.mc.player.posZ;
                WallClip.mc.player.motionX = mx / 3.0;
                WallClip.mc.player.motionZ = mz / 3.0;
            }
        }
    }
    
    int getBlocks(final Vec2i vec2i) {
        int i = 0;
        final BlockPos pos = this.getFlooredPos();
        if (vec2i.x != 0) {
            if (!this.isBlockHard(pos.add(vec2i.x, 0, 0))) {
                return 0;
            }
            ++i;
        }
        if (vec2i.y != 0) {
            if (!this.isBlockHard(pos.add(0, 0, vec2i.y))) {
                return 0;
            }
            ++i;
        }
        return i;
    }
    
    boolean isBlockHard(final BlockPos pos) {
        final Block b = WallClip.mc.world.getBlockState(pos).getBlock();
        return b == Blocks.BEDROCK || b == Blocks.OBSIDIAN;
    }
    
    Vec2i getWallDir() {
        final Vec2i[] vecs = { new Vec2i(1, 1), new Vec2i(1, -1), new Vec2i(-1, -1), new Vec2i(-1, 1), new Vec2i(0, 1), new Vec2i(0, -1), new Vec2i(1, 0), new Vec2i(-1, 0) };
        final BlockPos pos = this.getFlooredPos();
        double bestv = 0.0;
        Vec2i best = null;
        for (final Vec2i vec2i : vecs) {
            final double v = this.getBlocks(vec2i) * 10 - WallClip.mc.player.getDistance(pos.getX() + 0.5 + vec2i.x, WallClip.mc.player.posY, pos.getZ() + 0.5 + vec2i.y);
            if (v > bestv) {
                bestv = v;
                best = vec2i;
            }
        }
        return best;
    }
    
    BlockPos getFlooredPos() {
        return new BlockPos(Math.floor(WallClip.mc.player.posX), Math.floor(WallClip.mc.player.posY + 0.2), Math.floor(WallClip.mc.player.posZ));
    }
    
    class Vec2i
    {
        public final int x;
        public final int y;
        
        public Vec2i(final int inX, final int inY) {
            this.x = inX;
            this.y = inY;
        }
        
        @Override
        public String toString() {
            return "Vec2i(" + this.x + "," + this.y + ")";
        }
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            }
            if (!(p_equals_1_ instanceof Vec2i)) {
                return false;
            }
            final Vec2i vec3i = (Vec2i)p_equals_1_;
            return this.x == vec3i.x && this.y == vec3i.y;
        }
        
        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31).append(this.x).append(this.y).toHashCode();
        }
    }
}
