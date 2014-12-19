package com.lateralus.codebreaker.controller.helper;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Range;

import java.util.List;

import static com.google.common.collect.DiscreteDomain.integers;
import static com.google.common.collect.Lists.newArrayList;

public class NumberUtils {

    public static List<Integer> numberRange(int size) {
        Range<Integer> range = Range.closed(0, size - 1);
        return newArrayList(ContiguousSet.create(range, integers()));
    }
}
