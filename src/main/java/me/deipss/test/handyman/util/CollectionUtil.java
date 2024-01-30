package me.deipss.test.handyman.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtil {

    public  static <T>  List<T> diffKeepRight(Collection<T> left,Collection<T> right){
        List<T> intersection = intersection(left, right);
        return right.stream().filter(i->!intersection.contains(i)).collect(Collectors.toList());
    }


    public  static <T>  List<T> diffKeepLeft(Collection<T> left,Collection<T> right){
        List<T> intersection = intersection(left, right);
        return left.stream().filter(i->!intersection.contains(i)).collect(Collectors.toList());    }


    public  static <T>  List<T> intersection(Collection<T> left,Collection<T> right){
        return left.stream().filter(right::contains).collect(Collectors.toList());
    }



}
