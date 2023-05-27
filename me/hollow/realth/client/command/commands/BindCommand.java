//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.command.commands;

import me.hollow.realth.client.command.*;
import me.hollow.realth.api.util.*;
import me.hollow.realth.*;
import org.lwjgl.input.*;
import me.hollow.realth.api.property.*;
import me.hollow.realth.client.modules.*;

@CommandManifest(label = "Bind", aliases = { "b" })
public class BindCommand extends Command
{
    public void execute(final String[] args) {
        if (args.length < 2) {
            MessageUtil.sendClientMessage("Use the command like this -> (module, bind)", true);
            return;
        }
        final Module module = JordoHack.INSTANCE.getModuleManager().getModuleByLabel(args[1]);
        if (module != null) {
            final int index = Keyboard.getKeyIndex(args[2].toUpperCase());
            module.bind.setValue((Object)new Bind(index));
            MessageUtil.sendClientMessage(module.getLabel() + " has been bound to " + Keyboard.getKeyName(index), false);
        }
    }
}
