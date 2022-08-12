package com.krakert.tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.Converters
import com.krakert.tracker.model.DataCoinChart

//@Database(entities = [Coin::class, DataCoinChart::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class CryptoCacheRoomDatabase : RoomDatabase() {
//
//    abstract fun cryptoCacheDao(): CryptoCacheDao
//
//    companion object {
//        private const val DATABASE_NAME = "CRYPTO_CACHE_DATABASE"
//
//        @Volatile
//        private var cryptoCacheRoomDatabaseInstance: CryptoCacheRoomDatabase? = null
//
//        fun getDatabase(context: Context): CryptoCacheRoomDatabase? {
//            if (cryptoCacheRoomDatabaseInstance == null) {
//                synchronized(CryptoCacheRoomDatabase::class.java) {
//                    if (cryptoCacheRoomDatabaseInstance == null) {
//                        cryptoCacheRoomDatabaseInstance = Room.databaseBuilder(
//                            context.applicationContext,
//                            CryptoCacheRoomDatabase::class.java,
//                            DATABASE_NAME
//                        )
//                            .allowMainThreadQueries()
////                            .addMigrations(MIGRATION_1_2)
//                            .build()
//
//                    }
//                }
//            }
//            return cryptoCacheRoomDatabaseInstance
//        }
//    }
//}
//
//val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL(
//            "DELETE FROM coinTable"
//        )
//    }
//}