package me.hollow.realth.client.events;

import net.b0at.api.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class DeathEvent extends Event {

    public EntityPlayer player;

    public DeathEvent(EntityPlayer player) {
        this.player = player;
    }
}

