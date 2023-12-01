# erai(Extra Rules of Architecture Inspection)
erai is extra rules for Java inspecting architecture constraint, using ArchUnit.
- 0.0.x: Compatible with JDK 17
- 0.1.x: Compatible with JDK 21

## Implemented
- Inspect return values have nullability annotation

## TBD
- Inspect method arguments have nullability annotation

# Getting Started
## Set up
build.gradle
```groovy
plugins {
    ...
    // For JDK 17
    id "com.societegenerale.commons.arch-unit-gradle-plugin" version "3.1.0"
    // For JDK 21
    id "com.societegenerale.commons.arch-unit-gradle-plugin" version "4.0.0"
    ...
}

dependencies {
    ...
    // For JDK 17
    archUnitExtraLib('io.github.footaku:erai:0.0.7')
    // For JDK 21
    archUnitExtraLib('io.github.footaku:erai:0.1.0')
    ...
}

archUnit {
    preConfiguredRules=[
            "io.github.footaku.erai.rule.ShouldBeIndicateReturnValueNullability"
    ]
}
```

## Settings
In The default settings, executes all rules.  
If you only want to apply certain rules, create 'erai.yaml' in the project root.  
The configuration values are as follows.
```yaml
# Presence of Nullability annotation
nullability:
  returnValue:
    availableAnnotations:                 # FQCN of annotations that can be applied to return values.
      - "jakarta.annotation.Nonnull"
      - "javax.annotation.Nonnull"
    excludeClasses:                       # FQCN of classes that excluding from this rule.
      - "io.github.footaku.erai.NotInspectionClass"
```

## Run inspections
```shell
gradle checkRules
```
