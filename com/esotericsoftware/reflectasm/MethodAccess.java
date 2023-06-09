//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.esotericsoftware.reflectasm;

import java.util.*;
import java.lang.reflect.*;
import org.objectweb.asm.*;

public abstract class MethodAccess
{
    private String[] methodNames;
    private Class[][] parameterTypes;
    private Class[] returnTypes;
    
    public abstract Object invoke(final Object p0, final int p1, final Object... p2);
    
    public Object invoke(final Object object, final String methodName, final Class[] paramTypes, final Object... args) {
        return this.invoke(object, this.getIndex(methodName, paramTypes), args);
    }
    
    public Object invoke(final Object object, final String methodName, final Object... args) {
        return this.invoke(object, this.getIndex(methodName, (args == null) ? 0 : args.length), args);
    }
    
    public int getIndex(final String methodName) {
        for (int i = 0, n = this.methodNames.length; i < n; ++i) {
            if (this.methodNames[i].equals(methodName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find non-private method: " + methodName);
    }
    
    public int getIndex(final String methodName, final Class... paramTypes) {
        for (int i = 0, n = this.methodNames.length; i < n; ++i) {
            if (this.methodNames[i].equals(methodName) && Arrays.equals(paramTypes, this.parameterTypes[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find non-private method: " + methodName + " " + Arrays.toString(paramTypes));
    }
    
    public int getIndex(final String methodName, final int paramsCount) {
        for (int i = 0, n = this.methodNames.length; i < n; ++i) {
            if (this.methodNames[i].equals(methodName) && this.parameterTypes[i].length == paramsCount) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find non-private method: " + methodName + " with " + paramsCount + " params.");
    }
    
    public String[] getMethodNames() {
        return this.methodNames;
    }
    
    public Class[][] getParameterTypes() {
        return this.parameterTypes;
    }
    
    public Class[] getReturnTypes() {
        return this.returnTypes;
    }
    
    public static MethodAccess get(final Class type) {
        final ArrayList<Method> methods = new ArrayList<Method>();
        final boolean isInterface = type.isInterface();
        if (!isInterface) {
            for (Class nextClass = type; nextClass != Object.class; nextClass = nextClass.getSuperclass()) {
                addDeclaredMethodsToList(nextClass, methods);
            }
        }
        else {
            recursiveAddInterfaceMethodsToList(type, methods);
        }
        final int n = methods.size();
        final String[] methodNames = new String[n];
        final Class[][] parameterTypes = new Class[n][];
        final Class[] returnTypes = new Class[n];
        for (int i = 0; i < n; ++i) {
            final Method method = methods.get(i);
            methodNames[i] = method.getName();
            parameterTypes[i] = method.getParameterTypes();
            returnTypes[i] = method.getReturnType();
        }
        final String className = type.getName();
        String accessClassName = className + "MethodAccess";
        if (accessClassName.startsWith("java.")) {
            accessClassName = "reflectasm." + accessClassName;
        }
        final AccessClassLoader loader = AccessClassLoader.get(type);
        Class accessClass;
        synchronized (loader) {
            try {
                accessClass = loader.loadClass(accessClassName);
            }
            catch (ClassNotFoundException ignored) {
                final String accessClassNameInternal = accessClassName.replace('.', '/');
                final String classNameInternal = className.replace('.', '/');
                final ClassWriter cw = new ClassWriter(1);
                cw.visit(196653, 33, accessClassNameInternal, (String)null, "com/esotericsoftware/reflectasm/MethodAccess", (String[])null);
                MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
                mv.visitCode();
                mv.visitVarInsn(25, 0);
                mv.visitMethodInsn(183, "com/esotericsoftware/reflectasm/MethodAccess", "<init>", "()V");
                mv.visitInsn(177);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
                mv = cw.visitMethod(129, "invoke", "(Ljava/lang/Object;I[Ljava/lang/Object;)Ljava/lang/Object;", (String)null, (String[])null);
                mv.visitCode();
                if (!methods.isEmpty()) {
                    mv.visitVarInsn(25, 1);
                    mv.visitTypeInsn(192, classNameInternal);
                    mv.visitVarInsn(58, 4);
                    mv.visitVarInsn(21, 2);
                    final Label[] labels = new Label[n];
                    for (int j = 0; j < n; ++j) {
                        labels[j] = new Label();
                    }
                    final Label defaultLabel = new Label();
                    mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
                    final StringBuilder buffer = new StringBuilder(128);
                    for (int k = 0; k < n; ++k) {
                        mv.visitLabel(labels[k]);
                        if (k == 0) {
                            mv.visitFrame(1, 1, new Object[] { classNameInternal }, 0, (Object[])null);
                        }
                        else {
                            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                        }
                        mv.visitVarInsn(25, 4);
                        buffer.setLength(0);
                        buffer.append('(');
                        final Class[] paramTypes = parameterTypes[k];
                        final Class returnType = returnTypes[k];
                        for (int paramIndex = 0; paramIndex < paramTypes.length; ++paramIndex) {
                            mv.visitVarInsn(25, 3);
                            mv.visitIntInsn(16, paramIndex);
                            mv.visitInsn(50);
                            final Type paramType = Type.getType(paramTypes[paramIndex]);
                            switch (paramType.getSort()) {
                                case 1: {
                                    mv.visitTypeInsn(192, "java/lang/Boolean");
                                    mv.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z");
                                    break;
                                }
                                case 3: {
                                    mv.visitTypeInsn(192, "java/lang/Byte");
                                    mv.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B");
                                    break;
                                }
                                case 2: {
                                    mv.visitTypeInsn(192, "java/lang/Character");
                                    mv.visitMethodInsn(182, "java/lang/Character", "charValue", "()C");
                                    break;
                                }
                                case 4: {
                                    mv.visitTypeInsn(192, "java/lang/Short");
                                    mv.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S");
                                    break;
                                }
                                case 5: {
                                    mv.visitTypeInsn(192, "java/lang/Integer");
                                    mv.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I");
                                    break;
                                }
                                case 6: {
                                    mv.visitTypeInsn(192, "java/lang/Float");
                                    mv.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F");
                                    break;
                                }
                                case 7: {
                                    mv.visitTypeInsn(192, "java/lang/Long");
                                    mv.visitMethodInsn(182, "java/lang/Long", "longValue", "()J");
                                    break;
                                }
                                case 8: {
                                    mv.visitTypeInsn(192, "java/lang/Double");
                                    mv.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D");
                                    break;
                                }
                                case 9: {
                                    mv.visitTypeInsn(192, paramType.getDescriptor());
                                    break;
                                }
                                case 10: {
                                    mv.visitTypeInsn(192, paramType.getInternalName());
                                    break;
                                }
                            }
                            buffer.append(paramType.getDescriptor());
                        }
                        buffer.append(')');
                        buffer.append(Type.getDescriptor(returnType));
                        int invoke;
                        if (isInterface) {
                            invoke = 185;
                        }
                        else if (Modifier.isStatic(methods.get(k).getModifiers())) {
                            invoke = 184;
                        }
                        else {
                            invoke = 182;
                        }
                        mv.visitMethodInsn(invoke, classNameInternal, methodNames[k], buffer.toString());
                        switch (Type.getType(returnType).getSort()) {
                            case 0: {
                                mv.visitInsn(1);
                                break;
                            }
                            case 1: {
                                mv.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                                break;
                            }
                            case 3: {
                                mv.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                                break;
                            }
                            case 2: {
                                mv.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
                                break;
                            }
                            case 4: {
                                mv.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                                break;
                            }
                            case 5: {
                                mv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                break;
                            }
                            case 6: {
                                mv.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                                break;
                            }
                            case 7: {
                                mv.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                                break;
                            }
                            case 8: {
                                mv.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                                break;
                            }
                        }
                        mv.visitInsn(176);
                    }
                    mv.visitLabel(defaultLabel);
                    mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                }
                mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                mv.visitInsn(89);
                mv.visitTypeInsn(187, "java/lang/StringBuilder");
                mv.visitInsn(89);
                mv.visitLdcInsn((Object)"Method not found: ");
                mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
                mv.visitVarInsn(21, 2);
                mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
                mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
                mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
                mv.visitInsn(191);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
                cw.visitEnd();
                final byte[] data = cw.toByteArray();
                accessClass = loader.defineClass(accessClassName, data);
            }
        }
        try {
            final MethodAccess access = accessClass.newInstance();
            access.methodNames = methodNames;
            access.parameterTypes = parameterTypes;
            access.returnTypes = returnTypes;
            return access;
        }
        catch (Throwable t) {
            throw new RuntimeException("Error constructing method access class: " + accessClassName, t);
        }
    }
    
    private static void addDeclaredMethodsToList(final Class type, final ArrayList<Method> methods) {
        final Method[] declaredMethods = type.getDeclaredMethods();
        for (int i = 0, n = declaredMethods.length; i < n; ++i) {
            final Method method = declaredMethods[i];
            final int modifiers = method.getModifiers();
            if (!Modifier.isPrivate(modifiers)) {
                methods.add(method);
            }
        }
    }
    
    private static void recursiveAddInterfaceMethodsToList(final Class interfaceType, final ArrayList<Method> methods) {
        addDeclaredMethodsToList(interfaceType, methods);
        for (final Class nextInterface : interfaceType.getInterfaces()) {
            recursiveAddInterfaceMethodsToList(nextInterface, methods);
        }
    }
}
