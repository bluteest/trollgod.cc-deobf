/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.hollow.realth.client.command.commands;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Bind;
import me.hollow.realth.api.util.MessageUtil;
import me.hollow.realth.client.command.Command;
import me.hollow.realth.client.command.CommandManifest;
import me.hollow.realth.client.modules.Module;
import org.lwjgl.input.Keyboard;

@CommandManifest(label="Bind", aliases={"b"})
public class BindCommand
extends Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            MessageUtil.sendClientMessage("Use the command like this -> (module, bind)", true);
            return;
        }
        Module module = JordoHack.INSTANCE.getModuleManager().getModuleByLabel(args[1]);
        if (module != null) {
            int index = Keyboard.getKeyIndex((String)args[2].toUpperCase());
            module.bind.setValue(new Bind(index));
            MessageUtil.sendClientMessage(module.getLabel() + " has been bound to " + Keyboard.getKeyName((int)index), false);
        }
    }
}

