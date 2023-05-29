package me.hollow.realth.client.events;

import me.hollow.realth.api.event.EventStage;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PushEvent
        extends EventStage {
    private final Type type;

    public PushEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        BLOCK,
        ENTITY,
        PISTON
    }

}