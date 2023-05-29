/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.command.commands;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.MessageUtil;
import me.hollow.realth.client.command.Command;
import me.hollow.realth.client.command.CommandManifest;
import me.hollow.realth.client.modules.Module;

@CommandManifest(label="Toggle", aliases={"t"})
public class ToggleCommand
extends Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            return;
        }
        Module module = JordoHack.INSTANCE.getModuleManager().getModuleByLabel(args[1]);
        if (module != null) {
            module.toggle();
            MessageUtil.sendClientMessage(module.getLabel() + " has been toggled " + (module.isEnabled() ? "on" : "off"), false);
        }
    }
}

