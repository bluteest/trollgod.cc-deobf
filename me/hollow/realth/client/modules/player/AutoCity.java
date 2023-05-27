//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.modules.*;
import net.minecraft.client.*;
import me.hollow.realth.api.property.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import me.hollow.realth.*;
import net.minecraft.init.*;
import me.hollow.realth.api.util.*;

@ModuleManifest(label = "AutoCity", category = Module.Category.PLAYER)
public class AutoCity extends Module
{
    private final Minecraft mc;
    public final Setting<Integer> targetRange;
    public final Setting<Integer> breakRange;
    public final Setting<Boolean> damage;
    public final Setting<Boolean> onePointThirteen;
    protected boolean started;
    public final Setting<Boolean> Surround;
    public final Setting<Boolean> Burrow;
    protected BlockPos interactedPos;
    protected EnumFacing interactedFace;
    protected Timer timer;
    protected EntityPlayer currentTarget;
    protected Vec3i[] offsets;
    
    public AutoCity() {
        this.mc = Minecraft.getMinecraft();
        this.targetRange = (Setting<Integer>)this.register(new Setting("Target Range", (Object)32, (Object)1, (Object)64));
        this.breakRange = (Setting<Integer>)this.register(new Setting("Break Range", (Object)5, (Object)1, (Object)6));
        this.damage = (Setting<Boolean>)this.register(new Setting("Damage", (Object)false));
        this.onePointThirteen = (Setting<Boolean>)this.register(new Setting("1.13+", (Object)false));
        this.Surround = (Setting<Boolean>)this.register(new Setting("Surround", (Object)false));
        this.Burrow = (Setting<Boolean>)this.register(new Setting("Burrow", (Object)false));
        this.timer = new Timer();
        this.currentTarget = null;
        this.offsets = new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1) };
    }
    
    public EntityPlayer getTarget() {
        return this.currentTarget;
    }
    
    protected TreeMap<Double, BlockPos> hasAnyCrystallableSides(final BlockPos blockPos3, final BlockPos blockPos4, final boolean bl) {
        return Arrays.stream(this.offsets).map((Function<? super Vec3i, ?>)blockPos3::add).filter(blockPos2 -> !blockPos2.equals((Object)blockPos4)).filter(blockPos -> BlockUtil.canPosBeCrystalledSoon(blockPos.down(), bl)).collect((Collector<? super Object, ?, TreeMap<Double, BlockPos>>)Collectors.toMap(blockPos -> this.mc.player.getDistanceSq(blockPos), blockPos -> blockPos, (blockPos, blockPos2) -> blockPos2, (Supplier<R>)TreeMap::new));
    }
    
    public void onEnable() {
        JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
    }
    
    public void onDisable() {
        JordoHack.INSTANCE.getEventManager().deregisterListener((Object)this);
    }
    
    protected BlockPos getClosestSurroundPos(final EntityPlayer currentTarget) {
        final BlockPos blockPos3 = currentTarget.getPosition();
        final boolean bl = (boolean)this.onePointThirteen.getValue();
        final boolean bl2;
        final TreeMap treeMap = Arrays.stream(this.offsets).map((Function<? super Vec3i, ?>)blockPos3::add).filter(blockPos -> this.canMine(blockPos) && this.surroundValid(blockPos, bl2, false)).collect((Collector<? super Object, ?, TreeMap>)Collectors.toMap(blockPos -> this.mc.player.getDistanceSq(blockPos), blockPos -> blockPos, (blockPos, blockPos2) -> blockPos2, (Supplier<R>)TreeMap::new));
        if (!treeMap.isEmpty()) {
            return treeMap.firstEntry().getValue();
        }
        final boolean bl3;
        final TreeMap treeMap2 = Arrays.stream(this.offsets).map((Function<? super Vec3i, ?>)blockPos3::add).filter(blockPos -> this.canMine(blockPos) && this.surroundValid(blockPos, bl3, true)).collect((Collector<? super Object, ?, TreeMap>)Collectors.toMap(blockPos -> this.mc.player.getDistanceSq(blockPos), blockPos -> blockPos, (blockPos, blockPos2) -> blockPos2, (Supplier<R>)TreeMap::new));
        if (!treeMap2.isEmpty()) {
            return treeMap2.firstEntry().getValue();
        }
        return null;
    }
    
    protected boolean surroundValid(final BlockPos blockPos, final boolean bl, final boolean bl2) {
        return this.canMine(blockPos) && BlockUtil.canPosBeCrystalled(blockPos, bl) && (bl2 || BlockUtil.canPosBeCrystalledSoon(blockPos.down(), bl)) && this.mc.player.getDistanceSq(blockPos) < (int)this.breakRange.getValue() * 2.0f;
    }
    
    protected void breakPos(final BlockPos blockPos) {
        final EnumFacing enumFacing = JordoHack.interactionManager.closestEnumFacing(blockPos);
        JordoHack.interactionManager.interactBlock(blockPos, enumFacing);
        this.start(blockPos, enumFacing);
    }
    
    protected BlockPos getBurrowPos(final EntityPlayer currentTarget) {
        final BlockPos blockPos = currentTarget.getPosition();
        return this.canMine(blockPos) ? blockPos : null;
    }
    
    protected boolean canMine(final BlockPos blockPos) {
        return !this.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() && !BlockUtil.getState(blockPos).equals(Blocks.BEDROCK) && !JordoHack.interactionManager.getVisibleSides(blockPos).isEmpty() && this.mc.player.getDistanceSq(blockPos) < (int)this.breakRange.getValue() * 2.0f;
    }
    
    protected void start(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.timer.syncTime();
        this.started = true;
        this.interactedPos = blockPos;
        this.interactedFace = enumFacing;
    }
    
    public void onUpdate() {
        if (this.currentTarget != null && CombatUtil.isInHole(this.currentTarget) && this.started && this.timer.getTime(2000L)) {
            JordoHack.interactionManager.attemptBreak(this.interactedPos, this.interactedFace);
            this.interactedFace = null;
            this.interactedPos = null;
            this.timer.syncTime();
            this.started = false;
            this.currentTarget = null;
        }
    }
}
