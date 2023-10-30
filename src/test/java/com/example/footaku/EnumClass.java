package com.example.footaku;

import java.util.Arrays;

public enum EnumClass {
    NONE(0),
    ONE(1),
    TWO(2);

    private final int id;

    EnumClass(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    @lombok.NonNull
    public static EnumClass from(int id) {
        return Arrays.stream(EnumClass.values()).filter(v -> v.id == id).findFirst().orElseThrow();
    }
}
