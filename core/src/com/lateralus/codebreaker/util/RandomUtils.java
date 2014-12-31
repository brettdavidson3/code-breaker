package com.lateralus.codebreaker.util;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomUtils {

    private static final Random random = new Random(System.currentTimeMillis());

    public static int randomInt(int high) {
        return randomInt(0, high);
    }

    public static int randomInt(int low, int high) {
        return random.nextInt(high) + low;
    }

    public static <T> T getNextValue(List<T> availableValues) {
        return availableValues.remove(randomInt(availableValues.size()));
    }
}
