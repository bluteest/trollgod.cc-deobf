//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

public class Timer
{
    private long current;
    
    public Timer() {
        this.current = -1L;
    }
    
    public boolean hasReached(final long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }
    
    public boolean hasReached(final long delay, final boolean reset) {
        if (reset) {
            this.reset();
        }
        return System.currentTimeMillis() - this.current >= delay;
    }
    
    public void reset() {
        this.current = System.currentTimeMillis();
    }
    
    public void reset2() {
        this.current = System.nanoTime();
    }
    
    public void setMs(final long ms) {
        this.current = ms;
    }
    
    public void syncTime() {
        this.current = System.nanoTime();
    }
    
    public long getTimePassed() {
        return System.currentTimeMillis() - this.current;
    }
    
    public boolean getTime(final long l) {
        return this.nanoToMs(System.nanoTime() - this.current) >= l;
    }
    
    public long nanoToMs(final long l) {
        return l / 1000000L;
    }
    
    public boolean passedS(final double s) {
        return this.passedMs((long)s * 1000L);
    }
    
    public boolean passedNS(final long ns) {
        return System.nanoTime() - this.current >= ns;
    }
    
    public boolean passedMs(final long ms) {
        return this.passedNS(this.convertToNS(ms));
    }
    
    public long convertToNS(final long time) {
        return time * 1000000L;
    }
    
    public long getPassedTimeMs() {
        return this.nanoToMs(System.nanoTime() - this.current);
    }
}
