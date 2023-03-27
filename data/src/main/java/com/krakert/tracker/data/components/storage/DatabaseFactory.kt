package com.krakert.tracker.data.components.storage

import android.content.Context
import androidx.room.Room
import com.krakert.tracker.data.components.storage.mapper.DataConverters
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

object DatabaseFactory {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TrackerDatabase {
        return Room.databaseBuilder(
            appContext,
            TrackerDatabase::class.java,
            "app.db"
        ).addTypeConverter(DataConverters()).build()
    }

    @Provides
    fun provideTrackerDao(appDatabase: TrackerDatabase): TrackerDao {
        return appDatabase.trackerDao()
    }
}