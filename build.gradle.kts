plugins {
    kotlin("jvm") version "2.0.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.25"
}

group = "pl.training"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val springVersion = "6.1.13"
val jacksonVersion = "2.17.2"

dependencies {
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-orm:$springVersion")
    implementation("org.springframework.data:spring-data-jpa:3.3.4")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.hibernate:hibernate-core:6.6.1.Final")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.aspectj:aspectjweaver:1.9.22.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
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
