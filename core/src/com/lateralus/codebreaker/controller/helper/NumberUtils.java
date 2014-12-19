package com.lateralus.codebreaker.controller.helper;

import java.util.ArrayList;
import java.util.List;

public class NumberUtils {

    public static List<Integer> numberRange(int size) {
        // TODO - use guava
        List<Integer> range = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            range.add(i);
        }
        return range;
    }
}
