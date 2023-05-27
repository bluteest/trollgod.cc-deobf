//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event.profiler;

import net.b0at.api.event.types.*;
import java.util.*;
import net.b0at.api.event.cache.*;

public interface IEventProfiler<T>
{
    default void onRegisterListener(final Object listener, final boolean onlyAddPersistent) {
    }
    
    default void onDeregisterListener(final Object listener, final boolean onlyRemovePersistent) {
    }
    
    default void preListenerDiscovery(final Object listener) {
    }
    
    default void postListenerDiscovery(final Object listener) {
    }
    
    default void onDeregisterAll() {
    }
    
    default void onCleanup() {
    }
    
    default void preFireEvent(final T event, final EventTiming timing, final NavigableSet<HandlerEncapsulator<T>> handlers) {
    }
    
    default void postFireEvent(final T event, final EventTiming timing, final NavigableSet<HandlerEncapsulator<T>> handlers) {
    }
    
    default void onSkippedEvent(final T event, final EventTiming timing) {
    }
}
