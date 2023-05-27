//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event.cache;

import java.lang.reflect.*;
import net.b0at.api.event.types.*;
import java.util.*;

public class HandlerEncapsulatorWithTiming<T> extends HandlerEncapsulator<T>
{
    private NavigableSet<HandlerEncapsulator<T>> postParentSet;
    
    public HandlerEncapsulatorWithTiming(final Object listener, final Method method, final int methodIndex, final EventPriority priority, final NavigableSet<HandlerEncapsulator<T>> preParentSet, final NavigableSet<HandlerEncapsulator<T>> postParentSet) {
        super(listener, method, methodIndex, priority, (NavigableSet)preParentSet);
        this.postParentSet = postParentSet;
    }
    
    public void invoke(final T event, final EventTiming timing) {
        this.methodAccess.invoke(this.listener, this.methodIndex, new Object[] { event, timing });
    }
    
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            this.parentSet.add(this);
            this.postParentSet.add(this);
        }
        else {
            this.parentSet.remove(this);
            this.postParentSet.remove(this);
        }
    }
    
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == HandlerEncapsulatorWithTiming.class) {
            final HandlerEncapsulatorWithTiming other = (HandlerEncapsulatorWithTiming)obj;
            return Objects.equals(this.method, other.method) && Objects.equals(this.listener, other.listener);
        }
        return false;
    }
    
    public String toString() {
        return String.format("%s@%s#%s@%s(%s, EventPriority priority)", this.listener.getClass().getName(), Integer.toHexString(this.listener.hashCode()), this.method.getName(), Integer.toHexString(this.method.hashCode()), this.method.getParameters()[0].getType().getName());
    }
}
