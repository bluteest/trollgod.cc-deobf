//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.command.commands;

import me.hollow.realth.client.command.*;
import me.hollow.realth.*;

@CommandManifest(label = "Save", aliases = { "s" })
public class SaveCommand extends Command
{
    public void execute(final String[] args) {
        JordoHack.INSTANCE.getConfigManager().saveConfig(JordoHack.INSTANCE.getConfigManager().config.replaceFirst("TrollGod/", ""));
    }
}
