//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.mixin.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.hollow.realth.client.modules.render.*;
import net.minecraft.init.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockModelRenderer.class })
public class MixinBlockRender
{
    @Inject(method = { "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z" }, at = { @At("INVOKE") }, cancellable = true)
    public void render(final IBlockAccess worldIn, final IBakedModel modelIn, final IBlockState stateIn, final BlockPos posIn, final BufferBuilder buffer, final boolean checkSides, final long rand, final CallbackInfoReturnable<Boolean> cir) {
        if (NoRender.getInstance().isEnabled() && NoRender.getInstance().boxedVines.getValue() && stateIn.getBlock() == Blocks.VINE) {
            cir.cancel();
        }
    }
}
