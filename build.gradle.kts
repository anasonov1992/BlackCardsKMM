plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.3").apply(false)
    id("com.android.library").version("8.1.3").apply(false)
    kotlin("android").version("1.9.21").apply(false)
    kotlin("multiplatform").version("1.9.21").apply(false)
}

buildscript {
    dependencies {
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.21-1.0.16")
        classpath("com.rickclephas.kmp:kmp-nativecoroutines-gradle-plugin:0.13.3")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath("dev.icerock.moko:resources-generator:0.23.0")
    }
    repositories {
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}