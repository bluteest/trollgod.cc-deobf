/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.command.commands;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.MessageUtil;
import me.hollow.realth.client.command.Command;
import me.hollow.realth.client.command.CommandManifest;
import me.hollow.realth.client.modules.Module;

@CommandManifest(label="Drawn", aliases={"Hide", "d"})
public class DrawnCommand
extends Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            return;
        }
        Module module = JordoHack.INSTANCE.getModuleManager().getModuleByLabel(args[1]);
        if (module != null) {
            module.setDrawn(!module.isHidden());
            MessageUtil.sendClientMessage(module.getLabel() + " has been " + (module.isHidden() ? "hidden" : "unhidden"), false);
        }
    }
}

