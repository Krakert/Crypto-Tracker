package com.krakert.tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krakert.tracker.models.Coin
import com.krakert.tracker.models.database.Converters

@Database(entities = [Coin::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CryptoCacheRoomDatabase : RoomDatabase() {

    abstract fun cryptoCacheDao(): CryptoCacheDao

    companion object {
        private const val DATABASE_NAME = "CRYPTO_CACHE_DATABASE"

        @Volatile
        private var cryptoCacheRoomDatabaseInstance: CryptoCacheRoomDatabase? = null

        fun getDatabase(context: Context): CryptoCacheRoomDatabase? {
            if (cryptoCacheRoomDatabaseInstance == null) {
                synchronized(CryptoCacheRoomDatabase::class.java) {
                    if (cryptoCacheRoomDatabaseInstance == null) {
                        cryptoCacheRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            CryptoCacheRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()

                    }
                }
            }
            return cryptoCacheRoomDatabaseInstance
        }
    }
}