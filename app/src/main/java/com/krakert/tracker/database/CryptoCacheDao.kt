package com.krakert.tracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.DataCoinChart

@Dao
interface CryptoCacheDao {

    @Query ("SELECT * FROM coinTable")
    suspend fun getListCoins(): List<Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setListCoins(coins: List<Coin>)

    @Query ("SELECT * FROM dataCoinTable")
    suspend fun getListDataCoins(): List<DataCoinChart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setListDataCoins(coins: List<DataCoinChart>)
}