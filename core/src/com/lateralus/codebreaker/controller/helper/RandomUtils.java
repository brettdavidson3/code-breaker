package com.lateralus.codebreaker.controller.helper;

import java.util.List;
import java.util.Random;

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
