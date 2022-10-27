package com.stakt.tracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stakt.tracker.models.database.DataGraph
import com.stakt.tracker.models.database.PriceCoins
import com.stakt.tracker.models.responses.Coin
import com.stakt.tracker.models.responses.CoinFullData

@Database(
    entities = [Coin::class, CoinFullData::class, PriceCoins::class, DataGraph::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun cryptoCacheDao(): CryptoCacheDao

}