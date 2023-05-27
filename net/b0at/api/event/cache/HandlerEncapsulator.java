//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event.cache;

import java.lang.reflect.*;
import com.esotericsoftware.reflectasm.*;
import net.b0at.api.event.types.*;
import java.util.*;

public class HandlerEncapsulator<T>
{
    protected Object listener;
    protected Method method;
    private EventPriority priority;
    protected NavigableSet<HandlerEncapsulator<T>> parentSet;
    protected MethodAccess methodAccess;
    protected int methodIndex;
    
    public HandlerEncapsulator(final Object listener, final Method method, final int methodIndex, final EventPriority priority, final NavigableSet<HandlerEncapsulator<T>> parentSet) {
        this.listener = listener;
        this.method = method;
        this.priority = priority;
        this.parentSet = parentSet;
        this.methodIndex = methodIndex;
        method.setAccessible(true);
        this.methodAccess = MethodAccess.get((Class)this.listener.getClass());
    }
    
    public void invoke(final T event, final EventTiming timing) {
        this.methodAccess.invoke(this.listener, this.methodIndex, new Object[] { event });
    }
    
    public final EventPriority getPriority() {
        return this.priority;
    }
    
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            this.parentSet.add(this);
        }
        else {
            this.parentSet.remove(this);
        }
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == HandlerEncapsulator.class) {
            final HandlerEncapsulator other = (HandlerEncapsulator)obj;
            return Objects.equals(this.method, other.method) && Objects.equals(this.listener, other.listener);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%s@%s#%s@%s(%s)", this.listener.getClass().getName(), Integer.toHexString(this.listener.hashCode()), this.method.getName(), Integer.toHexString(this.method.hashCode()), this.method.getParameters()[0].getType().getName());
    }
}
