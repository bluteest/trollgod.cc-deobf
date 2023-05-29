/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.events;

import net.b0at.api.event.Event;

public class UpdateEvent
extends Event {
    private final int stage;

    public UpdateEvent(int stage) {
        this.stage = stage;
    }

    public final int getStage() {
        return this.stage;
    }
}

