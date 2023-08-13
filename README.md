# erai(Extra Rules of Architecture Inspection)
erai is extra rules for Java inspecting architecture constraint, using ArchUnit.

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
    id "com.societegenerale.commons.arch-unit-gradle-plugin" version "3.0.0"
    ...
}

dependencies {
    ...
    archUnitExtraLib('io.github.footaku:erai:0.0.1')
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
If you only want to apply certain rules, create 'erai.yaml' in the classpath root.  
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
