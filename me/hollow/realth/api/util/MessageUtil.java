//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package me.hollow.realth.api.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hollow.realth.api.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class MessageUtil
implements Util {
    public static String prefix =(Object)ChatFormatting.DARK_PURPLE + "[" + (Object)ChatFormatting.LIGHT_PURPLE + "TrollGod.CC" + (Object)ChatFormatting.DARK_PURPLE + "]";

    public static void updatePrefix() {
        prefix = (Object)ChatFormatting.DARK_PURPLE + "[" + (Object)ChatFormatting.LIGHT_PURPLE + "TrollGod.CC" + (Object)ChatFormatting.DARK_PURPLE + "]";
    }

    public static void sendClientMessage(String string, boolean deleteOld) {
        if (MessageUtil.mc.player == null) {
            return;
        }
        TextComponentString component = new TextComponentString(prefix + " " + (Object)ChatFormatting.LIGHT_PURPLE + string);
        if (deleteOld) {
            MessageUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, -727);
        } else {
            MessageUtil.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)component);
        }
    }

    public static void sendClientMessage(String string, int id) {
        if (MessageUtil.mc.player == null) {
            return;
        }
        TextComponentString component = new TextComponentString(prefix + " " + (Object)ChatFormatting.LIGHT_PURPLE + string);
        MessageUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, id);
    }
}

