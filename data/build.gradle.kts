plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("kapt")
}

android {
    defaultConfig {
        compileSdk = Config.compileSdk
        minSdk = Config.minSdk
    }
    buildTypes {
        getByName("debug") {
            enableUnitTestCoverage = true
        }
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

    namespace = "com.krakert.tracker.data"
}

dependencies {
    implementation(project(":domain"))

    //Compile time dependencies
    kapt(Deps.daggerHiltCompiler)

    //Framework dependencies
    implementation(Deps.daggerHilt)

    //Ktor
    implementation(Deps.ktorCore)
    implementation(Deps.ktorClient)
    implementation(Deps.ktorNeogotiation)
    implementation(Deps.ktorSerialization)
    implementation(Deps.ktorJson)
    implementation(Deps.ktorLogging)
    implementation(Deps.SLF4J)

    implementation(Deps.preference)
    implementation(Deps.timber)

    // Kotlin Coroutines
    implementation(Deps.coroutinesCore)
    implementation(Deps.coroutinesAndroid)

    //Testing dependencies
    testImplementation(Deps.junit)
}

kapt {
    correctErrorTypes = true
}