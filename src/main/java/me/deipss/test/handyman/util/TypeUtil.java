package me.deipss.test.handyman.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class TypeUtil {

    public static boolean isMap(Object o) {
        return Map.class.isAssignableFrom(o.getClass());
    }

    public static boolean isCollection(Object o) {
        return Collection.class.isAssignableFrom(o.getClass());
    }

    public static boolean isSet(Object o) {
        return Set.class.isAssignableFrom(o.getClass());
    }

    public static boolean isArray(Object o) {
        return o.getClass().isArray();
    }

    /*
    Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE
     */
    public static boolean isPrimitive(Object o) {
        return o.getClass().isPrimitive();
    }

    public static boolean isString(Object o) {
        return o.getClass().getName().startsWith("java.lang.String");
    }

    public static boolean isBoolean(Object o) {
        return o.getClass().getName().startsWith("java.lang.Boolean");
    }

    public static boolean isCharacter(Object o) {
        return o.getClass().getName().startsWith("java.lang.Character");
    }

    public static boolean isByte(Object o) {
        return o.getClass().getName().startsWith("java.lang.Byte");
    }

    public static boolean isShort(Object o) {
        return o.getClass().getName().startsWith("java.lang.Short");
    }

    public static boolean isInteger(Object o) {
        return o.getClass().getName().startsWith("java.lang.Integer");
    }

    public static boolean isLong(Object o) {
        return o.getClass().getName().startsWith("java.lang.Long");
    }


    public static boolean isFloat(Object o) {
        return o.getClass().getName().startsWith("java.lang.Float");
    }

    public static boolean isDouble(Object o) {
        return o.getClass().getName().startsWith("java.lang.Double");
    }

    public static boolean isVoid(Object o) {
        return o.getClass().getName().startsWith("java.lang.Void");
    }


}
