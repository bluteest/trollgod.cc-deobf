//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 *  net.minecraft.util.math.MathHelper
 */
package me.hollow.realth.client.managers;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.Util;
import me.hollow.realth.api.util.Timer;
import me.hollow.realth.client.events.PacketEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.text.DecimalFormat;
import java.util.Arrays;

public class TPSManager
implements Util {
    private long prevTime;
    private final float[] ticks = new float[20];
    private int currentTick;
    private float TPS = 20.0f;
    private long lastUpdate = -1L;
    private final Timer timer = new Timer();
    private final float[] tpsCounts = new float[10];
    private final DecimalFormat format = new DecimalFormat("##.00#");

    public void load() {
        this.prevTime = -1L;
        int len = this.ticks.length;
        for (int i = 0; i < len; ++i) {
            this.ticks[i] = 0.0f;
        }
        JordoHack.INSTANCE.getEventManager().registerListener(this);
    }
    public final float getTpsFactor() {
        return 20.0f / this.TPS;
    }

    public float getTickRate() {
        int tickCount = 0;
        float tickRate = 0.0f;
        for (float tick : this.ticks) {
            if (!(tick > 0.0f)) continue;
            tickRate += tick;
            ++tickCount;
        }
        return MathHelper.clamp((float)(tickRate / (float)tickCount), (float)0.0f, (float)20.0f);
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        timer.reset();
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            long currentTime = System.currentTimeMillis();

            if (lastUpdate == -1) {
                lastUpdate = currentTime;
                return;
            }

            long timeDiff = currentTime - lastUpdate;

            float tickTime = timeDiff / 20.0F;
            if (tickTime == 0) {
                tickTime = 50;
            }

            float tps = 1000 / tickTime;

            System.arraycopy(tpsCounts, 0, tpsCounts, 1, tpsCounts.length - 1);
            tpsCounts[0] = tps;

            this.TPS = tps;
            lastUpdate = currentTime;
        }
    }


    public void reset() {
        this.TPS = 20.0f;
    }

    public float getTPS() {
        return this.TPS;
    }


    public int getPing() {
        if (mc.player == null || mc.world == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.getConnection().getGameProfile().getId()) == null) {
            return -1;
        }
        return mc.getConnection().getPlayerInfo(mc.getConnection().getGameProfile().getId()).getResponseTime();
    }

    @EventHandler
    public void receivePacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (this.prevTime != -1L) {
                this.ticks[this.currentTick % this.ticks.length] = MathHelper.clamp((float)(20.0f / ((float)(System.currentTimeMillis() - this.prevTime) / 1000.0f)), (float)0.0f, (float)20.0f);
                ++this.currentTick;
            }
            this.prevTime = System.currentTimeMillis();
        }
    }
}

