//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.b0at.api.event.sorting;

import net.b0at.api.event.cache.*;
import java.util.*;

public class HandlerEncapsulatorSorter<T> implements Comparator<HandlerEncapsulator<T>>
{
    @Override
    public int compare(final HandlerEncapsulator<T> a, final HandlerEncapsulator<T> b) {
        if (Objects.equals(a, b)) {
            return 0;
        }
        if (a.getPriority().ordinal() == b.getPriority().ordinal()) {
            return Integer.compare(a.hashCode(), b.hashCode());
        }
        if (a.getPriority().ordinal() > b.getPriority().ordinal()) {
            return 1;
        }
        return -1;
    }
}
