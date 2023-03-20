@file:Suppress("unused", "SpellCheckingInspection")

object Versions {
    // Shared versions
    const val kotlin = "1.6.10"
    const val jacoco = "0.8.7"
    const val daggerHilt = "2.42"
    const val composeWear = "1.1.1"
    const val accompanist = "0.23.1"
}

object Projects {
    const val gradleTools = "com.android.tools.build:gradle:7.4.1"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:6.8.0"
    const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
}

object Deps {
    // Android
    const val ktx = "androidx.core:core-ktx:1.9.0"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:1.4.2"

    // Preferences DataStore
    const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"

    // Use of the input types WearOS has
    const val wearInput = "androidx.wear:wear-input:1.2.0-alpha02"

    // Livedata
    const val composeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.composeWear}"

    // ViewModel
    const val composeViewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"

    private const val RETROFIT = "2.9.0"
    private const val OKHTTP = "4.10.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val okhttp = "com.squareup.okhttp3:okhttp:4.10.0"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:4.4.0"

    // Crashlytics
    const val firebaseBom = "com.google.firebase:firebase-bom:31.0.2"
    const val firebaseCrashlyitcs = "com.google.firebase:firebase-crashlytics-ktx:18.3.2"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:21.2.0"

    // Room
    const val roomCompiler = "androidx.room:room-compiler:2.4.3"
    const val roomRuntime = "androidx.room:room-runtime:2.4.3"

    // Kotlin Extensions and Coroutines support for Room
    const val roomKtx = "androidx.room:room-ktx:2.4.3"

    // Gson
    const val gson = "com.google.code.gson:gson:2.10"

    // General Wear functionality
    const val wear = "androidx.wear:wear:1.2.0"

    // Tiles functionality
    const val wearTile = "androidx.wear.tiles:tiles:1.1.0"

    // Preview Tiles in an Activity for testing purposes
    const val wearTilePreview = "androidx.wear.tiles:tiles-renderer:1.1.0"

    private const val COROUTINES = "1.6.3"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES"

    // Compose const val to load images via coil
    const val composeCoil = "io.coil-kt:coil:0.13.0"
    const val composeLottie = "com.airbnb.android:lottie-compose:5.2.0"
    const val palette = "com.github.skydoves:landscapist-palette:2.0.3"

    // Share preference
    const val preference = "androidx.preference:preference:1.2.0"

    // Compose Navigation
    const val composeNavigation = "androidx.wear.compose:compose-navigation:${Versions.composeWear}"
    const val composeHiltNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0"

    // General Compose dependencies
    const val composeActivity = "androidx.activity:activity-compose:1.5.0"

    // Compose preview
    const val composePreview = "androidx.compose.ui:ui-tooling:${Versions.composeWear}"
    const val composeIcons = "androidx.compose.material:material-icons-extended:${Versions.composeWear}"

    // Compose for Wear OS Dependencies
    const val composeMaterial = "androidx.wear.compose:compose-material:${Versions.composeWear}"

    //DI
    private const val ANDROID_HILT = "1.0.0"
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    const val daggerHiltJetpackCompiler = "androidx.hilt:hilt-compiler:$ANDROID_HILT"
    const val daggerHiltCore = "com.google.dagger:hilt-core:${Versions.daggerHilt}"

    // Testing
    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val tilePreview = "androidx.wear.tiles:tiles-renderer:1.1.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.0.1"
    const val junit = "junit:junit:4.13.2"

}