plugins {
    `java-library`
    `maven-publish`
}

group = "io.github.footaku"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
    test {
        java {
            srcDir("src/test/java")
        }
    }
}

dependencies {
    implementation("com.societegenerale.commons:arch-unit-build-plugin-core:3.0.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.2")

    testImplementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    testImplementation("org.springframework:spring-core:6.0.9")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testCompileOnly("org.projectlombok:lombok:1.18.28")

}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("erai")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    val javadocJar by creating(Jar::class) {
        dependsOn.add(javadoc)
        archiveClassifier.set("javadoc")
        from(javadoc)
    }

    artifacts {
        archives(sourcesJar)
        archives(javadocJar)
        archives(jar)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "io.github.footaku"
            artifactId = "erai"
            version = "0.0.1"

            pom {
                name.set("erai")
                description.set("Extra Rules of Architecture Inspection")
                url.set("https://github.com/footaku/erai")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("footaku")
                        name.set("footaku")
                        email.set("jptkyfjmt@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/footaku/erai")
                    developerConnection.set("scm:git:git://github.com/footaku/erai")
                    url.set("https://github.com/footaku/erai/tree/main")
                }
            }

            from(components["java"])
        }
    }
}
