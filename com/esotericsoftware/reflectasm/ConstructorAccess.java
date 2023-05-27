//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.esotericsoftware.reflectasm;

import java.lang.reflect.*;
import org.objectweb.asm.*;

public abstract class ConstructorAccess<T>
{
    boolean isNonStaticMemberClass;
    
    public boolean isNonStaticMemberClass() {
        return this.isNonStaticMemberClass;
    }
    
    public abstract T newInstance();
    
    public abstract T newInstance(final Object p0);
    
    public static <T> ConstructorAccess<T> get(final Class<T> type) {
        final Class enclosingType = type.getEnclosingClass();
        final boolean isNonStaticMemberClass = enclosingType != null && type.isMemberClass() && !Modifier.isStatic(type.getModifiers());
        final String className = type.getName();
        String accessClassName = className + "ConstructorAccess";
        if (accessClassName.startsWith("java.")) {
            accessClassName = "reflectasm." + accessClassName;
        }
        final AccessClassLoader loader = AccessClassLoader.get((Class)type);
        Class accessClass;
        synchronized (loader) {
            try {
                accessClass = loader.loadClass(accessClassName);
            }
            catch (ClassNotFoundException ignored) {
                final String accessClassNameInternal = accessClassName.replace('.', '/');
                final String classNameInternal = className.replace('.', '/');
                Constructor<T> constructor = null;
                int modifiers = 0;
                String enclosingClassNameInternal;
                if (!isNonStaticMemberClass) {
                    enclosingClassNameInternal = null;
                    try {
                        constructor = type.getDeclaredConstructor((Class<?>[])null);
                        modifiers = constructor.getModifiers();
                    }
                    catch (Exception ex) {
                        throw new RuntimeException("Class cannot be created (missing no-arg constructor): " + type.getName(), ex);
                    }
                    if (Modifier.isPrivate(modifiers)) {
                        throw new RuntimeException("Class cannot be created (the no-arg constructor is private): " + type.getName());
                    }
                }
                else {
                    enclosingClassNameInternal = enclosingType.getName().replace('.', '/');
                    try {
                        constructor = type.getDeclaredConstructor(enclosingType);
                        modifiers = constructor.getModifiers();
                    }
                    catch (Exception ex) {
                        throw new RuntimeException("Non-static member class cannot be created (missing enclosing class constructor): " + type.getName(), ex);
                    }
                    if (Modifier.isPrivate(modifiers)) {
                        throw new RuntimeException("Non-static member class cannot be created (the enclosing class constructor is private): " + type.getName());
                    }
                }
                final String superclassNameInternal = Modifier.isPublic(modifiers) ? "com/esotericsoftware/reflectasm/PublicConstructorAccess" : "com/esotericsoftware/reflectasm/ConstructorAccess";
                final ClassWriter cw = new ClassWriter(0);
                cw.visit(196653, 33, accessClassNameInternal, (String)null, superclassNameInternal, (String[])null);
                insertConstructor(cw, superclassNameInternal);
                insertNewInstance(cw, classNameInternal);
                insertNewInstanceInner(cw, classNameInternal, enclosingClassNameInternal);
                cw.visitEnd();
                accessClass = loader.defineClass(accessClassName, cw.toByteArray());
            }
        }
        ConstructorAccess<T> access;
        try {
            access = accessClass.newInstance();
        }
        catch (Throwable t) {
            throw new RuntimeException("Exception constructing constructor access class: " + accessClassName, t);
        }
        if (!(access instanceof PublicConstructorAccess) && !AccessClassLoader.areInSameRuntimeClassLoader((Class)type, accessClass)) {
            throw new RuntimeException((isNonStaticMemberClass ? "Non-static member class cannot be created (the enclosing class constructor is protected or package-protected, and its ConstructorAccess could not be defined in the same class loader): " : "Class cannot be created (the no-arg constructor is protected or package-protected, and its ConstructorAccess could not be defined in the same class loader): ") + type.getName());
        }
        access.isNonStaticMemberClass = isNonStaticMemberClass;
        return access;
    }
    
    private static void insertConstructor(final ClassWriter cw, final String superclassNameInternal) {
        final MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, superclassNameInternal, "<init>", "()V");
        mv.visitInsn(177);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
    
    static void insertNewInstance(final ClassWriter cw, final String classNameInternal) {
        final MethodVisitor mv = cw.visitMethod(1, "newInstance", "()Ljava/lang/Object;", (String)null, (String[])null);
        mv.visitCode();
        mv.visitTypeInsn(187, classNameInternal);
        mv.visitInsn(89);
        mv.visitMethodInsn(183, classNameInternal, "<init>", "()V");
        mv.visitInsn(176);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
    
    static void insertNewInstanceInner(final ClassWriter cw, final String classNameInternal, final String enclosingClassNameInternal) {
        final MethodVisitor mv = cw.visitMethod(1, "newInstance", "(Ljava/lang/Object;)Ljava/lang/Object;", (String)null, (String[])null);
        mv.visitCode();
        if (enclosingClassNameInternal != null) {
            mv.visitTypeInsn(187, classNameInternal);
            mv.visitInsn(89);
            mv.visitVarInsn(25, 1);
            mv.visitTypeInsn(192, enclosingClassNameInternal);
            mv.visitInsn(89);
            mv.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            mv.visitInsn(87);
            mv.visitMethodInsn(183, classNameInternal, "<init>", "(L" + enclosingClassNameInternal + ";)V");
            mv.visitInsn(176);
            mv.visitMaxs(4, 2);
        }
        else {
            mv.visitTypeInsn(187, "java/lang/UnsupportedOperationException");
            mv.visitInsn(89);
            mv.visitLdcInsn((Object)"Not an inner class.");
            mv.visitMethodInsn(183, "java/lang/UnsupportedOperationException", "<init>", "(Ljava/lang/String;)V");
            mv.visitInsn(191);
            mv.visitMaxs(3, 2);
        }
        mv.visitEnd();
    }
}
