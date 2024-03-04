package me.deipss.test.handyman.util;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数校验工具类
 * @param <T>
 */
public class ValidUtil<T> {
    public static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     *
     * @param t 参数
     * @param groups 参数的校验分组
     * @return 检验不通过，返回提示语句，检验通过返回空
     * @param <T> 泛型
     */
    public static <T> String check(T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = validator.validate(t, groups);
        if (validate.size() > 0) {
            List<String> collect = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            return StringUtils.join(collect, ',');
        }
        return null;
    }

    /**
     *
     * @param t 参数
     * @return 检验不通过，返回提示语句，检验通过返回空
     * @param <T> 泛型
     */
    public static <T> String check(T t) {
        Set<ConstraintViolation<T>> validate = validator.validate(t);
        if (validate.size() > 0) {
            List<String> collect = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            return StringUtils.join(collect, ',');

        }
        return null;
    }

    /**
     *
     * @param t 参数
     * @param <T> 泛型
     * 检验不通过抛出异常
     */

    public static <T> void checkThenThrow(T t) throws Exception {
        Set<ConstraintViolation<T>> validate = validator.validate(t);
        if (validate.size() > 0) {
            List<String> collect = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            throw new IllegalArgumentException(StringUtils.join(collect, ','));
        }
    }
    /**
     *
     * @param t 参数
     * @param groups 参数的校验分组
     * @param <T> 泛型
     * 检验不通过抛出异常
     */
    public static <T> void checkThenThrow(T t, Class<?>... groups) throws Exception {
        Set<ConstraintViolation<T>> validate = validator.validate(t, groups);
        if (validate.size() > 0) {
            List<String> collect = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            throw new IllegalArgumentException(StringUtils.join(collect, ','));
        }
    }
}
