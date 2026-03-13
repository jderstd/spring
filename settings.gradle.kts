rootProject.name = "workspace"

include("spring")
include("spring-test")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
