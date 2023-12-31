@file:Suppress("unused", "SpellCheckingInspection")

object Versions {
    const val kotlin = "1.9.10"
    const val gradle = "7.4.2"

    const val kotlinCoroutines = "1.6.4"

    const val daggerHilt = "2.46"
    const val hilt = "1.0.0"

    const val androidAppcompat = "1.6.1"
    const val androidSplash = "1.0.1"

    const val compose = "1.4.7"
    const val composeCompiler = "1.5.3"
    const val composeRuntime = "1.4.0"
    const val composeActivity = "1.7.0"
    const val composeWear = "1.2.1"
    const val composeNavigation = "1.1.2"
    const val composeViewModel = "2.5.0"
    const val composeUi = "1.4.0"
    const val composeMaterial = "1.1.2"
    const val composePreview = "1.4.0"
    const val composeIcon = "1.4.0"

    const val coil = "2.4.0"
    const val landscapistCoil = "2.0.3"
    const val composeLottie = "6.0.0"
    const val pallete = "2.0.3"

    const val googleService = "4.3.15"
    const val firebaseAnalytics = "21.2.1"
    const val firebaseBom = "32.3.1"
    const val firebaseCrashlytics = "2.9.9"

    const val ktor = "2.3.1"
    const val SLF4j = "1.7.36"
    const val serialization = "1.6.0"

}

object Deps {
    // Android
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidAppcompat}"
    const val androidSplash = "androidx.core:core-splashscreen:${Versions.androidSplash}"

    // Use of the input types WearOS has
    const val wearInput = "androidx.wear:wear-input:1.2.0-alpha02"

    // Ktor
    const val ktorCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClient = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorNeogotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktorSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val ktorJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val SLF4J = "org.slf4j:slf4j-android:${Versions.SLF4j}"

    // Crashlytics
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseCrashlyitcs = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"

    // General Wear functionality
    const val wear = "androidx.wear:wear:1.2.0"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    // Compose const val to load images via coil
    const val composeCoil = "io.coil-kt:coil:${Versions.coil}"
    const val landscapistCoil = "com.github.skydoves:landscapist-coil:${Versions.landscapistCoil}"
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
    const val composeFoundation = "androidx.wear.compose:compose-foundation:${Versions.composeWear}"

    //DI
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    const val daggerHiltJetpackCompiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"
    const val daggerHiltCore = "com.google.dagger:hilt-core:${Versions.daggerHilt}"

    // Testing
    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val tilePreview = "androidx.wear.tiles:tiles-renderer:1.1.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
    const val junit = "junit:junit:4.13.2"
    const val androidTestJunit = "androidx.test.ext:junit:1.1.5"
    const val junitJupiter = "org.junit.jupiter:junit-jupiter:5.8.1"
    const val composeUiTools = "androidx.compose.ui:ui-tooling"
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest"
}

