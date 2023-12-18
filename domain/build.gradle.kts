plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    defaultConfig {
        compileSdk = Config.compileSdk
        minSdk = Config.minSdk
    }
    buildTypes {
        getByName("debug") {
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    namespace = "com.krakert.tracker.domain"
}

dependencies {
    //Compile time dependencies
    kapt(Deps.daggerHiltCompiler)

    //Framework dependencies
    implementation(Deps.daggerHiltCore)
    implementation(Deps.coroutinesCore)

}