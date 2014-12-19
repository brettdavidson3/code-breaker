package com.lateralus.codebreaker.controller.helper;

import java.util.ArrayList;

public class CollectionUtils {

    public static <T> ArrayList<T> newArrayList(T[] arr) {
        // TODO: guava
        ArrayList<T> list = new ArrayList<>();
        for (T t : arr) {
            list.add(t);
        }
        return list;
    }
}
