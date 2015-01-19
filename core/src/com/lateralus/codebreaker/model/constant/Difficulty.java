package com.lateralus.codebreaker.model.constant;

public enum Difficulty {
    Easy, Normal, Hard;

    public Difficulty getNext() {
        switch (this) {
            case Easy:      return Normal;
            case Normal:    return Hard;
            case Hard:      return Easy;
        }
        return Normal;
    }

    public Difficulty getPrevious() {
        switch (this) {
            case Easy:      return Hard;
            case Normal:    return Easy;
            case Hard:      return Normal;
        }
        return Normal;
    }
}
