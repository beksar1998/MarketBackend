import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}

group = "com.beksar.market"
version = ""

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.springboot.starter.web)
    implementation(libs.springboot.starter.data.jpa)
    implementation(libs.springboot.starter.validation)
    implementation(libs.springboot.starter.security)
    implementation(libs.springboot.starter.mail)

    implementation(libs.jetbrains.kotlin.reflect)

    implementation(libs.swagger.ui)
    implementation(libs.swagger.api)

    implementation(libs.jwt.api)
    implementation(libs.jwt.impl)
    implementation(libs.jwt.jackson)

    runtimeOnly(libs.mysql.driver)

    implementation(libs.telegram)

    implementation("javax.xml.bind:jaxb-api:2.3.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
