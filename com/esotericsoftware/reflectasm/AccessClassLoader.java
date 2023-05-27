//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.esotericsoftware.reflectasm;

import java.util.*;
import java.lang.ref.*;
import java.lang.reflect.*;
import java.security.*;

class AccessClassLoader extends ClassLoader
{
    private static final WeakHashMap<ClassLoader, WeakReference<AccessClassLoader>> accessClassLoaders;
    private static final ClassLoader selfContextParentClassLoader;
    private static volatile AccessClassLoader selfContextAccessClassLoader;
    private static volatile Method defineClassMethod;
    
    static AccessClassLoader get(final Class type) {
        final ClassLoader parent = getParentClassLoader(type);
        if (AccessClassLoader.selfContextParentClassLoader.equals(parent)) {
            if (AccessClassLoader.selfContextAccessClassLoader == null) {
                synchronized (AccessClassLoader.accessClassLoaders) {
                    if (AccessClassLoader.selfContextAccessClassLoader == null) {
                        AccessClassLoader.selfContextAccessClassLoader = new AccessClassLoader(AccessClassLoader.selfContextParentClassLoader);
                    }
                }
            }
            return AccessClassLoader.selfContextAccessClassLoader;
        }
        synchronized (AccessClassLoader.accessClassLoaders) {
            final WeakReference<AccessClassLoader> ref = AccessClassLoader.accessClassLoaders.get(parent);
            if (ref != null) {
                final AccessClassLoader accessClassLoader = ref.get();
                if (accessClassLoader != null) {
                    return accessClassLoader;
                }
                AccessClassLoader.accessClassLoaders.remove(parent);
            }
            final AccessClassLoader accessClassLoader = new AccessClassLoader(parent);
            AccessClassLoader.accessClassLoaders.put(parent, new WeakReference<AccessClassLoader>(accessClassLoader));
            return accessClassLoader;
        }
    }
    
    public static void remove(final ClassLoader parent) {
        if (AccessClassLoader.selfContextParentClassLoader.equals(parent)) {
            AccessClassLoader.selfContextAccessClassLoader = null;
        }
        else {
            synchronized (AccessClassLoader.accessClassLoaders) {
                AccessClassLoader.accessClassLoaders.remove(parent);
            }
        }
    }
    
    public static int activeAccessClassLoaders() {
        int sz = AccessClassLoader.accessClassLoaders.size();
        if (AccessClassLoader.selfContextAccessClassLoader != null) {
            ++sz;
        }
        return sz;
    }
    
    private AccessClassLoader(final ClassLoader parent) {
        super(parent);
    }
    
    @Override
    protected synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        if (name.equals(FieldAccess.class.getName())) {
            return FieldAccess.class;
        }
        if (name.equals(MethodAccess.class.getName())) {
            return MethodAccess.class;
        }
        if (name.equals(ConstructorAccess.class.getName())) {
            return ConstructorAccess.class;
        }
        if (name.equals(PublicConstructorAccess.class.getName())) {
            return PublicConstructorAccess.class;
        }
        return super.loadClass(name, resolve);
    }
    
    Class<?> defineClass(final String name, final byte[] bytes) throws ClassFormatError {
        try {
            return (Class<?>)getDefineClassMethod().invoke(this.getParent(), name, bytes, 0, bytes.length, this.getClass().getProtectionDomain());
        }
        catch (Exception ex) {
            return this.defineClass(name, bytes, 0, bytes.length, this.getClass().getProtectionDomain());
        }
    }
    
    static boolean areInSameRuntimeClassLoader(final Class type1, final Class type2) {
        if (type1.getPackage() != type2.getPackage()) {
            return false;
        }
        final ClassLoader loader1 = type1.getClassLoader();
        final ClassLoader loader2 = type2.getClassLoader();
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        if (loader1 == null) {
            return loader2 == null || loader2 == systemClassLoader;
        }
        if (loader2 == null) {
            return loader1 == systemClassLoader;
        }
        return loader1 == loader2;
    }
    
    private static ClassLoader getParentClassLoader(final Class type) {
        ClassLoader parent = type.getClassLoader();
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        return parent;
    }
    
    private static Method getDefineClassMethod() throws Exception {
        if (AccessClassLoader.defineClassMethod == null) {
            synchronized (AccessClassLoader.accessClassLoaders) {
                AccessClassLoader.defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                try {
                    AccessClassLoader.defineClassMethod.setAccessible(true);
                }
                catch (Exception ex) {}
            }
        }
        return AccessClassLoader.defineClassMethod;
    }
    
    static {
        accessClassLoaders = new WeakHashMap<ClassLoader, WeakReference<AccessClassLoader>>();
        selfContextParentClassLoader = getParentClassLoader(AccessClassLoader.class);
        AccessClassLoader.selfContextAccessClassLoader = new AccessClassLoader(AccessClassLoader.selfContextParentClassLoader);
    }
}
