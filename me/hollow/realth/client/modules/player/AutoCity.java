package me.hollow.realth.client.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.Util;
import me.hollow.realth.client.managers.PlayerManager;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.api.util.BlockUtil;
import me.hollow.realth.api.util.Timer;
import me.hollow.realth.api.util.*;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;


//author lyric
@ModuleManifest(label="AutoCity", category=Module.Category.PLAYER)
public class AutoCity extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    public final Setting<Integer> breakRange = this.register(new Setting<Integer>("Break Range", 5, 1, 6));
    public final Setting<Boolean> onePointThirteen = this.register(new Setting<Boolean>("1.13+", false));
    protected boolean started;
    public final Setting<Boolean> Surround = this.register(new Setting<Boolean>("Surround", false));

    public final Setting<Boolean> Burrow = this.register(new Setting<Boolean>("Burrow", false));
    protected BlockPos interactedPos;
    protected EnumFacing interactedFace;
    protected Timer timer = new Timer();
    protected PlayerManager.Player target;
    protected Vec3i[] offsets = new Vec3i[]{new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1)};

    public PlayerManager.Player getTarget()
    {
        return target;
    }
    protected boolean perform( final PlayerManager.Player player) {
         if(Burrow.getValue())
         {
             final BlockPos burrowPos = getBurrowPos(player);
             if (burrowPos != null) {
                 breakPos(burrowPos);
                 return true;
             }
         }
         if (Surround.getValue())
         {
             final BlockPos surroundPos = getClosestSurroundPos(player);
             if (surroundPos != null) {
                 breakPos(surroundPos);
                 return true;
             }
         }
        return false;
    }
    @Override
    public void onEnable()
    {
        JordoHack.INSTANCE.getEventManager().registerListener(this);
    }
    @Override
    public void onDisable()
    {
        JordoHack.INSTANCE.getEventManager().deregisterListener(this);
    }

    protected BlockPos getClosestSurroundPos(PlayerManager.Player target) {
        BlockPos blockPos3 = target.getPosition();
        boolean bl = this.onePointThirteen.getValue();
        TreeMap treeMap = Arrays.stream(this.offsets).map(blockPos3::add).filter(blockPos -> this.canMine((BlockPos)blockPos) && this.surroundValid((BlockPos)blockPos, bl, false)).collect(Collectors.toMap(blockPos -> this.mc.player.getDistanceSq((BlockPos)blockPos), blockPos -> blockPos, (blockPos, blockPos2) -> blockPos2, TreeMap::new));
        if (!treeMap.isEmpty()) {
            return (BlockPos)treeMap.firstEntry().getValue();
        }
        TreeMap treeMap2 = Arrays.stream(this.offsets).map(blockPos3::add).filter(blockPos -> this.canMine((BlockPos)blockPos) && this.surroundValid((BlockPos)blockPos, bl, true)).collect(Collectors.toMap(blockPos -> this.mc.player.getDistanceSq((BlockPos)blockPos), blockPos -> blockPos, (blockPos, blockPos2) -> blockPos2, TreeMap::new));
        if (!treeMap2.isEmpty()) {
            return (BlockPos)treeMap2.firstEntry().getValue();
        }
        return null;
    }

    protected boolean surroundValid(BlockPos blockPos, boolean bl, boolean bl2) {
        return this.canMine(blockPos) && BlockUtil.canPosBeCrystalled(blockPos, bl) && (bl2 || BlockUtil.canPosBeCrystalledSoon(blockPos.down(), bl)) && this.mc.player.getDistanceSq(blockPos) < (double)(this.breakRange.getValue() * 2.0f);
    }

    protected void breakPos(BlockPos blockPos) {
        EnumFacing enumFacing = JordoHack.interactionManager.closestEnumFacing(blockPos);
        JordoHack.interactionManager.interactBlock(blockPos, enumFacing);
        this.start(blockPos, enumFacing);
    }

    protected BlockPos getBurrowPos(PlayerManager.Player target) {
        BlockPos blockPos = target.getPosition();
        return this.canMine(blockPos) ? blockPos : null;
    }

    protected boolean canMine(BlockPos blockPos) {
        return !this.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() && !BlockUtil.getState(blockPos).equals(Blocks.BEDROCK) && !JordoHack.interactionManager.getVisibleSides(blockPos).isEmpty() && this.mc.player.getDistanceSq(blockPos) < (double)(this.breakRange.getValue() * 2.0f);
    }

    protected void start(BlockPos blockPos, EnumFacing enumFacing) {
        this.timer.syncTime();
        this.started = true;
        this.interactedPos = blockPos;
        this.interactedFace = enumFacing;
    }
}

