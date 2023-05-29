package me.hollow.realth.client.modules.misc;

import me.hollow.realth.JordoHack;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.client.events.PacketEvent;
import me.hollow.realth.api.util.MathUtil;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.server.SPacketChat;


// @author lyric
@ModuleManifest(label="AutoReply", category=Module.Category.MISC)
public class AutoReply extends Module {
    private static AutoReply INSTANCE = new AutoReply();
    public static AutoReply getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AutoReply();
        }
        return INSTANCE;
    }
    private void setInstance() {
        INSTANCE = this;
    }
    Setting<Boolean> coords = this.register(new Setting("Coords", true));
    Setting<Boolean> ignoreY = register(new Setting("IgnoreY", true, v -> coords.getValue()));
    Setting<Integer> radius = register(new Setting("RadiusInThousands", 5, 1, 50, v -> coords.getValue()));

    @EventHandler
    public void onReceivePacket(PacketEvent.Receive e)  {
        if (fullNullCheck() || isDisabled()) {
            return;
        }
        if (e.getPacket() instanceof SPacketChat) {
            SPacketChat p = (SPacketChat) e.getPacket();
            String msg = p.getChatComponent().getUnformattedText();
            if (msg.contains("says: ") || msg.contains("whispers: ")) {
                String ign = msg.split(" ")[0];
                if (mc.player.getName() == ign) {
                    return;
                }
                if (coords.getValue() && JordoHack.friendManager.isFriend((ign)) && MathUtil.getDistance(0, mc.player.posY, 0) < radius.getValue() * 1000) {
                    String lowerCaseMsg = msg.toLowerCase();
                    if (lowerCaseMsg.contains("cord") || lowerCaseMsg.contains("coord") || lowerCaseMsg.contains("coords") || lowerCaseMsg.contains("cords") || lowerCaseMsg.contains("wya") || lowerCaseMsg.contains("where are you") || lowerCaseMsg.contains("where r u") || lowerCaseMsg.contains("where ru")) {
                        if (lowerCaseMsg.contains("discord") || lowerCaseMsg.contains("record")) {
                            return;
                        }
                        int x = (int) mc.player.posX;
                        int y = (int) mc.player.posY;
                        int z = (int) mc.player.posZ;
                        mc.player.sendChatMessage("/msg " + ign + (" " + x + "x " + (ignoreY.getValue() ? "" : y + "y ") + z + "z"));
                    }
                }
            }
        }
    }
}
