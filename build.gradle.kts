plugins {
    id("java")
    kotlin("jvm") version "2.2.0"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        maven("https://jitpack.io")
        mavenCentral()
    }

    dependencies {
        // Котлин
        compileOnly(kotlin("stdlib-jdk8"))

        // Логи
        compileOnly("org.slf4j:slf4j-api:2.0.17")
        compileOnly("com.github.nnikitochka:YetAnotherLogger:1.1")
    }

    kotlin {
        jvmToolchain(21)
    }
}