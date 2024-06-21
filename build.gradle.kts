val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.1"
    kotlin("plugin.serialization") version "1.8.0"
}

group = "com.kylix"
version = "0.0.1"
application {
    mainClass.set("com.kylix.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Firebase
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("com.google.firebase:firebase-admin:9.1.1")

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation("org.litote.kmongo:kmongo-coroutine:4.8.0") // latest: 4.8.0

}

tasks.create("stage") {
    dependsOn("installDist")
}