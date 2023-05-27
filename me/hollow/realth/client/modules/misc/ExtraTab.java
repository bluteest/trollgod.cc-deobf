//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.modules.misc;

import me.hollow.realth.client.modules.*;
import me.hollow.realth.api.property.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import com.mojang.realmsclient.gui.*;
import me.hollow.realth.*;

@ModuleManifest(label = "ExtraTab", category = Category.MISC, listen = false)
public class ExtraTab extends Module
{
    public Setting<Boolean> pingDisplay;
    public Setting<Boolean> coloredPing;
    public Setting<Integer> size;
    private static ExtraTab INSTANCE;
    
    public ExtraTab() {
        this.pingDisplay = (Setting<Boolean>)this.register(new Setting("Ping", (Object)true));
        this.coloredPing = (Setting<Boolean>)this.register(new Setting("Colored", (Object)true));
        this.size = (Setting<Integer>)this.register(new Setting("Size", (Object)250, (Object)1, (Object)1000));
        ExtraTab.INSTANCE = this;
    }
    
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfo) {
        final String string = (networkPlayerInfo.getDisplayName() != null) ? networkPlayerInfo.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfo.getPlayerTeam(), networkPlayerInfo.getGameProfile().getName());
        final String prefix = "";
        String k = "";
        if (networkPlayerInfo.getDisplayName() != null) {
            k = networkPlayerInfo.getDisplayName().getUnformattedText();
        }
        else {
            k = networkPlayerInfo.getGameProfile().getName();
        }
        if (getINSTANCE().pingDisplay.getValue()) {
            if (!(boolean)getINSTANCE().coloredPing.getValue()) {
                return prefix + string + ChatFormatting.GRAY + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "");
            }
            if (networkPlayerInfo.getResponseTime() <= 50) {
                return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.GREEN + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : (prefix + string + ChatFormatting.GREEN + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : ""));
            }
            if (networkPlayerInfo.getResponseTime() <= 100) {
                return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.GOLD + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : (prefix + string + ChatFormatting.GOLD + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : ""));
            }
            if (networkPlayerInfo.getResponseTime() <= 150) {
                return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.RED + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : (prefix + string + ChatFormatting.RED + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : ""));
            }
            if (networkPlayerInfo.getResponseTime() <= 9999) {
                return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.DARK_RED + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : (prefix + string + ChatFormatting.DARK_RED + " " + (((boolean)getINSTANCE().pingDisplay.getValue()) ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : ""));
            }
        }
        if (JordoHack.INSTANCE.getFriendManager().isFriend(string)) {
            return prefix + ChatFormatting.AQUA + string;
        }
        return prefix + string;
    }
    
    public static ExtraTab getINSTANCE() {
        if (ExtraTab.INSTANCE == null) {
            ExtraTab.INSTANCE = new ExtraTab();
        }
        return ExtraTab.INSTANCE;
    }
    
    private void setInstance() {
        ExtraTab.INSTANCE = this;
    }
    
    static {
        ExtraTab.INSTANCE = new ExtraTab();
    }
}
