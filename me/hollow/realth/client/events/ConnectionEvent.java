//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.client.events;

import me.hollow.realth.api.event.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class ConnectionEvent extends EventStage
{
    private final UUID uuid;
    private final EntityPlayer entity;
    private final String name;
    
    public ConnectionEvent(final int stage, final UUID uuid, final String name) {
        super(stage);
        this.uuid = uuid;
        this.name = name;
        this.entity = null;
    }
    
    public ConnectionEvent(final int stage, final EntityPlayer entity, final UUID uuid, final String name) {
        super(stage);
        this.entity = entity;
        this.uuid = uuid;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public EntityPlayer getEntity() {
        return this.entity;
    }
}
