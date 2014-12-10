package com.lateralus.codebreaker.controller.helper;

import java.util.Random;

public class RandomUtils {

    private static final Random random = new Random();

    public static int randomInt(int high) {
        return randomInt(0, high);
    }

    public static int randomInt(int low, int high) {
        return random.nextInt(high) + low;
    }
}
