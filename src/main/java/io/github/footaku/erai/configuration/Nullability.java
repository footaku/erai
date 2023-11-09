package io.github.footaku.erai.configuration;

import java.util.List;
import java.util.Objects;

/**
 * Nullability settings.
 */
public record Nullability(ReturnValue returnValue) {
    private static final List<String> DEFAULT_AVAILABLE_ANNOTATIONS = List.of(
        "lombok.NonNull",
        "jakarta.annotation.Nonnull",
        "jakarta.annotation.Nullable",
        "javax.annotation.Nonnull",
        "javax.annotation.Nullable",
        "org.springframework.lang.NonNull",
        "org.springframework.lang.Nullable"
    );

    /**
     * Listing available annotations for return values.
     *
     * @return enable
     */
    public List<String> availableAnnotations() {
        if (Objects.isNull(returnValue.availableAnnotations) || returnValue.availableAnnotations.isEmpty()) {
            return DEFAULT_AVAILABLE_ANNOTATIONS;
        }
        return returnValue.availableAnnotations;
    }

    /**
     * Listing excluded classes from inspection.
     *
     * @return enable
     */
    public List<String> excludeClasses() {
        if (Objects.isNull(returnValue.excludeClasses) || returnValue.excludeClasses.isEmpty()) {
            return List.of();
        }
        return returnValue.excludeClasses;
    }

    /**
     * Return value settings.
     */
    public record ReturnValue(
        List<String> availableAnnotations,
        List<String> excludeClasses
    ) {
    }
}
