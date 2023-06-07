plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.8.10"
    id("com.android.library")
    id("kotlin-parcelize")
    id("com.rickclephas.kmp.nativecoroutines")
    id("com.squareup.sqldelight")
}

kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
    }
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

                // Ktor
                implementation("io.ktor:ktor-client-core:2.2.4")
                implementation("io.ktor:ktor-client-content-negotiation:2.2.4")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
                implementation("io.ktor:ktor-client-logging:2.2.4")
                implementation("io.ktor:ktor-client-auth:2.2.4")

                // SQLight
                implementation("com.squareup.sqldelight:runtime:1.5.5")

                // Koin
                implementation("io.insert-koin:koin-core:3.4.0")

                // Paging
                implementation("io.github.kuuuurt:multiplatform-paging:0.6.1")

                // Immutable Collections
                implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

                // UUID
                implementation("com.benasher44:uuid:0.7.0")

                // Decompose
                implementation("com.arkivanov.decompose:decompose:2.0.0-alpha-02")

                // MVIKotlin
                implementation("com.arkivanov.mvikotlin:mvikotlin:3.2.1")
                implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.2.1")
                implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.2.1")
                implementation("com.arkivanov.mvikotlin:rx:3.2.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)

                // Ktor
                implementation("io.ktor:ktor-client-android:2.2.4")

                // SQLight
                implementation("com.squareup.sqldelight:android-driver:1.5.5")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // Ktor
                implementation("io.ktor:ktor-client-darwin:2.2.4")

                // SQLight
                implementation("com.squareup.sqldelight:native-driver:1.5.5")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.blackcardskmm"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

sqldelight {
    database("BlackCardsDb") {
        packageName = "com.example.blackcardskmm.db"
        sourceFolders = listOf("sqldelight")
    }
}