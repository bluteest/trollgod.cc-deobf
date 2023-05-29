/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.events;

import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import net.b0at.api.event.Event;

public class ClientEvent
extends Event {
    Module module;
    Setting setting;
    private int stage;

    public ClientEvent(Setting setting) {
        this.setting = setting;
    }

    public Module getModule() {
        return this.module;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public ClientEvent() {
    }

    public ClientEvent(int stage) {
        this.stage = stage;
    }

    public int getStage() {
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}

