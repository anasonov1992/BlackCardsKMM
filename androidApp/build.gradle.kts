plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    kotlin("android")
}

android {
    namespace = "com.example.blackcardskmm.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.blackcardskmm.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(project(":shared"))

    // Compose
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.compose.foundation:foundation:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.compiler:compiler:1.5.7")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Compose Accompanist
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.30.0")
    implementation("com.google.accompanist:accompanist-insets-ui:0.30.0")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.30.0")
    implementation("com.google.accompanist:accompanist-placeholder:0.30.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.0")
    implementation("com.google.accompanist:accompanist-navigation-material:0.30.0")
    implementation("com.google.accompanist:accompanist-permissions:0.30.0")

    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.google.android.material:material:1.11.0")

    // Koin
    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-android:3.5.3")
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    // Immutable Collections
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    // Collapsed Toolbar
    implementation("me.onebone:toolbar-compose:2.3.5")

    // Paging 3.0
    implementation("io.github.kuuuurt:multiplatform-paging:0.6.1")

    // Files
    implementation("commons-io:commons-io:2.11.0")

    // Reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.21")

    // Splash Core API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Balloon
    implementation("com.github.skydoves:balloon-compose:1.5.2")
    implementation("androidx.paging:paging-compose:3.3.0-alpha02")

    // Decompose
    implementation("com.arkivanov.decompose:decompose:2.2.1")
    implementation("com.arkivanov.decompose:extensions-compose-jetpack:2.2.1")

    // MVIKotlin
    implementation("com.arkivanov.mvikotlin:mvikotlin:3.3.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.3.0")
}