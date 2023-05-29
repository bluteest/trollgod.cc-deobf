/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.hollow.realth.client.events;

import net.b0at.api.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class TotemPopEvent extends Event {

    private final EntityPlayer entity;

    public TotemPopEvent(EntityPlayer entity) {
        this.entity = entity;
    }

    public EntityPlayer getEntity() {
        return entity;
    }
}

