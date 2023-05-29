//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.network.play.server.SPacketChat
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package me.hollow.realth.client.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.realth.api.mixin.mixins.network.ISPacketChat;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.events.PacketEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.text.SimpleDateFormat;
import java.util.Date;

@ModuleManifest(label="BetterChat", category=Module.Category.RENDER)
public class BetterChat
extends Module {
    public final Setting<Boolean> timeStamps = this.register(new Setting<Boolean>("Timestamps", true));
    private final Setting<Bracket> bracket = this.register(new Setting<Bracket>("Bracket", Bracket.Triangle));
    public final Setting<Boolean> giantBeetleSoundsLikeJackhammer = this.register(new Setting<Boolean>("NoRect", true));
    public final Setting<Boolean> cFont = this.register(new Setting<Boolean>("CustomFont", true));
    public final Setting<Boolean> tabFriends = this.register(new Setting<Boolean>("FriendHighlight", true));
    private static BetterChat INSTANCE = new BetterChat();

    public BetterChat() {
        INSTANCE = this;
    }

    public static BetterChat getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BetterChat();
        }
        return INSTANCE;
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            SPacketChat packet = (SPacketChat)event.getPacket();
            if (this.timeStamps.getValue().booleanValue()) {
                ((ISPacketChat)packet).setChatComponent((ITextComponent)new TextComponentString(this.getTimeString() + packet.getChatComponent().getFormattedText()));
            }
        }
    }

    public String getTimeString() {
        String date = new SimpleDateFormat("k:mm").format(new Date());
        final String bracket = this.bracket.getValue().equals(Bracket.Triangle) ? "<" : this.bracket.getValue().equals(Bracket.Square) ? "[" : "";
        final String bracket2 = this.bracket.getValue().equals(Bracket.Triangle) ? ">" : this.bracket.getValue().equals(Bracket.Square) ? "]" : "";
        return (Object)ChatFormatting.DARK_PURPLE + bracket + (Object) ChatFormatting.LIGHT_PURPLE + date + (Object)ChatFormatting.DARK_PURPLE + bracket2 + " " + (Object)ChatFormatting.RESET;
    }

    public static enum Bracket
    {
        Square,
        Triangle,
        None;
    }
}

