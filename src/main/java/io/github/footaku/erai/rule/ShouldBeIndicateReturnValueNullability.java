package io.github.footaku.erai.rule;

import com.tngtech.archunit.core.domain.JavaModifier;
import io.github.footaku.erai.Erai;
import com.societegenerale.commons.plugin.rules.ArchRuleTest;
import com.societegenerale.commons.plugin.service.ScopePathProvider;
import com.societegenerale.commons.plugin.utils.ArchUtils;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import java.util.Collection;
import java.util.List;

/**
 * ShouldBeIndicateReturnValueNullability
 */
public class ShouldBeIndicateReturnValueNullability implements ArchRuleTest {

    private final List<String> availableAnnotations = Erai.getSetting().nullability().availableAnnotations();
    private final List<String> excludeClasses = Erai.getSetting().nullability().excludeClasses();

    /**
     * Default Constructor
     */
    public ShouldBeIndicateReturnValueNullability() {}

    @Override
    public void execute(String packagePath, ScopePathProvider scopePathProvider, Collection<String> excludedPaths) {
        var rule = ArchRuleDefinition.methods()
            .that(isNotExcludedClass)
            .and(hasReturnValue)
            .and(isReturnBoxingType)
            .and(isNotEnumBasicMethod)
            .and(isNotGeneratedCode)
            .and(isNotOverrideBasicMethod)
            .should(beAnnotatedWith)
            .allowEmptyShould(true);

        var classes = ArchUtils.importAllClassesInPackage(scopePathProvider.getMainClassesPath(), packagePath, excludedPaths);
        rule.check(classes);
    }

    DescribedPredicate<JavaMethod> isNotExcludedClass =
        new DescribedPredicate<>("is not excluded classes") {
            @Override
            public boolean test(JavaMethod javaMethod) {
                return !excludeClasses.contains(javaMethod.getOwner().getName());
            }
        };

    DescribedPredicate<JavaMethod> hasReturnValue =
        new DescribedPredicate<>("has return value") {
            @Override
            public boolean test(JavaMethod input) {
                var returnTYpe = input.getReturnType().toErasure();
                return !returnTYpe.getName().equals("void");
            }
        };

    DescribedPredicate<JavaMethod> isReturnBoxingType =
        new DescribedPredicate<>("is return boxing type") {
            @Override
            public boolean test(JavaMethod input) {
                var returnTYpe = input.getReturnType().toErasure();
                return !returnTYpe.isPrimitive();
            }
        };

    DescribedPredicate<JavaMethod> isNotEnumBasicMethod =
        new DescribedPredicate<>("is not Enum basic methods") {
            private final static List<String> BASIC_METHODS = List.of("$values", "values", "valueOf");

            @Override
            public boolean test(JavaMethod input) {
                if (!BASIC_METHODS.contains(input.getName())) {
                    return true;
                }

                return !input.getOwner().isEnum();
            }
        };

    DescribedPredicate<JavaMethod> isNotGeneratedCode =
        new DescribedPredicate<>("is not generated code") {
            private final static List<String> GENERATED_ANNOTATION_NAMES = List.of(
                "lombok.Generated"
            );

            @Override
            public boolean test(JavaMethod input) {
                if (isDoma2Generated(input)) {
                    return false;
                }
                var annotations = input.getAnnotations().stream().map(a -> a.getRawType().getName()).toList();
                return annotations.stream().noneMatch(GENERATED_ANNOTATION_NAMES::contains);
            }

            private boolean isDoma2Generated(JavaMethod input) {
                var clazz = input.getOwner();
                var classAnnotations = clazz.getAnnotations().stream().map(a -> a.getRawType().getName()).toList();

                if (clazz.getPackageName().startsWith("__")) {
                    return true;
                }

                if (classAnnotations.contains("org.seasar.doma.DaoImplementation")) {
                    return true;
                }

                return classAnnotations.contains("org.seasar.doma.EntityTypeImplementation")
                    && clazz.getSimpleName().startsWith("_");
            }
        };

    DescribedPredicate<JavaMethod> isNotOverrideBasicMethod =
        new DescribedPredicate<>("is not override basic method") {
            private final static List<String> BASIC_METHODS = List.of("toString", "equals", "hashCode");

            @Override
            public boolean test(JavaMethod input) {
                if (!BASIC_METHODS.contains(input.getName())) {
                    return true;
                }

                if (input.getOwner().isRecord()) {
                    return false;
                }

                var annotations = input.getAnnotations()
                    .stream()
                    .map(a -> a.getRawType().getName())
                    .toList();

                return annotations.stream().noneMatch(n -> n.equals("java.lang.Override"));
            }
        };

    ArchCondition<JavaMethod> beAnnotatedWith = new ArchCondition<>("be annotated with non-null or nullable") {
        @Override
        public void check(JavaMethod input, ConditionEvents events) {
            var modifiers = input.getModifiers();
            if (modifiers.contains(JavaModifier.PRIVATE)) {
                return;
            }

            var annotations = input.getAnnotations()
                .stream()
                .map(a -> a.getRawType().getName())
                .toList();

            var contains = annotations.stream().anyMatch(availableAnnotations::contains);
            if (!contains) {
                var owner = input.getOwner().getName();
                var methodName = input.getName();
                events.add(SimpleConditionEvent.violated(
                    input.getFullName(),
                    owner + "#" + methodName + " should be annotated with non-null or nullable annotation."
                ));
            }
        }
    };
}
