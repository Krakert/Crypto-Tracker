package com.krakert.tracker.database

import androidx.room.*
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.responses.Coin
import com.krakert.tracker.models.responses.ListCoins

@Dao
interface CryptoCacheDao {

    @Query("SELECT * FROM coinTable")
    suspend fun getListCoins(): List<Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Delete
    suspend fun deleteAll(it: List<Coin>)


//    @Query ("SELECT * FROM dataCoinTable")
//    suspend fun getListDataCoins(): List<DataCoinChart>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun setListDataCoins(coins: List<DataCoinChart>)
}