//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.command.commands;

import me.hollow.realth.client.command.*;
import me.hollow.realth.*;

@CommandManifest(label = "Friend", aliases = { "friends", "friend" })
public class FriendCommand extends Command
{
    public void execute(final String[] args) {
        if (args.length < 2) {
            return;
        }
        try {
            final String name = args[2];
            final String upperCase = args[1].toUpperCase();
            switch (upperCase) {
                case "ADD": {
                    JordoHack.INSTANCE.getFriendManager().addFriend(name);
                    break;
                }
                case "DEL": {
                    JordoHack.INSTANCE.getFriendManager().removeFriend(name);
                    break;
                }
                case "DELETE": {
                    JordoHack.INSTANCE.getFriendManager().removeFriend(name);
                    break;
                }
                case "CLEAR": {
                    JordoHack.INSTANCE.getFriendManager().clearFriends();
                    break;
                }
                case "INSIDE": {
                    JordoHack.INSTANCE.getFriendManager().clearFriends();
                    break;
                }
            }
        }
        catch (Exception ex) {}
    }
}
