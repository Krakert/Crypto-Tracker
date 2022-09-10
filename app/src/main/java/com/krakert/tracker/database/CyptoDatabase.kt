package com.krakert.tracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krakert.tracker.models.database.DataGraph
import com.krakert.tracker.models.database.PriceCoins
import com.krakert.tracker.models.responses.Coin
import com.krakert.tracker.models.responses.CoinFullData

@Database(
    entities = [Coin::class, CoinFullData::class, PriceCoins::class, DataGraph::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun cryptoCacheDao(): CryptoCacheDao

}