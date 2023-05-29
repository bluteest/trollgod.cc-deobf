/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.command.commands;

import me.hollow.realth.JordoHack;
import me.hollow.realth.client.command.Command;
import me.hollow.realth.client.command.CommandManifest;

@CommandManifest(label="Save", aliases={"s"})
public class SaveCommand
extends Command {
    @Override
    public void execute(String[] args) {
        JordoHack.INSTANCE.getConfigManager().saveConfig(JordoHack.INSTANCE.getConfigManager().config.replaceFirst("TrollGod/", ""));
    }
}

