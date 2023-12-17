import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val prop = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "keystore.properties")))
}

android {
    signingConfigs {
        create("release") {
            keyAlias = prop.getProperty("keyAlias")
            keyPassword = prop.getProperty("keyPassword")
            storeFile = file(prop.getProperty("storeFile"))
            storePassword = prop.getProperty("storePassword")
        }
    }
    defaultConfig {
        compileSdk = Config.compileSdk
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        applicationId = Config.applicationId
        versionName = Config.versionName
        versionCode = Config.versionCode
        setProperty(
            "archivesBaseName",
            "$applicationId-v$versionCode($versionName)"
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    namespace = "com.krakert.tracker.app"
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

    //Compile time dependencies
    kapt(Deps.daggerHiltCompiler)
    kapt(Deps.daggerHiltJetpackCompiler)
    implementation(Deps.androidNavigation)

    //Firebase
    implementation(platform(Deps.firebaseBom))
    implementation(Deps.firebaseAnalytics)
    implementation(Deps.firebaseCrashlyitcs)

    //Framework dependencies
    implementation(Deps.daggerHilt)

    //Development dependencies
    implementation(Deps.timber)

    //Firebase dependencies
    implementation(Deps.firebaseBom)
    implementation(Deps.firebaseCrashlyitcs)
    implementation(Deps.firebaseAnalytics)

}
