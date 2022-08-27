package com.krakert.tracker.di

import android.content.Context
import androidx.room.Room
import com.krakert.tracker.database.CryptoCacheDao
import com.krakert.tracker.database.CryptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent ::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CryptoDatabase {
        return Room.databaseBuilder(
            appContext,
            CryptoDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideCryptoDao(appDatabase: CryptoDatabase): CryptoCacheDao {
        return appDatabase.cryptoCacheDao()
    }
}