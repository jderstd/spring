import org.gradle.api.JavaVersion
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

group = "io.github.jderstd"

plugins {
    // Kotlin JVM
    id("org.jetbrains.kotlin.jvm") version "2.3.10" apply false
    // Maven publication
    id("com.vanniktech.maven.publish") version "0.36.0" apply false
}

data class JavaToolchain(
    val version: Int,
    val javaVersion: JavaVersion,
    val jvmTarget: JvmTarget,
)

fun resolveJavaToolchain(version: Int): JavaToolchain =
    when (version) {
        17 -> {
            JavaToolchain(
                17,
                JavaVersion.VERSION_17,
                JvmTarget.JVM_17,
            )
        }

        21 -> {
            JavaToolchain(
                21,
                JavaVersion.VERSION_21,
                JvmTarget.JVM_21,
            )
        }

        else -> {
            error("Unsupported Java toolchain version: $version")
        }
    }

val javaToolchain: JavaToolchain = resolveJavaToolchain(17)
val kotlinVersion: KotlinVersion = KotlinVersion.KOTLIN_2_3

subprojects {
    group = rootProject.group

    // Sources
    repositories {
        mavenCentral()
    }

    // Test Platform
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    // Kotlin JVM
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinJvmProjectExtension> {
            explicitApi()
            jvmToolchain(javaToolchain.version)

            compilerOptions {
                jvmTarget = javaToolchain.jvmTarget
                apiVersion = kotlinVersion
                languageVersion = kotlinVersion
            }
        }
    }

    // Java API
    pluginManager.withPlugin("java-library") {
        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion = JavaLanguageVersion.of(javaToolchain.version)
            }

            sourceCompatibility = javaToolchain.javaVersion
            targetCompatibility = javaToolchain.javaVersion
        }
    }
}
