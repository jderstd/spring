version = "0.1.0"

plugins {
    `java-library`
    kotlin("jvm")
}

val springFrameworkVersion = providers
    .gradleProperty("springVersion")
    .get()

dependencies {
    // main
    compileOnly("org.springframework:spring-web:$springFrameworkVersion")
    compileOnly("org.springframework:spring-context:$springFrameworkVersion")

    // test
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework:spring-web:$springFrameworkVersion")
    testImplementation("org.springframework:spring-context:$springFrameworkVersion")
    testImplementation("org.springframework:spring-test:$springFrameworkVersion")
}
