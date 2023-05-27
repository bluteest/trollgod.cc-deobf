//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event.exceptions;

public class ListenerNotAlreadyRegisteredException extends RuntimeException
{
    private static final String ERROR_MESSAGE = "Unable to deregister listener %s since it is not already registered!";
    
    public ListenerNotAlreadyRegisteredException(final Object listener) {
        super(String.format("Unable to deregister listener %s since it is not already registered!", listener.getClass().getName()));
    }
}
