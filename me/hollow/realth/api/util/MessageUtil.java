//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import me.hollow.realth.api.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;

public class MessageUtil implements Util
{
    public static String prefix;
    
    public static void updatePrefix() {
        MessageUtil.prefix = ChatFormatting.DARK_PURPLE + "[" + ChatFormatting.LIGHT_PURPLE + "TrollGod.CC" + ChatFormatting.DARK_PURPLE + "]";
    }
    
    public static void sendClientMessage(final String string, final boolean deleteOld) {
        if (MessageUtil.mc.player == null) {
            return;
        }
        final TextComponentString component = new TextComponentString(MessageUtil.prefix + " " + ChatFormatting.LIGHT_PURPLE + string);
        if (deleteOld) {
            MessageUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, -727);
        }
        else {
            MessageUtil.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)component);
        }
    }
    
    public static void sendClientMessage(final String string, final int id) {
        if (MessageUtil.mc.player == null) {
            return;
        }
        final TextComponentString component = new TextComponentString(MessageUtil.prefix + " " + ChatFormatting.LIGHT_PURPLE + string);
        MessageUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, id);
    }
    
    static {
        MessageUtil.prefix = ChatFormatting.DARK_PURPLE + "[" + ChatFormatting.LIGHT_PURPLE + "TrollGod.CC" + ChatFormatting.DARK_PURPLE + "]";
    }
}
