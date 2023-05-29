/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.events;

import net.b0at.api.event.Event;

public class TickEvent
extends Event {
    private final int stage;

    public TickEvent(int stage) {
        this.stage = stage;
    }

    public final int getStage() {
        return this.stage;
    }
}

