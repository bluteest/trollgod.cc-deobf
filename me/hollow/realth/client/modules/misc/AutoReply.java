//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import net.minecraft.network.play.server.*;
import me.hollow.realth.*;
import me.hollow.realth.api.util.*;
import net.b0at.api.event.*;

@ModuleManifest(label = "AutoReply", category = Category.MISC)
public class AutoReply extends Module
{
    private static AutoReply INSTANCE;
    Setting<Boolean> coords;
    Setting<Boolean> ignoreY;
    Setting<Integer> radius;
    
    public AutoReply() {
        this.coords = (Setting<Boolean>)this.register(new Setting("Coords", (Object)true));
        this.ignoreY = (Setting<Boolean>)this.register(new Setting("IgnoreY", (Object)true, v -> (boolean)this.coords.getValue()));
        this.radius = (Setting<Integer>)this.register(new Setting("RadiusInThousands", (Object)5, (Object)1, (Object)50, v -> (boolean)this.coords.getValue()));
    }
    
    public static AutoReply getInstance() {
        if (AutoReply.INSTANCE == null) {
            AutoReply.INSTANCE = new AutoReply();
        }
        return AutoReply.INSTANCE;
    }
    
    private void setInstance() {
        AutoReply.INSTANCE = this;
    }
    
    @EventHandler
    public void onReceivePacket(final PacketEvent.Receive e) {
        if (Module.fullNullCheck() || this.isDisabled()) {
            return;
        }
        if (e.getPacket() instanceof SPacketChat) {
            final SPacketChat p = (SPacketChat)e.getPacket();
            final String msg = p.getChatComponent().getUnformattedText();
            if (msg.contains("says: ") || msg.contains("whispers: ")) {
                final String ign = msg.split(" ")[0];
                if (AutoReply.mc.player.getName() == ign) {
                    return;
                }
                if ((boolean)this.coords.getValue() && JordoHack.friendManager.isFriend(ign) && MathUtil.getDistance(0.0, AutoReply.mc.player.posY, 0.0) < (int)this.radius.getValue() * 1000) {
                    final String lowerCaseMsg = msg.toLowerCase();
                    if (lowerCaseMsg.contains("cord") || lowerCaseMsg.contains("coord") || lowerCaseMsg.contains("coords") || lowerCaseMsg.contains("cords") || lowerCaseMsg.contains("wya") || lowerCaseMsg.contains("where are you") || lowerCaseMsg.contains("where r u") || lowerCaseMsg.contains("where ru")) {
                        if (lowerCaseMsg.contains("discord") || lowerCaseMsg.contains("record")) {
                            return;
                        }
                        final int x = (int)AutoReply.mc.player.posX;
                        final int y = (int)AutoReply.mc.player.posY;
                        final int z = (int)AutoReply.mc.player.posZ;
                        AutoReply.mc.player.sendChatMessage("/msg " + ign + " " + x + "x " + (this.ignoreY.getValue() ? "" : (y + "y ")) + z + "z");
                    }
                }
            }
        }
    }
    
    static {
        AutoReply.INSTANCE = new AutoReply();
    }
}
