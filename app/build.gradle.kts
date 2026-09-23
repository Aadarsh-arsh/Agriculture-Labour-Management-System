import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.serialization")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use {
        localProperties.load(it)
    }
}

val supabaseUrl = localProperties.getProperty("SUPABASE_URL", "")
val supabasePublishableKey =
    localProperties.getProperty("SUPABASE_PUBLISHABLE_KEY", "")

android {
    namespace = "com.example.kisanmitra"

    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.kisanmitra"

        minSdk = 26

        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"$supabaseUrl\""
        )

        buildConfigField(
            "String",
            "SUPABASE_PUBLISHABLE_KEY",
            "\"$supabasePublishableKey\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // -------------------------
    // Jetpack Compose
    // -------------------------

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // -------------------------
    // Android / Lifecycle
    // -------------------------

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // -------------------------
    // Navigation
    // -------------------------

    implementation("androidx.navigation:navigation-compose:2.9.4")

    // -------------------------
    // Room Database
    // -------------------------

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // -------------------------
    // Supabase
    // -------------------------

    implementation(
        platform("io.github.jan-tennert.supabase:bom:3.2.1")
    )

    implementation(
        "io.github.jan-tennert.supabase:auth-kt"
    )

    implementation(
        "io.github.jan-tennert.supabase:postgrest-kt"
    )

    // Ktor Android networking
    implementation(
        "io.ktor:ktor-client-android:3.2.1"
    )

    // -------------------------
    // Testing
    // -------------------------

    testImplementation(libs.junit)

    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        libs.androidx.junit
    )

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )
}