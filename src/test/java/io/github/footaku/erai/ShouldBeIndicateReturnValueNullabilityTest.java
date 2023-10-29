package io.github.footaku.erai;

import com.societegenerale.commons.plugin.utils.ArchUtils;
import io.github.footaku.erai.rule.ShouldBeIndicateReturnValueNullability;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class ShouldBeIndicateReturnValueNullabilityTest {
    @BeforeEach
    public void setup() {
        //noinspection InstantiationOfUtilityClass
        new ArchUtils(new SilentLog());
    }

    @Test
    public void defaultSettingTestShouldThrowViolations() {
        var testTargetPath = "com/example/footaku";

        var th = Assertions.catchThrowable(() -> {
            var sut = new ShouldBeIndicateReturnValueNullability();
            addExcludedClasses(sut);

            sut.execute(testTargetPath, new TestScopeProvider(), Collections.emptySet());
        });

        Assertions.assertThat(th).isInstanceOf(AssertionError.class).hasMessageContaining("was violated (2 times)");
    }

    @Test
    public void specifiedAnnotationTestShouldThrowViolations() {
        var testTargetPath = "com/example/footaku";

        var th = Assertions.catchThrowable(() -> {
            var sut = new ShouldBeIndicateReturnValueNullability();
            addExcludedClasses(sut);
            addAvailableAnnotations(sut);

            sut.execute(testTargetPath, new TestScopeProvider(), Collections.emptySet());
        });

        Assertions.assertThat(th).isInstanceOf(AssertionError.class).hasMessageContaining("was violated (10 times)");
    }

    private void addExcludedClasses(Object sut) {
        try {
            var field = ShouldBeIndicateReturnValueNullability.class.getDeclaredField("excludedClasses");
            field.setAccessible(true);
            field.set(sut, List.of("com.example.footaku.NotInspectionClass"));
        } catch (Exception e) {
            // TODO WARN log
        }
    }

    private void addAvailableAnnotations(Object sut) {
        try {
            var field = ShouldBeIndicateReturnValueNullability.class.getDeclaredField("availableAnnotations");
            field.setAccessible(true);
            field.set(sut, List.of("lombok.NonNull"));
        } catch (Exception e) {
            // TODO WARN log
        }
    }
}
