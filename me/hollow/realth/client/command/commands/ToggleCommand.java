//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.command.commands;

import me.hollow.realth.client.command.*;
import me.hollow.realth.*;
import me.hollow.realth.api.util.*;
import me.hollow.realth.client.modules.*;

@CommandManifest(label = "Toggle", aliases = { "t" })
public class ToggleCommand extends Command
{
    public void execute(final String[] args) {
        if (args.length < 2) {
            return;
        }
        final Module module = JordoHack.INSTANCE.getModuleManager().getModuleByLabel(args[1]);
        if (module != null) {
            module.toggle();
            MessageUtil.sendClientMessage(module.getLabel() + " has been toggled " + (module.isEnabled() ? "on" : "off"), false);
        }
    }
}
