//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event;

import net.b0at.api.event.profiler.*;
import net.b0at.api.event.types.*;
import java.util.function.*;
import net.b0at.api.event.sorting.*;
import java.util.*;
import java.lang.annotation.*;
import net.b0at.api.event.cache.*;
import java.lang.reflect.*;
import java.util.concurrent.*;
import net.b0at.api.event.exceptions.*;
import me.hollow.realth.client.events.*;
import me.hollow.realth.client.modules.*;
import me.hollow.realth.*;

public final class EventManager<T>
{
    private final Comparator<HandlerEncapsulator<T>> ENCAPSULATOR_SORTER;
    private IEventProfiler<T> eventProfiler;
    private Map<EventTiming, Map<Class<? extends T>, NavigableSet<HandlerEncapsulator<T>>>> eventEncapsulatorMap;
    private Set<Object> discoveredListeners;
    private Map<Object, Boolean> listenerPersistentStates;
    private Map<Object, Boolean> listenerNonPersistentStates;
    private Map<Object, Set<HandlerEncapsulator<T>>> persistentCache;
    private Map<Object, Set<HandlerEncapsulator<T>>> nonPersistentCache;
    private int registeredListenerCount;
    private Consumer<Exception> exceptionHook;
    private final Class<T> BASE_CLASS;
    
    public EventManager(final Class<T> baseClass) {
        this.ENCAPSULATOR_SORTER = new HandlerEncapsulatorSorter<T>();
        this.eventProfiler = new IEventProfiler<T>() {};
        this.eventEncapsulatorMap = new HashMap<EventTiming, Map<Class<? extends T>, NavigableSet<HandlerEncapsulator<T>>>>();
        this.discoveredListeners = new HashSet<Object>();
        this.listenerPersistentStates = new HashMap<Object, Boolean>();
        this.listenerNonPersistentStates = new HashMap<Object, Boolean>();
        this.persistentCache = new HashMap<Object, Set<HandlerEncapsulator<T>>>();
        this.nonPersistentCache = new HashMap<Object, Set<HandlerEncapsulator<T>>>();
        this.registeredListenerCount = 0;
        this.BASE_CLASS = baseClass;
        this.eventEncapsulatorMap.put(EventTiming.PRE, new HashMap<Class<? extends T>, NavigableSet<net.b0at.api.event.cache.HandlerEncapsulator<T>>>());
        this.eventEncapsulatorMap.put(EventTiming.POST, new HashMap<Class<? extends T>, NavigableSet<net.b0at.api.event.cache.HandlerEncapsulator<T>>>());
    }
    
    public void registerListener(final Object listener, final boolean onlyAddPersistent) throws ListenerAlreadyRegisteredException {
        final Map<Object, Boolean> listenerStates = onlyAddPersistent ? this.listenerPersistentStates : this.listenerNonPersistentStates;
        final Boolean state = listenerStates.get(listener);
        if (state == Boolean.TRUE) {
            throw new ListenerAlreadyRegisteredException(listener);
        }
        if (!this.discoveredListeners.contains(listener)) {
            this.discoverEventHandlers(listener);
        }
        listenerStates.put(listener, Boolean.TRUE);
        final Set<HandlerEncapsulator<T>> encapsulatorSet = onlyAddPersistent ? this.persistentCache.get(listener) : this.nonPersistentCache.get(listener);
        for (final HandlerEncapsulator<T> encapsulator : encapsulatorSet) {
            encapsulator.setEnabled(true);
        }
        this.registeredListenerCount += encapsulatorSet.size();
        this.eventProfiler.onRegisterListener(listener, onlyAddPersistent);
    }
    
    public void registerListener(final Object listener) throws ListenerAlreadyRegisteredException {
        this.registerListener(listener, false);
    }
    
    private void discoverEventHandlers(final Object listener) {
        this.eventProfiler.preListenerDiscovery(listener);
        final HashSet persistentSet = new HashSet();
        final HashSet nonPersistentSet = new HashSet();
        this.discoveredListeners.add(listener);
        this.persistentCache.put(listener, persistentSet);
        this.nonPersistentCache.put(listener, nonPersistentSet);
        int methodIndex = 0;
        for (Class<?> clazz = listener.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (final Method method : clazz.getDeclaredMethods()) {
                if ((method.getModifiers() & 0x2) == 0x0) {
                    if ((method.getParameterCount() == 1 || (method.getParameterCount() == 2 && EventTiming.class.isAssignableFrom(method.getParameterTypes()[1]))) && method.isAnnotationPresent((Class<? extends Annotation>)EventHandler.class) && this.BASE_CLASS.isAssignableFrom(method.getParameterTypes()[0])) {
                        final boolean includesTimingParam = method.getParameterCount() == 2;
                        final Class eventClass = method.getParameterTypes()[0];
                        final EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                        HandlerEncapsulator encapsulator;
                        if (includesTimingParam) {
                            final NavigableSet preSet = this.getOrCreateNavigableSet(this.eventEncapsulatorMap.get(EventTiming.PRE), eventClass);
                            final NavigableSet postSet = this.getOrCreateNavigableSet(this.eventEncapsulatorMap.get(EventTiming.POST), eventClass);
                            encapsulator = (HandlerEncapsulator)new HandlerEncapsulatorWithTiming(listener, method, methodIndex, eventHandler.priority(), preSet, postSet);
                        }
                        else {
                            final NavigableSet navigableSet = this.getOrCreateNavigableSet(this.eventEncapsulatorMap.get(eventHandler.timing()), eventClass);
                            encapsulator = new HandlerEncapsulator(listener, method, methodIndex, eventHandler.priority(), navigableSet);
                        }
                        final HashSet encapsulatorSet = eventHandler.persistent() ? persistentSet : nonPersistentSet;
                        encapsulatorSet.add(encapsulator);
                    }
                    ++methodIndex;
                }
            }
        }
        this.eventProfiler.postListenerDiscovery(listener);
    }
    
    private NavigableSet<HandlerEncapsulator<T>> getOrCreateNavigableSet(final Map<Class<? extends T>, NavigableSet<HandlerEncapsulator<T>>> encapsulatorMap, final Class<? extends T> eventClass) {
        NavigableSet<HandlerEncapsulator<T>> navigableSet = encapsulatorMap.get(eventClass);
        if (navigableSet == null) {
            navigableSet = new ConcurrentSkipListSet<HandlerEncapsulator<T>>(this.ENCAPSULATOR_SORTER);
            encapsulatorMap.put(eventClass, navigableSet);
        }
        return navigableSet;
    }
    
    public void deregisterListener(final Object listener, final boolean onlyRemovePersistent) throws ListenerNotAlreadyRegisteredException {
        final Map<Object, Boolean> listenerStates = onlyRemovePersistent ? this.listenerPersistentStates : this.listenerNonPersistentStates;
        final Boolean state = listenerStates.get(listener);
        if (state != Boolean.TRUE) {
            throw new ListenerNotAlreadyRegisteredException(listener);
        }
        listenerStates.put(listener, Boolean.FALSE);
        final Set<HandlerEncapsulator<T>> encapsulatorSet = onlyRemovePersistent ? this.persistentCache.get(listener) : this.nonPersistentCache.get(listener);
        for (final HandlerEncapsulator<T> encapsulator : encapsulatorSet) {
            encapsulator.setEnabled(false);
        }
        this.registeredListenerCount -= encapsulatorSet.size();
        this.eventProfiler.onDeregisterListener(listener, onlyRemovePersistent);
    }
    
    @EventHandler
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (Module.fullNullCheck()) {
            return;
        }
        if (event.getStage() == 0) {
            JordoHack.INSTANCE.getSpeedManager().updateValues();
            JordoHack.INSTANCE.getRotationManager().updateRotations();
            JordoHack.INSTANCE.getPositionManager().updatePosition();
        }
        if (event.getStage() == 1) {
            JordoHack.INSTANCE.getRotationManager().resetRotations();
            JordoHack.INSTANCE.getPositionManager().restorePosition();
        }
    }
    
    public void deregisterListener(final Object listener) throws ListenerNotAlreadyRegisteredException {
        this.deregisterListener(listener, false);
    }
    
    public void deregisterAll() {
        this.eventEncapsulatorMap.get(EventTiming.PRE).values().forEach(Set::clear);
        this.eventEncapsulatorMap.get(EventTiming.POST).values().forEach(Set::clear);
        final Boolean b;
        this.listenerPersistentStates.keySet().forEach(listener -> b = this.listenerPersistentStates.put(listener, Boolean.FALSE));
        final Boolean b2;
        this.listenerNonPersistentStates.keySet().forEach(listener -> b2 = this.listenerNonPersistentStates.put(listener, Boolean.FALSE));
        this.registeredListenerCount = 0;
        this.eventProfiler.onDeregisterAll();
    }
    
    public void cleanup() {
        this.eventEncapsulatorMap.get(EventTiming.PRE).clear();
        this.eventEncapsulatorMap.get(EventTiming.POST).clear();
        this.discoveredListeners.clear();
        this.listenerPersistentStates.clear();
        this.listenerNonPersistentStates.clear();
        this.persistentCache.clear();
        this.nonPersistentCache.clear();
        this.registeredListenerCount = 0;
        this.eventProfiler.onCleanup();
    }
    
    public int getRegisteredListenerCount() {
        return this.registeredListenerCount;
    }
    
    public <E extends T> E fireEvent(final E event) {
        return this.fireEvent(event, EventTiming.PRE);
    }
    
    public synchronized <E extends T> E fireEvent(final E event, final EventTiming timing) {
        final NavigableSet<HandlerEncapsulator<T>> encapsulatorSet = this.eventEncapsulatorMap.get(timing).get(event.getClass());
        if (encapsulatorSet == null || encapsulatorSet.isEmpty()) {
            this.eventProfiler.onSkippedEvent(event, timing);
        }
        else {
            this.eventProfiler.preFireEvent(event, timing, encapsulatorSet);
            for (final HandlerEncapsulator handlerEncapsulator : encapsulatorSet) {
                try {
                    handlerEncapsulator.invoke((Object)event, timing);
                }
                catch (Exception e) {
                    if (this.exceptionHook == null) {
                        throw e;
                    }
                    this.exceptionHook.accept(e);
                }
            }
            this.eventProfiler.postFireEvent(event, timing, encapsulatorSet);
        }
        return event;
    }
    
    public void setEventProfiler(final IEventProfiler<T> eventProfiler) {
        this.eventProfiler = eventProfiler;
    }
    
    public void setExceptionHook(final Consumer<Exception> exceptionHook) {
        this.exceptionHook = exceptionHook;
    }
}
