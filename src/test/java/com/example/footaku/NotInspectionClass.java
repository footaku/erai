package com.example.footaku;

public class NotInspectionClass {
    public void returnVoid() {
    }

    public String returnStringWithoutAnnotation() {
        return "Hello, world";
    }

    public static String staticMethodWithoutAnnotation() {
        return "Hello, world";
    }

    @lombok.NonNull
    public static String staticMethodWithAnnotation() {
        return "Hello, world";
    }

    @lombok.NonNull
    public String annotatedWithLombokNonnull() {
        return "Hello, world";
    }

    @javax.annotation.Nonnull
    public String annotatedWithJavaxNonnull() {
        return "Hello, world";
    }

    @jakarta.annotation.Nonnull
    public String annotatedWithJakartaNonnull() {
        return "Hello, world";
    }

    @org.springframework.lang.NonNull
    public String annotatedWithSpringNonnull() {
        return "Hello, world";
    }

    @javax.annotation.Nullable
    public String annotatedWithJavaxNullable() {
        return null;
    }

    @jakarta.annotation.Nullable
    public String annotatedWithJakartaNullable() {
        return "Hello, world";
    }

    @org.springframework.lang.Nullable
    public String annotatedWithSpringNullable() {
        return "Hello, world";
    }
}
