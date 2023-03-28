package com.krakert.tracker.data.components.storage

import androidx.room.*
import com.krakert.tracker.data.tracker.entity.database.DBCoinCurrentDataEntity
import com.krakert.tracker.data.components.tracker.entity.ListPricesCoinsEntity
import com.krakert.tracker.data.tracker.entity.database.DBListCoinItemEntity

@Dao
interface TrackerDao {

    /*---------------- Used for the AddCoinViewModel ----------------*/
    @Query("SELECT * FROM coinsTable")
    suspend fun getListCoins(): List<DBListCoinItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCoins(coins: List<DBListCoinItemEntity>)

    @Delete
    suspend fun deleteListCoins(it: List<DBListCoinItemEntity>)
    /*---------------------------------------------------------------*/

    /*---------------- Used for the DetailsViewModel ----------------*/
    @Query("SELECT * FROM detailsCoinTable WHERE detailsCoinTable.id = :coinId")
    suspend fun getDetailsCoin(coinId: String): DBCoinCurrentDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailsCoin(coinCurrentDataEntity: DBCoinCurrentDataEntity)

    @Delete
    suspend fun deleteDetailsCoin(coinCurrentDataEntity: DBCoinCurrentDataEntity)
    /*---------------------------------------------------------------*/

    /*---------------- Used for the OverviewViewModel ---------------*/
    @Query("SELECT * FROM priceCoinsTable")
    suspend fun getPriceCoins(): ListPricesCoinsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceCoins(listPricesCoinsEntity: ListPricesCoinsEntity)

    @Query("DELETE FROM priceCoinsTable")
    suspend fun deletePriceCoins()

    /*---------------------------------------------------------------*/

    //    @Query("SELECT * FROM marketChartTable WHERE marketChartTable.id = :coinId")
    //    suspend fun getMarketChartCoin(coinId: String): DataGraph?
    //
    //
    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //    suspend fun insertMarketChartCoin(dataGraph: DataGraph)
    //
    //    @Delete
    //    suspend fun deleteMarketChartCoin(dataGraph: DataGraph)

    /*---------------------------------------------------------------*/
}