package me.deipss.test.handyman.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hxl
 * @version 1.0
 * @since 2022/2/23 10:50
 */
@Slf4j
public class IfUtil {

    public static <T> T trueReturn(Condition c, ReturnAction<T> a, T r) {
        if (c.run()) {
            a.run();
        }
        return r;
    }

    public static <T> T trueReturnElse(Condition c, ReturnAction<T> a0, ReturnAction<T> a1) {
        if (c.run()) {
            a0.run();
        } else {
            a1.run();
        }
        return null;
    }

    public static void trueDo(Condition c, DoAction a) {
        if (c.run()) {
            a.run();
        }
    }

    public static void trueDoElse(Condition c, DoAction a0, DoAction a1) {
        if (c.run()) {
            a0.run();
        } else {
            a1.run();
        }
    }

    public static <T> T trueReturn(boolean b, ReturnAction<T> a, T r) {
        if (b) {
            a.run();
        }
        return r;
    }

    public static <T> T trueReturnElse(boolean b, ReturnAction<T> a0, ReturnAction<T> a1) {
        if (b) {
            a0.run();
        } else {
            a1.run();
        }
        return null;
    }

    public static void trueDo(boolean b, DoAction a) {
        if (b) {
            a.run();
        }
    }


    public static void notEmptyDo(String b, DoAction a) {
        if (StringUtils.isNotEmpty(b)) {
            a.run();
        }
    }

    public static void nullDo(Object b, DoAction a) {
        if (null == b) {
            a.run();
        }
    }

    public static void notNullDo(Object b, DoAction a) {
        if (null != b) {
            a.run();
        }
    }

    public static void trueDoElse(boolean b, DoAction a0, DoAction a1) {
        if (b) {
            a0.run();
        } else {
            a1.run();
        }
    }


    public interface Condition {
        Boolean run();
    }

    public interface DoAction {
        void run();
    }

    public interface ReturnAction<T> {
        T run();
    }


}