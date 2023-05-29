/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.command;

import me.hollow.realth.client.command.CommandManifest;

public class Command {
    String label;
    String[] aliases;

    public Command() {
        if (this.getClass().isAnnotationPresent(CommandManifest.class)) {
            CommandManifest manifest = this.getClass().getAnnotation(CommandManifest.class);
            this.label = manifest.label();
            this.aliases = manifest.aliases();
        }
    }

    public void execute(String[] args) {
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getAliases() {
        return this.aliases;
    }
}

