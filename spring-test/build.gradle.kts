plugins {
    `java-library`
    kotlin("jvm")
}

val springFrameworkVersion: String =
    providers
        .gradleProperty("springVersion")
        .get()

dependencies {
    testImplementation(project(":spring"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework:spring-web:$springFrameworkVersion")
}
