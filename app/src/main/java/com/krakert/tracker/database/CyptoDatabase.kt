package com.krakert.tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krakert.tracker.models.responses.Coin
import com.krakert.tracker.models.database.Converters

@Database(entities = [Coin::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun cryptoCacheDao(): CryptoCacheDao

    companion object {
        private const val DATABASE_NAME = "CRYPTO_CACHE_DATABASE"

        @Volatile
        private var cryptoDatabaseInstance: CryptoDatabase? = null

        fun getDatabase(context: Context): CryptoDatabase? {
            if (cryptoDatabaseInstance == null) {
                synchronized(CryptoDatabase::class.java) {
                    if (cryptoDatabaseInstance == null) {
                        cryptoDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            CryptoDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()

                    }
                }
            }
            return cryptoDatabaseInstance
        }
    }
}