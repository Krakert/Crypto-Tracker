@file:Suppress("unused", "SpellCheckingInspection")

object Versions {
    const val kotlin = "1.8.10"
    const val gradle = "7.4.2"

    const val kotlinCoroutines = "1.6.4"

    const val jacoco = "0.8.7"
    const val daggerHilt = "2.45"
    const val hilt = "1.0.0"

    const val accompanist = "0.23.1"

    const val androidAppcompat = "1.6.1"
    const val androidLifecycle = "2.6.1"

    const val compose = "1.4.4"
    const val composeCompiler = "1.4.4"
    const val composeRuntime = "1.4.0"
    const val composeActivity = "1.7.0"
    const val composeWear = "1.1.0"
    const val composeNavigation = "1.1.2"
    const val composeViewModel = "2.5.0"
    const val composeUi = "1.4.0"
    const val composeMaterial = "1.1.2"
    const val composePreview = "1.4.0"
    const val composeIcon = "1.4.0"

    const val coil = "2.3.0"
    const val composeLottie = "6.0.0"
    const val pallete = "2.0.3"

    const val roomCompiler = "2.5.1"
    const val roomRuntime = "2.5.1"
    const val roomKtx = "2.5.1"

    const val dagger = "2.45"

    const val firebaseAnalytics = "21.2.1"
    const val firebaseBom = "31.4.0"
    const val firebaseCrashlytics = "18.3.6"

    const val ktor = "2.2.4"
    const val napier = "2.6.1"
    const val serialization = "1.5.0"

}

object Projects {
    const val gradleTools = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.46.0"
    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:6.8.0"
    const val daggerHiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
    const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:${Versions.serialization}"
}

object Deps {
    // Android
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidAppcompat}"

    // Preferences DataStore
    const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"

    // Use of the input types WearOS has
    const val wearInput = "androidx.wear:wear-input:1.2.0-alpha02"

    // Ktor
    const val ktorCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClient = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorJvm = "io.ktor:ktor-client-core-jvm:${Versions.ktor}"
    const val ktorNeogotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktorSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val ktorJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val napier = "io.github.aakira:napier:${Versions.napier}"



    // Crashlytics
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseCrashlyitcs = "com.google.firebase:firebase-crashlytics-ktx:${Versions.firebaseCrashlytics}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}"

    // Room
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"

    // General Wear functionality
    const val wear = "androidx.wear:wear:1.2.0"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    // Compose const val to load images via coil
    const val composeCoil = "io.coil-kt:coil:${Versions.coil}"
    const val composeLottie = "com.airbnb.android:lottie-compose:${Versions.composeLottie}"
    const val palette = "com.github.skydoves:landscapist-palette:${Versions.pallete}"

    // Share preference
    const val preference = "androidx.preference:preference:1.2.0"

    // Compose Navigation
    const val androidNavigation = "androidx.navigation:navigation-compose:2.5.3"
    const val composeNavigation = "androidx.wear.compose:compose-navigation:${Versions.composeNavigation}"
    const val composeHiltNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0"

    const val composeCompiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.composeRuntime}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
    const val composePreview = "androidx.compose.ui:ui-tooling:${Versions.composePreview}"
    const val composeIcons = "androidx.compose.material:material-icons-core:${Versions.composeIcon}"
    const val composeIconsExtend = "androidx.compose.material:material-icons-extended:${Versions.composeIcon}"

    // Compose for Wear OS Dependencies
    const val composeMaterial = "androidx.wear.compose:compose-material:${Versions.composeWear}"

    //DI
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    const val daggerHiltJetpackCompiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"
    const val daggerHiltCore = "com.google.dagger:hilt-core:${Versions.daggerHilt}"

    // Testing
    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val tilePreview = "androidx.wear.tiles:tiles-renderer:1.1.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.0.1"
    const val junit = "junit:junit:4.13.2"

}