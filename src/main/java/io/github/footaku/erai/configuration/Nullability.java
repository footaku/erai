package io.github.footaku.erai.configuration;

import java.util.List;
import java.util.Objects;

/**
 * Nullability settings.
 */
public record Nullability(boolean enable, ReturnValue returnValue) {
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
        if (Objects.isNull(returnValue.availableAnnotations) || returnValue.availableAnnotations.size() < 1) {
            return DEFAULT_AVAILABLE_ANNOTATIONS;
        }
        return returnValue.availableAnnotations;
    }

    /**
     * Listing excluded classes from inspection.
     *
     * @return enable
     */
    public List<String> excludedClasses() {
        if (Objects.isNull(returnValue.excludedClasses) || returnValue.excludedClasses.size() < 1) {
            return List.of();
        }
        return returnValue.excludedClasses;
    }

    /**
     * Return value settings.
     */
    public record ReturnValue(
        List<String> availableAnnotations,
        List<String> excludedClasses
    ) {
    }
}
