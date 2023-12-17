// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin apply false
    id("com.google.dagger.hilt.android") version Versions.daggerHilt apply false
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.serialization apply false
    id("com.google.gms.google-services") version Versions.googleService apply false
    id("com.google.firebase.crashlytics") version Versions.firebaseCrashlytics apply false
}