//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.managers;

import com.google.gson.*;
import com.google.common.reflect.*;
import java.io.*;
import me.hollow.realth.api.util.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class FriendManager
{
    private List<Friend> friends;
    private File directory;
    
    public FriendManager() {
        this.friends = new ArrayList<Friend>();
    }
    
    public void init() {
        if (!this.directory.exists()) {
            try {
                this.directory.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.loadFriends();
    }
    
    public void unload() {
        this.saveFriends();
    }
    
    public void setDirectory(final File directory) {
        this.directory = directory;
    }
    
    public void saveFriends() {
        if (this.directory.exists()) {
            try (final Writer writer = new FileWriter(this.directory)) {
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson((Object)this.friends));
            }
            catch (IOException e) {
                this.directory.delete();
            }
        }
    }
    
    public void loadFriends() {
        if (!this.directory.exists()) {
            return;
        }
        try (final FileReader inFile = new FileReader(this.directory)) {
            this.friends = new ArrayList<Friend>((Collection<? extends Friend>)new GsonBuilder().setPrettyPrinting().create().fromJson((Reader)inFile, new TypeToken<ArrayList<Friend>>() {}.getType()));
        }
        catch (Exception ex) {}
    }
    
    public void addFriend(final String name) {
        MessageUtil.sendClientMessage("Added " + name + " as a friend ", false);
        this.friends.add(new Friend(name));
    }
    
    public final Friend getFriend(final String ign) {
        for (final Friend friend : this.friends) {
            if (friend.getName().equalsIgnoreCase(ign)) {
                return friend;
            }
        }
        return null;
    }
    
    public final boolean isFriend(final String ign) {
        return this.getFriend(ign) != null;
    }
    
    public boolean isFriend(final EntityPlayer ign) {
        return this.getFriend(ign.getName()) != null;
    }
    
    public void clearFriends() {
        this.friends.clear();
    }
    
    public void removeFriend(final String name) {
        final Friend f = this.getFriend(name);
        if (f != null) {
            this.friends.remove(f);
        }
    }
    
    public static final class Friend
    {
        final String name;
        
        public Friend(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
