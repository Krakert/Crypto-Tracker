package com.krakert.tracker.repository

import android.content.Context
import com.krakert.tracker.database.CryptoCacheDao
import com.krakert.tracker.database.CryptoCacheRoomDatabase
import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.DataCoin

class CryptoCacheRepository(context: Context)  {
    private val cryptoCacheDao: CryptoCacheDao

    init {
        val database = CryptoCacheRoomDatabase.getDatabase(context)
        cryptoCacheDao = database!!.cryptoCacheDao()
    }

    suspend fun getListCoins(): List<Coin> = cryptoCacheDao.getListCoins()

    suspend fun setListCoins(coins: List<Coin>) = cryptoCacheDao.setListCoins(coins)

    suspend fun getListDataCoins(): List<DataCoin> = cryptoCacheDao.getListDataCoins()

    suspend fun setListDataCoins(dataCoins: List<DataCoin>) = cryptoCacheDao.setListDataCoins(dataCoins)
}