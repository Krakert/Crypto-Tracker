import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    compileSdk = Config.compileSdk
    defaultConfig {
        buildConfigField("String", "BUILD_TIME", "\"${SimpleDateFormat("MM-dd-yyyy_hh-mm").format(Date())}\"")
        buildConfigField("String", "VERSION_NAME", "\"${Config.versionName}\"")
        buildConfigField("String", "VERSION_CODE", "\"${Config.versionCode}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    namespace = "com.krakert.tracker.presentation"
}

dependencies {
    implementation(project(":domain"))

    //Compile time dependencies
    kapt(Deps.composeCompiler)
    kapt(Deps.daggerHiltCompiler)
    kapt(Deps.daggerHiltJetpackCompiler)

    //Android dependencies
    implementation(Deps.appCompat)

    //Framework dependencies
    implementation(Deps.daggerHilt)
    implementation(Deps.timber)

    // Compose
    implementation(Deps.composeRuntime)
    implementation(Deps.composeActivity)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeIcons)
    implementation(Deps.composeIconsExtend)
    implementation(Deps.composePreview)
    implementation(Deps.composeViewModel)
    implementation(Deps.composeNavigation)
    implementation(Deps.composeHiltNavigation)
    implementation(Deps.composeCoil)
    implementation(Deps.landscapistCoil)
    implementation(Deps.palette)

}