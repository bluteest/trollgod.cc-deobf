//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lun\Documents\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.other.CPacketChatMessage
 */
package me.hollow.realth.client.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.hollow.realth.JordoHack;
import me.hollow.realth.api.util.MessageUtil;
import me.hollow.realth.client.command.Command;
import me.hollow.realth.client.command.commands.BindCommand;
import me.hollow.realth.client.command.commands.DrawnCommand;
import me.hollow.realth.client.command.commands.FriendCommand;
import me.hollow.realth.client.command.commands.SaveCommand;
import me.hollow.realth.client.command.commands.ToggleCommand;
import me.hollow.realth.client.command.commands.TutorialCommand;
import me.hollow.realth.client.events.PacketEvent;
import me.hollow.realth.client.modules.other.ClickGui;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.client.CPacketChatMessage;

public class CommandManager {
    private final List<Command> commands = new ArrayList<Command>();

    @EventHandler
    public void onSendPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            this.checkCommands(((CPacketChatMessage)event.getPacket()).getMessage(), event);
        }
    }

    private void checkCommands(String message, PacketEvent event) {
        if (message.startsWith(ClickGui.getInstance().prefix.getValue())) {
            String[] args = message.split(" ");
            String input = message.split(" ")[0].substring(1);
            for (Command command : this.commands) {
                if (!input.equalsIgnoreCase(command.getLabel()) && !this.checkAliases(input, command)) continue;
                event.cancel();
                command.execute(args);
            }
            if (!event.isCancelled()) {
                MessageUtil.sendClientMessage("Command " + message + " was not found!", true);
                event.cancel();
            }
            event.cancel();
        }
    }

    private boolean checkAliases(String input, Command command) {
        for (String str : command.getAliases()) {
            if (!input.equalsIgnoreCase(str)) continue;
            return true;
        }
        return false;
    }

    public void init() {
        this.register(new ToggleCommand(), new BindCommand(), new DrawnCommand(), new FriendCommand(), new SaveCommand(), new TutorialCommand());
        JordoHack.INSTANCE.getEventManager().registerListener(this);
    }

    public void register(Command ... command) {
        Collections.addAll(this.commands, command);
    }
}

