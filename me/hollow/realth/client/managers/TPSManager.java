//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.api.*;
import me.hollow.realth.api.util.*;
import java.text.*;
import me.hollow.realth.*;
import net.minecraft.util.math.*;
import me.hollow.realth.client.events.*;
import net.minecraft.network.play.server.*;
import net.b0at.api.event.*;

public class TPSManager implements Util
{
    private long prevTime;
    private final float[] ticks;
    private int currentTick;
    private float TPS;
    private long lastUpdate;
    private final Timer timer;
    private final float[] tpsCounts;
    private final DecimalFormat format;
    
    public TPSManager() {
        this.ticks = new float[20];
        this.TPS = 20.0f;
        this.lastUpdate = -1L;
        this.timer = new Timer();
        this.tpsCounts = new float[10];
        this.format = new DecimalFormat("##.00#");
    }
    
    public void load() {
        this.prevTime = -1L;
        for (int len = this.ticks.length, i = 0; i < len; ++i) {
            this.ticks[i] = 0.0f;
        }
        JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
    }
    
    public final float getTpsFactor() {
        return 20.0f / this.TPS;
    }
    
    public float getTickRate() {
        int tickCount = 0;
        float tickRate = 0.0f;
        for (final float tick : this.ticks) {
            if (tick > 0.0f) {
                tickRate += tick;
                ++tickCount;
            }
        }
        return MathHelper.clamp(tickRate / tickCount, 0.0f, 20.0f);
    }
    
    @EventHandler
    public void onPacketReceive(final PacketEvent.Receive event) {
        this.timer.reset();
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            final long currentTime = System.currentTimeMillis();
            if (this.lastUpdate == -1L) {
                this.lastUpdate = currentTime;
                return;
            }
            final long timeDiff = currentTime - this.lastUpdate;
            float tickTime = timeDiff / 20.0f;
            if (tickTime == 0.0f) {
                tickTime = 50.0f;
            }
            final float tps = 1000.0f / tickTime;
            System.arraycopy(this.tpsCounts, 0, this.tpsCounts, 1, this.tpsCounts.length - 1);
            this.tpsCounts[0] = tps;
            this.TPS = tps;
            this.lastUpdate = currentTime;
        }
    }
    
    public void reset() {
        this.TPS = 20.0f;
    }
    
    public float getTPS() {
        return this.TPS;
    }
    
    public int getPing() {
        if (TPSManager.mc.player == null || TPSManager.mc.world == null || TPSManager.mc.getConnection() == null || TPSManager.mc.getConnection().getPlayerInfo(TPSManager.mc.getConnection().getGameProfile().getId()) == null) {
            return -1;
        }
        return TPSManager.mc.getConnection().getPlayerInfo(TPSManager.mc.getConnection().getGameProfile().getId()).getResponseTime();
    }
    
    @EventHandler
    public void receivePacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (this.prevTime != -1L) {
                this.ticks[this.currentTick % this.ticks.length] = MathHelper.clamp(20.0f / ((System.currentTimeMillis() - this.prevTime) / 1000.0f), 0.0f, 20.0f);
                ++this.currentTick;
            }
            this.prevTime = System.currentTimeMillis();
        }
    }
}
