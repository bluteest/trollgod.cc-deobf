//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.esotericsoftware.reflectasm;

import java.util.*;
import java.lang.reflect.*;
import org.objectweb.asm.*;

public abstract class FieldAccess
{
    private String[] fieldNames;
    private Class[] fieldTypes;
    
    public int getIndex(final String fieldName) {
        for (int i = 0, n = this.fieldNames.length; i < n; ++i) {
            if (this.fieldNames[i].equals(fieldName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find non-private field: " + fieldName);
    }
    
    public void set(final Object instance, final String fieldName, final Object value) {
        this.set(instance, this.getIndex(fieldName), value);
    }
    
    public Object get(final Object instance, final String fieldName) {
        return this.get(instance, this.getIndex(fieldName));
    }
    
    public String[] getFieldNames() {
        return this.fieldNames;
    }
    
    public Class[] getFieldTypes() {
        return this.fieldTypes;
    }
    
    public int getFieldCount() {
        return this.fieldTypes.length;
    }
    
    public abstract void set(final Object p0, final int p1, final Object p2);
    
    public abstract void setBoolean(final Object p0, final int p1, final boolean p2);
    
    public abstract void setByte(final Object p0, final int p1, final byte p2);
    
    public abstract void setShort(final Object p0, final int p1, final short p2);
    
    public abstract void setInt(final Object p0, final int p1, final int p2);
    
    public abstract void setLong(final Object p0, final int p1, final long p2);
    
    public abstract void setDouble(final Object p0, final int p1, final double p2);
    
    public abstract void setFloat(final Object p0, final int p1, final float p2);
    
    public abstract void setChar(final Object p0, final int p1, final char p2);
    
    public abstract Object get(final Object p0, final int p1);
    
    public abstract String getString(final Object p0, final int p1);
    
    public abstract char getChar(final Object p0, final int p1);
    
    public abstract boolean getBoolean(final Object p0, final int p1);
    
    public abstract byte getByte(final Object p0, final int p1);
    
    public abstract short getShort(final Object p0, final int p1);
    
    public abstract int getInt(final Object p0, final int p1);
    
    public abstract long getLong(final Object p0, final int p1);
    
    public abstract double getDouble(final Object p0, final int p1);
    
    public abstract float getFloat(final Object p0, final int p1);
    
    public static FieldAccess get(final Class type) {
        final ArrayList<Field> fields = new ArrayList<Field>();
        for (Class nextClass = type; nextClass != Object.class; nextClass = nextClass.getSuperclass()) {
            final Field[] declaredFields = nextClass.getDeclaredFields();
            for (int i = 0, n = declaredFields.length; i < n; ++i) {
                final Field field = declaredFields[i];
                final int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers)) {
                    if (!Modifier.isPrivate(modifiers)) {
                        fields.add(field);
                    }
                }
            }
        }
        final String[] fieldNames = new String[fields.size()];
        final Class[] fieldTypes = new Class[fields.size()];
        for (int j = 0, n2 = fieldNames.length; j < n2; ++j) {
            fieldNames[j] = fields.get(j).getName();
            fieldTypes[j] = fields.get(j).getType();
        }
        final String className = type.getName();
        String accessClassName = className + "FieldAccess";
        if (accessClassName.startsWith("java.")) {
            accessClassName = "reflectasm." + accessClassName;
        }
        Class accessClass = null;
        final AccessClassLoader loader = AccessClassLoader.get(type);
        synchronized (loader) {
            try {
                accessClass = loader.loadClass(accessClassName);
            }
            catch (ClassNotFoundException ignored) {
                final String accessClassNameInternal = accessClassName.replace('.', '/');
                final String classNameInternal = className.replace('.', '/');
                final ClassWriter cw = new ClassWriter(0);
                cw.visit(196653, 33, accessClassNameInternal, (String)null, "com/esotericsoftware/reflectasm/FieldAccess", (String[])null);
                insertConstructor(cw);
                insertGetObject(cw, classNameInternal, fields);
                insertSetObject(cw, classNameInternal, fields);
                insertGetPrimitive(cw, classNameInternal, fields, Type.BOOLEAN_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.BOOLEAN_TYPE);
                insertGetPrimitive(cw, classNameInternal, fields, Type.BYTE_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.BYTE_TYPE);
                insertGetPrimitive(cw, classNameInternal, fields, Type.SHORT_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.SHORT_TYPE);
                insertGetPrimitive(cw, classNameInternal, fields, Type.INT_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.INT_TYPE);
                insertGetPrimitive(cw, classNameInternal, fields, Type.LONG_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.LONG_TYPE);
                insertGetPrimitive(cw, classNameInternal, fields, Type.DOUBLE_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.DOUBLE_TYPE);
                insertGetPrimitive(cw, classNameInternal, fields, Type.FLOAT_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.FLOAT_TYPE);
                insertGetPrimitive(cw, classNameInternal, fields, Type.CHAR_TYPE);
                insertSetPrimitive(cw, classNameInternal, fields, Type.CHAR_TYPE);
                insertGetString(cw, classNameInternal, fields);
                cw.visitEnd();
                accessClass = loader.defineClass(accessClassName, cw.toByteArray());
            }
        }
        try {
            final FieldAccess access = accessClass.newInstance();
            access.fieldNames = fieldNames;
            access.fieldTypes = fieldTypes;
            return access;
        }
        catch (Throwable t) {
            throw new RuntimeException("Error constructing field access class: " + accessClassName, t);
        }
    }
    
    private static void insertConstructor(final ClassWriter cw) {
        final MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, "com/esotericsoftware/reflectasm/FieldAccess", "<init>", "()V");
        mv.visitInsn(177);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
    
    private static void insertSetObject(final ClassWriter cw, final String classNameInternal, final ArrayList<Field> fields) {
        int maxStack = 6;
        MethodVisitor mv = cw.visitMethod(1, "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(21, 2);
        if (!fields.isEmpty()) {
            --maxStack;
            final Label[] labels = new Label[fields.size()];
            for (int i = 0, n = labels.length; i < n; ++i) {
                labels[i] = new Label();
            }
            final Label defaultLabel = new Label();
            mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
            for (int j = 0, n2 = labels.length; j < n2; ++j) {
                final Field field = fields.get(j);
                final Type fieldType = Type.getType((Class)field.getType());
                mv.visitLabel(labels[j]);
                mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                mv.visitVarInsn(25, 1);
                mv.visitTypeInsn(192, classNameInternal);
                mv.visitVarInsn(25, 3);
                switch (fieldType.getSort()) {
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
                        mv.visitTypeInsn(192, fieldType.getDescriptor());
                        break;
                    }
                    case 10: {
                        mv.visitTypeInsn(192, fieldType.getInternalName());
                        break;
                    }
                }
                mv.visitFieldInsn(181, classNameInternal, field.getName(), fieldType.getDescriptor());
                mv.visitInsn(177);
            }
            mv.visitLabel(defaultLabel);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        mv = insertThrowExceptionForFieldNotFound(mv);
        mv.visitMaxs(maxStack, 4);
        mv.visitEnd();
    }
    
    private static void insertGetObject(final ClassWriter cw, final String classNameInternal, final ArrayList<Field> fields) {
        int maxStack = 6;
        final MethodVisitor mv = cw.visitMethod(1, "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(21, 2);
        if (!fields.isEmpty()) {
            --maxStack;
            final Label[] labels = new Label[fields.size()];
            for (int i = 0, n = labels.length; i < n; ++i) {
                labels[i] = new Label();
            }
            final Label defaultLabel = new Label();
            mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
            for (int j = 0, n2 = labels.length; j < n2; ++j) {
                final Field field = fields.get(j);
                mv.visitLabel(labels[j]);
                mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                mv.visitVarInsn(25, 1);
                mv.visitTypeInsn(192, classNameInternal);
                mv.visitFieldInsn(180, classNameInternal, field.getName(), Type.getDescriptor((Class)field.getType()));
                final Type fieldType = Type.getType((Class)field.getType());
                switch (fieldType.getSort()) {
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
        insertThrowExceptionForFieldNotFound(mv);
        mv.visitMaxs(maxStack, 3);
        mv.visitEnd();
    }
    
    private static void insertGetString(final ClassWriter cw, final String classNameInternal, final ArrayList<Field> fields) {
        int maxStack = 6;
        final MethodVisitor mv = cw.visitMethod(1, "getString", "(Ljava/lang/Object;I)Ljava/lang/String;", (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(21, 2);
        if (!fields.isEmpty()) {
            --maxStack;
            final Label[] labels = new Label[fields.size()];
            final Label labelForInvalidTypes = new Label();
            boolean hasAnyBadTypeLabel = false;
            for (int i = 0, n = labels.length; i < n; ++i) {
                if (fields.get(i).getType().equals(String.class)) {
                    labels[i] = new Label();
                }
                else {
                    labels[i] = labelForInvalidTypes;
                    hasAnyBadTypeLabel = true;
                }
            }
            final Label defaultLabel = new Label();
            mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
            for (int j = 0, n2 = labels.length; j < n2; ++j) {
                if (!labels[j].equals(labelForInvalidTypes)) {
                    mv.visitLabel(labels[j]);
                    mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                    mv.visitVarInsn(25, 1);
                    mv.visitTypeInsn(192, classNameInternal);
                    mv.visitFieldInsn(180, classNameInternal, fields.get(j).getName(), "Ljava/lang/String;");
                    mv.visitInsn(176);
                }
            }
            if (hasAnyBadTypeLabel) {
                mv.visitLabel(labelForInvalidTypes);
                mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                insertThrowExceptionForFieldType(mv, "String");
            }
            mv.visitLabel(defaultLabel);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        insertThrowExceptionForFieldNotFound(mv);
        mv.visitMaxs(maxStack, 3);
        mv.visitEnd();
    }
    
    private static void insertSetPrimitive(final ClassWriter cw, final String classNameInternal, final ArrayList<Field> fields, final Type primitiveType) {
        int maxStack = 6;
        int maxLocals = 4;
        final String typeNameInternal = primitiveType.getDescriptor();
        String setterMethodName = null;
        int loadValueInstruction = 0;
        switch (primitiveType.getSort()) {
            case 1: {
                setterMethodName = "setBoolean";
                loadValueInstruction = 21;
                break;
            }
            case 3: {
                setterMethodName = "setByte";
                loadValueInstruction = 21;
                break;
            }
            case 2: {
                setterMethodName = "setChar";
                loadValueInstruction = 21;
                break;
            }
            case 4: {
                setterMethodName = "setShort";
                loadValueInstruction = 21;
                break;
            }
            case 5: {
                setterMethodName = "setInt";
                loadValueInstruction = 21;
                break;
            }
            case 6: {
                setterMethodName = "setFloat";
                loadValueInstruction = 23;
                break;
            }
            case 7: {
                setterMethodName = "setLong";
                loadValueInstruction = 22;
                ++maxLocals;
                break;
            }
            case 8: {
                setterMethodName = "setDouble";
                loadValueInstruction = 24;
                ++maxLocals;
                break;
            }
            default: {
                setterMethodName = "set";
                loadValueInstruction = 25;
                break;
            }
        }
        MethodVisitor mv = cw.visitMethod(1, setterMethodName, "(Ljava/lang/Object;I" + typeNameInternal + ")V", (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(21, 2);
        if (!fields.isEmpty()) {
            --maxStack;
            final Label[] labels = new Label[fields.size()];
            final Label labelForInvalidTypes = new Label();
            boolean hasAnyBadTypeLabel = false;
            for (int i = 0, n = labels.length; i < n; ++i) {
                if (Type.getType((Class)fields.get(i).getType()).equals((Object)primitiveType)) {
                    labels[i] = new Label();
                }
                else {
                    labels[i] = labelForInvalidTypes;
                    hasAnyBadTypeLabel = true;
                }
            }
            final Label defaultLabel = new Label();
            mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
            for (int j = 0, n2 = labels.length; j < n2; ++j) {
                if (!labels[j].equals(labelForInvalidTypes)) {
                    mv.visitLabel(labels[j]);
                    mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                    mv.visitVarInsn(25, 1);
                    mv.visitTypeInsn(192, classNameInternal);
                    mv.visitVarInsn(loadValueInstruction, 3);
                    mv.visitFieldInsn(181, classNameInternal, fields.get(j).getName(), typeNameInternal);
                    mv.visitInsn(177);
                }
            }
            if (hasAnyBadTypeLabel) {
                mv.visitLabel(labelForInvalidTypes);
                mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                insertThrowExceptionForFieldType(mv, primitiveType.getClassName());
            }
            mv.visitLabel(defaultLabel);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        mv = insertThrowExceptionForFieldNotFound(mv);
        mv.visitMaxs(maxStack, maxLocals);
        mv.visitEnd();
    }
    
    private static void insertGetPrimitive(final ClassWriter cw, final String classNameInternal, final ArrayList<Field> fields, final Type primitiveType) {
        int maxStack = 6;
        final String typeNameInternal = primitiveType.getDescriptor();
        String getterMethodName = null;
        int returnValueInstruction = 0;
        switch (primitiveType.getSort()) {
            case 1: {
                getterMethodName = "getBoolean";
                returnValueInstruction = 172;
                break;
            }
            case 3: {
                getterMethodName = "getByte";
                returnValueInstruction = 172;
                break;
            }
            case 2: {
                getterMethodName = "getChar";
                returnValueInstruction = 172;
                break;
            }
            case 4: {
                getterMethodName = "getShort";
                returnValueInstruction = 172;
                break;
            }
            case 5: {
                getterMethodName = "getInt";
                returnValueInstruction = 172;
                break;
            }
            case 6: {
                getterMethodName = "getFloat";
                returnValueInstruction = 174;
                break;
            }
            case 7: {
                getterMethodName = "getLong";
                returnValueInstruction = 173;
                break;
            }
            case 8: {
                getterMethodName = "getDouble";
                returnValueInstruction = 175;
                break;
            }
            default: {
                getterMethodName = "get";
                returnValueInstruction = 176;
                break;
            }
        }
        MethodVisitor mv = cw.visitMethod(1, getterMethodName, "(Ljava/lang/Object;I)" + typeNameInternal, (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(21, 2);
        if (!fields.isEmpty()) {
            --maxStack;
            final Label[] labels = new Label[fields.size()];
            final Label labelForInvalidTypes = new Label();
            boolean hasAnyBadTypeLabel = false;
            for (int i = 0, n = labels.length; i < n; ++i) {
                if (Type.getType((Class)fields.get(i).getType()).equals((Object)primitiveType)) {
                    labels[i] = new Label();
                }
                else {
                    labels[i] = labelForInvalidTypes;
                    hasAnyBadTypeLabel = true;
                }
            }
            final Label defaultLabel = new Label();
            mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
            for (int j = 0, n2 = labels.length; j < n2; ++j) {
                final Field field = fields.get(j);
                if (!labels[j].equals(labelForInvalidTypes)) {
                    mv.visitLabel(labels[j]);
                    mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                    mv.visitVarInsn(25, 1);
                    mv.visitTypeInsn(192, classNameInternal);
                    mv.visitFieldInsn(180, classNameInternal, field.getName(), typeNameInternal);
                    mv.visitInsn(returnValueInstruction);
                }
            }
            if (hasAnyBadTypeLabel) {
                mv.visitLabel(labelForInvalidTypes);
                mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                insertThrowExceptionForFieldType(mv, primitiveType.getClassName());
            }
            mv.visitLabel(defaultLabel);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }
        mv = insertThrowExceptionForFieldNotFound(mv);
        mv.visitMaxs(maxStack, 3);
        mv.visitEnd();
    }
    
    private static MethodVisitor insertThrowExceptionForFieldNotFound(final MethodVisitor mv) {
        mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
        mv.visitInsn(89);
        mv.visitTypeInsn(187, "java/lang/StringBuilder");
        mv.visitInsn(89);
        mv.visitLdcInsn((Object)"Field not found: ");
        mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
        mv.visitVarInsn(21, 2);
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
        mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
        mv.visitInsn(191);
        return mv;
    }
    
    private static MethodVisitor insertThrowExceptionForFieldType(final MethodVisitor mv, final String fieldType) {
        mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
        mv.visitInsn(89);
        mv.visitTypeInsn(187, "java/lang/StringBuilder");
        mv.visitInsn(89);
        mv.visitLdcInsn((Object)("Field not declared as " + fieldType + ": "));
        mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
        mv.visitVarInsn(21, 2);
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
        mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
        mv.visitInsn(191);
        return mv;
    }
}
