package me.deipss.test.handyman.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamUtil {

    public static <T, K> Map<K, T> toMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper) {
        return collection.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (a, b) -> a));
    }

    public static <T, K> Map<K, List<T>> group(Collection<T> collection, Function<? super T, ? extends K> keyMapper) {
        return collection.stream().collect(Collectors.groupingBy(keyMapper));
    }

    public static <T> Collection<T> filterNull(Collection<T> collection) {
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T, U> List<T> sort(Collection<T> collection, Comparator<? super T> comparator) {
        return collection.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static <T, U> List<T> sortReversed(Collection<T> collection, Comparator<? super T> comparator) {
        return collection.stream().sorted(comparator.reversed()).collect(Collectors.toList());
    }
}
