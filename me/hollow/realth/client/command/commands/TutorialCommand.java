//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.command.commands;

import me.hollow.realth.client.command.*;
import net.minecraft.client.*;
import net.minecraft.client.tutorial.*;

@CommandManifest(label = "Tutorial", aliases = { "tut" })
public class TutorialCommand extends Command
{
    public void execute(final String[] args) {
        Minecraft.getMinecraft().gameSettings.tutorialStep = TutorialSteps.NONE;
    }
}
