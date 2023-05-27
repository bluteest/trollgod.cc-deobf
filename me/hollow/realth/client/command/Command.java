//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.command;

import java.lang.annotation.*;

public class Command
{
    String label;
    String[] aliases;
    
    public Command() {
        if (this.getClass().isAnnotationPresent(CommandManifest.class)) {
            final CommandManifest manifest = this.getClass().getAnnotation(CommandManifest.class);
            this.label = manifest.label();
            this.aliases = manifest.aliases();
        }
    }
    
    public void execute(final String[] args) {
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public String[] getAliases() {
        return this.aliases;
    }
}
