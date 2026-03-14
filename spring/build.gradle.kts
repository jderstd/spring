import org.gradle.api.publish.maven.MavenPublication

version = "0.1.0"

plugins {
    `java-library`
    kotlin("jvm")
    `maven-publish`
}

val springFrameworkVersion: String =
    providers
        .gradleProperty("springVersion")
        .get()

dependencies {
    api("org.springframework:spring-web:$springFrameworkVersion")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
