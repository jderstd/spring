plugins {
    // Kotlin JVM
    id("org.jetbrains.kotlin.jvm")
    // Java API
    id("java-library")
    // Maven publication
    id("com.vanniktech.maven.publish")
}

val moduleVersion: String = "0.1.0"

version = moduleVersion

val repo: String = "github.com/jderstd/spring"

val repoURL: String = "https://$repo"

mavenPublishing {
    coordinates(group as String?, "spring", moduleVersion)

    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    pom {
        name.set("JDER Spring")
        description.set("A response builder for Spring")
        inceptionYear.set("2026")
        url.set(repoURL)

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/license/mit")
            }
        }

        developers {
            developer {
                id.set("alpheusmtx")
                name.set("Alpheus")
                email.set("contact@alphe.us")
            }
        }

        scm {
            url.set(repoURL)
            connection.set("scm:git:git://$repo.git")
            developerConnection.set("scm:git:ssh://git@$repo.git")
        }
    }
}

val springFrameworkVersion: String =
    providers
        .gradleProperty("springVersion")
        .get()

dependencies {
    api("org.springframework:spring-web:$springFrameworkVersion")
}
