/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

@ModuleManifest(label="ExtraTab", category=Module.Category.MISC, listen=false)
public class ExtraTab
extends Module {
    public Setting<Boolean> pingDisplay = this.register(new Setting<Boolean>("Ping", true));
    public Setting<Boolean> coloredPing = this.register(new Setting<Boolean>("Colored", true));
    public Setting<Integer> size = this.register(new Setting<Integer>("Size", 250, 1, 1000));
    private static ExtraTab INSTANCE = new ExtraTab();

    public ExtraTab() {
        INSTANCE = this;
    }

    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfo) {
        String string = networkPlayerInfo.getDisplayName() != null ? networkPlayerInfo.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfo.getPlayerTeam(), (String)networkPlayerInfo.getGameProfile().getName());
        String prefix = "";
        String k = "";
        if(networkPlayerInfo.getDisplayName() != null) {
            k = networkPlayerInfo.getDisplayName().getUnformattedText();
        }
        else
        {
            k = networkPlayerInfo.getGameProfile().getName();
        }
        if (ExtraTab.getINSTANCE().pingDisplay.getValue().booleanValue()) {
            if (ExtraTab.getINSTANCE().coloredPing.getValue().booleanValue()) {
                if (networkPlayerInfo.getResponseTime() <= 50) {
                    return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.GREEN + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : prefix + string + ChatFormatting.GREEN + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "");
                }
                if (networkPlayerInfo.getResponseTime() <= 100) {
                    return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.GOLD + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : prefix + string + ChatFormatting.GOLD + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "");
                }
                if (networkPlayerInfo.getResponseTime() <= 150) {
                    return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.RED + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : prefix + string + ChatFormatting.RED + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "");
                }
                if (networkPlayerInfo.getResponseTime() <= 9999) {
                    return JordoHack.INSTANCE.getFriendManager().isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.DARK_RED + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : prefix + string + ChatFormatting.DARK_RED + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "");
                }
                //OyVey.friendManager.isFriend(string) ? (prefix + ChatFormatting.AQUA + string + ChatFormatting.DARK_RED + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "")) : prefix + string + ChatFormatting.DARK_RED + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "");
            } else {
                return prefix + string + ChatFormatting.GRAY + " " + (ExtraTab.getINSTANCE().pingDisplay.getValue() != false ? Integer.valueOf(networkPlayerInfo.getResponseTime()) : "");
            }
        }
        if (JordoHack.INSTANCE.getFriendManager().isFriend(string)) {
            return prefix + ChatFormatting.AQUA + string;
        }
        return prefix + string;
    }

    public static ExtraTab getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ExtraTab();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}

