package me.hollow.realth.client.events;

import net.b0at.api.event.Event;
import me.hollow.realth.api.event.EventStage;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class BlockEvent extends Event {

    public BlockPos pos;
    public EnumFacing facing;

    public BlockEvent(int stage, BlockPos pos, EnumFacing facing) {
        super(stage);
        this.pos = pos;
        this.facing = facing;
    }
}
