/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.events;

import net.b0at.api.event.Event;

public class KeyEvent
extends Event {
    private final int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}

