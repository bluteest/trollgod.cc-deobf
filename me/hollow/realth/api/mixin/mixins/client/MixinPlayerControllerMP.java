/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.multiplayer.PlayerControllerMP
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 */
package me.hollow.realth.api.mixin.mixins.client;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.mixin.accessors.IPlayerControllerMP;
import me.hollow.realth.client.events.BreakBlockEvent;
import me.hollow.realth.client.events.ClickBlockEvent;
import me.hollow.realth.client.events.BlockEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerControllerMP.class})
public abstract class MixinPlayerControllerMP
implements IPlayerControllerMP {

    @Override
    @Accessor(value="isHittingBlock")
    public abstract void setIsHittingBlock(boolean var1);

    @Inject(method={"clickBlock"}, at={@At(value="HEAD")}, cancellable=true)
    public void clickBlock(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
        ClickBlockEvent event = new ClickBlockEvent(loc, face);
        JordoHack.INSTANCE.getEventManager().fireEvent(event);
        if (event.isCancelled()) {
            cir.cancel();
        }
    }

    @Redirect(method = {"onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
        public float getPlayerRelativeBlockHardnessHook(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
            return state.getPlayerRelativeBlockHardness(player, worldIn, pos) ;
    }
    @Inject(method = {"onPlayerDamageBlock"}, at = {@At(value = "HEAD")}, cancellable = true)

    private void onPlayerDamageBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
        JordoHack.INSTANCE.getEventManager().fireEvent(new BlockEvent(4, pos, face));
    }
    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
        private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> info){
            JordoHack.INSTANCE.getEventManager().fireEvent(new BreakBlockEvent(pos));
        }
    }


