package me.deipss.test.handyman.util;


import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 异常检查类
 */
public class Assert {

    public static void nullable(Object o, Exception e) throws Exception {
        if (Objects.isNull(o)) {
            throw e;
        }
    }

    public static void empty(String s, Exception e) throws Exception {
        nullable(s, e);
        if (StringUtils.isEmpty(s)) {
            throw e;
        }
    }

    public static void emptyCollection(Collection<?> list, Exception e) throws Exception {
        nullable(list, e);
        if (list.size() < 1) {
            throw e;
        }
    }

    public static void throwE(String s) throws Exception {
        throw new Exception(s);
    }

    public static void ifTrue(boolean b, String s) throws Exception {
        if (b) {
            throw new Exception(s);
        }
    }

    public static void empty(Collection<?> list, Exception e) throws Exception {
        nullable(list, e);
        if (list.isEmpty()) {
            throw e;
        }
    }
}
