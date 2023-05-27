//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import me.hollow.realth.client.command.*;
import me.hollow.realth.client.events.*;
import net.minecraft.network.play.client.*;
import net.b0at.api.event.*;
import me.hollow.realth.client.modules.other.*;
import me.hollow.realth.api.util.*;
import me.hollow.realth.client.command.commands.*;
import me.hollow.realth.*;
import java.util.*;

public class CommandManager
{
    private final List<Command> commands;
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
    }
    
    @EventHandler
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            this.checkCommands(((CPacketChatMessage)event.getPacket()).getMessage(), (PacketEvent)event);
        }
    }
    
    private void checkCommands(final String message, final PacketEvent event) {
        if (message.startsWith((String)ClickGui.getInstance().prefix.getValue())) {
            final String[] args = message.split(" ");
            final String input = message.split(" ")[0].substring(1);
            for (final Command command : this.commands) {
                if (!input.equalsIgnoreCase(command.getLabel()) && !this.checkAliases(input, command)) {
                    continue;
                }
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
    
    private boolean checkAliases(final String input, final Command command) {
        for (final String str : command.getAliases()) {
            if (input.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
    
    public void init() {
        this.register((Command)new ToggleCommand(), (Command)new BindCommand(), (Command)new DrawnCommand(), (Command)new FriendCommand(), (Command)new SaveCommand(), (Command)new TutorialCommand());
        JordoHack.INSTANCE.getEventManager().registerListener((Object)this);
    }
    
    public void register(final Command... command) {
        Collections.addAll(this.commands, command);
    }
}
