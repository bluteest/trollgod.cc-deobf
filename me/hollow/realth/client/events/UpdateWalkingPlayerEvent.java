package me.hollow.realth.client.events;

import me.hollow.realth.api.event.EventStage;
import net.b0at.api.event.Event;

public class UpdateWalkingPlayerEvent extends Event {

    public UpdateWalkingPlayerEvent(int stage) {
        super(stage);
    }
}
