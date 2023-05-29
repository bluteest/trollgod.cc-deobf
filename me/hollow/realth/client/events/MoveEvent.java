/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.events;

import net.b0at.api.event.Event;
import net.minecraftforge.fml.common.eventhandler.Cancelable;


@Cancelable
public class MoveEvent extends Event {

    private double motionX, motionY, motionZ;

    public MoveEvent(double motionX, double motionY, double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    public double getMotionX() {
        return motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }


    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }

}

