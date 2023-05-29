/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package me.hollow.realth.client.events;

import net.b0at.api.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent
extends Event {
    public final Packet packet;

    public PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public static class Send
    extends PacketEvent {
        public Send(Packet packet) {
            super(packet);
        }
    }

    public static class Receive
    extends PacketEvent {
        public Receive(Packet packet) {
            super(packet);
        }
    }
}

