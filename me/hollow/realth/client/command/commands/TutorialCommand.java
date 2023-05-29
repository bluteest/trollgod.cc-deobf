//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.other.Minecraft
 *  net.minecraft.other.tutorial.TutorialSteps
 */
package me.hollow.realth.client.command.commands;

import me.hollow.realth.client.command.Command;
import me.hollow.realth.client.command.CommandManifest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.tutorial.TutorialSteps;

@CommandManifest(label="Tutorial", aliases={"tut"})
public class TutorialCommand
extends Command {
    @Override
    public void execute(String[] args) {
        Minecraft.getMinecraft().gameSettings.tutorialStep = TutorialSteps.NONE;
    }
}

