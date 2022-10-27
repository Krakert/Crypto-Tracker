package com.stakt.tracker.di

import android.content.Context
import androidx.room.Room
import com.stakt.tracker.database.CryptoCacheDao
import com.stakt.tracker.database.CryptoDatabase
import com.stakt.tracker.database.DataConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CryptoDatabase {
        return Room.databaseBuilder(
            appContext,
            CryptoDatabase::class.java,
            "app.db"
        ).addTypeConverter(DataConverters()).build()
    }

    @Provides
    fun provideCryptoDao(appDatabase: CryptoDatabase): CryptoCacheDao {
        return appDatabase.cryptoCacheDao()
    }
}