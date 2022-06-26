package com.krakert.tracker.database

import androidx.room.*
import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.DataCoin

@Dao
interface CryptoCacheDao {

    @Query ("SELECT * FROM coinTable")
    suspend fun getListCoins(): List<Coin>

    @Update
    suspend fun setListCoins(coins: List<Coin>)

    @Query ("SELECT * FROM dataCoinTable")
    suspend fun getListDataCoins(): List<DataCoin>

    @Update
    suspend fun setListDataCoins(coins: List<DataCoin>)
}