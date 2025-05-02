plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"

    kotlin("plugin.noarg") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("plugin.allopen") version "1.9.25"

    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    application
}

noArg {
    annotation("jakarta.persistence.Entity")
}

allOpen {
    annotations(
        "jakarta.persistence.Entity",
        "jakarta.persistence.Embeddable",
        "jakarta.persistence.MappedSuperclass"
    )
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.4.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("mysql:mysql-connector-java:8.0.33")

    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.hibernate:hibernate-core:6.6.3.Final")

    implementation("org.springframework.boot:spring-boot-starter-security:3.4.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.hibernate:hibernate-validator:8.0.0.Final")

    implementation("org.springframework.boot:spring-boot-starter-mail:3.4.0")

    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.4.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate6:2.18.2")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")

    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")

    implementation("org.aspectj:aspectjweaver:1.9.2")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
