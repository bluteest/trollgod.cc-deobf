//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.events.*;
import net.minecraft.network.play.server.*;
import me.hollow.realth.api.mixin.mixins.network.*;
import net.minecraft.util.text.*;
import net.b0at.api.event.*;
import java.text.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;

@ModuleManifest(label = "BetterChat", category = Module.Category.RENDER)
public class BetterChat extends Module
{
    public final Setting<Boolean> timeStamps;
    private final Setting<Bracket> bracket;
    public final Setting<Boolean> giantBeetleSoundsLikeJackhammer;
    public final Setting<Boolean> cFont;
    public final Setting<Boolean> tabFriends;
    private static BetterChat INSTANCE;
    
    public BetterChat() {
        this.timeStamps = (Setting<Boolean>)this.register(new Setting("Timestamps", (Object)true));
        this.bracket = (Setting<Bracket>)this.register(new Setting("Bracket", (Object)Bracket.Triangle));
        this.giantBeetleSoundsLikeJackhammer = (Setting<Boolean>)this.register(new Setting("NoRect", (Object)true));
        this.cFont = (Setting<Boolean>)this.register(new Setting("CustomFont", (Object)true));
        this.tabFriends = (Setting<Boolean>)this.register(new Setting("FriendHighlight", (Object)true));
        BetterChat.INSTANCE = this;
    }
    
    public static BetterChat getInstance() {
        if (BetterChat.INSTANCE == null) {
            BetterChat.INSTANCE = new BetterChat();
        }
        return BetterChat.INSTANCE;
    }
    
    @EventHandler
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (this.timeStamps.getValue()) {
                ((ISPacketChat)packet).setChatComponent((ITextComponent)new TextComponentString(this.getTimeString() + packet.getChatComponent().getFormattedText()));
            }
        }
    }
    
    public String getTimeString() {
        final String date = new SimpleDateFormat("k:mm").format(new Date());
        final String bracket = ((Bracket)this.bracket.getValue()).equals(Bracket.Triangle) ? "<" : (((Bracket)this.bracket.getValue()).equals(Bracket.Square) ? "[" : "");
        final String bracket2 = ((Bracket)this.bracket.getValue()).equals(Bracket.Triangle) ? ">" : (((Bracket)this.bracket.getValue()).equals(Bracket.Square) ? "]" : "");
        return ChatFormatting.DARK_PURPLE + bracket + ChatFormatting.LIGHT_PURPLE + date + ChatFormatting.DARK_PURPLE + bracket2 + " " + ChatFormatting.RESET;
    }
    
    static {
        BetterChat.INSTANCE = new BetterChat();
    }
    
    public enum Bracket
    {
        Square, 
        Triangle, 
        None;
    }
}
