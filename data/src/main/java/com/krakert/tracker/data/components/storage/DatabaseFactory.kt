package com.krakert.tracker.data.components.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseFactory {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): TrackerDatabase {
        return Room.databaseBuilder(
            appContext,
            TrackerDatabase::class.java,
            "app.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTrackerDao(appDatabase: TrackerDatabase): TrackerDao {
        return appDatabase.trackerDao()
    }
}