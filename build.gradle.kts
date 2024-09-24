plugins {
    kotlin("jvm") version "2.0.10"
}

group = "pl.training"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.1.13")
    implementation("org.aspectj:aspectjweaver:1.9.22.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}