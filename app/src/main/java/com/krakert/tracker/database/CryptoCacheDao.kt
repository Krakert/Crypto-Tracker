package com.krakert.tracker.database

//@Dao
//interface CryptoCacheDao {
//
//    @Query ("SELECT * FROM coinTable")
//    suspend fun getListCoins(): List<Coin>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun setListCoins(coins: List<Coin>)
//
//    @Query ("SELECT * FROM dataCoinTable")
//    suspend fun getListDataCoins(): List<DataCoinChart>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun setListDataCoins(coins: List<DataCoinChart>)
//}