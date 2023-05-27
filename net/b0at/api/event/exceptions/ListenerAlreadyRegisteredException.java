//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event.exceptions;

public class ListenerAlreadyRegisteredException extends RuntimeException
{
    private static final String ERROR_MESSAGE = "Unable to register listener %s since it is already registered!";
    
    public ListenerAlreadyRegisteredException(final Object listener) {
        super(String.format("Unable to register listener %s since it is already registered!", listener.getClass().getName()));
    }
}
