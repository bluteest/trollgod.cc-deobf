/*
 * Decompiled with CFR 0.150.
 */
package me.hollow.realth.client.command.commands;

import me.hollow.realth.JordoHack;
import me.hollow.realth.client.command.Command;
import me.hollow.realth.client.command.CommandManifest;

@CommandManifest(label="Friend", aliases={"friends", "friend"})
public class FriendCommand
extends Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            return;
        }
        try {
            String name = args[2];
            switch (args[1].toUpperCase()) {
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
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

