/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.api.util;

public class Timer {
    private long current = -1L;

    public boolean hasReached(long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }

    public boolean hasReached(long delay, boolean reset) {
        if (reset) {
            this.reset();
        }
        return System.currentTimeMillis() - this.current >= delay;
    }

    public void reset() {
        this.current = System.currentTimeMillis();
    }

    public void reset2() {
        current = System.nanoTime();
    }

    public void setMs(long ms) {
        this.current = ms;
    }

    public void syncTime() {
        this.current = System.nanoTime();
    }
    public long getTimePassed() {
        return System.currentTimeMillis() - this.current;
    }
    public boolean getTime(long l) {
        return this.nanoToMs(System.nanoTime() - this.current) >= l;
    }

    public long nanoToMs(long l) {
        return l / 1000000L;
    }
    public boolean passedS(double s) {
        return passedMs((long) s * 1000L);
    }
    public
    boolean passedNS ( long ns ) {
        return System.nanoTime ( ) - this.current >= ns;
    }
    public
    boolean passedMs ( long ms ) {
        return this.passedNS ( this.convertToNS ( ms ) );
    }
    public
    long convertToNS ( long time ) {
        return time * 1000000L;
    }
    public long getPassedTimeMs() {
        return this.nanoToMs(System.nanoTime() - this.current);
    }

}

