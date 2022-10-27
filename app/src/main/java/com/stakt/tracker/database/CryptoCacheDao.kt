package com.stakt.tracker.database

import androidx.room.*
import com.stakt.tracker.models.database.DataGraph
import com.stakt.tracker.models.database.PriceCoins
import com.stakt.tracker.models.responses.Coin
import com.stakt.tracker.models.responses.CoinFullData

@Dao
interface CryptoCacheDao {

    /*---------------- Used for the AddCoinViewModel ----------------*/
    @Query("SELECT * FROM coinTable")
    suspend fun getListCoins(): List<Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCoins(coins: List<Coin>)

    @Delete
    suspend fun deleteListCoins(it: List<Coin>)
    /*---------------------------------------------------------------*/

    /*---------------- Used for the DetailsViewModel ----------------*/
    @Query("SELECT * FROM detailsCoinTable WHERE detailsCoinTable.id = :coinId")
    suspend fun getDetailsCoin(coinId: String): CoinFullData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailsCoin(coinFullData: CoinFullData)

    @Delete
    suspend fun deleteDetailsCoin(coinFullData: CoinFullData)
    /*---------------------------------------------------------------*/

    /*---------------- Used for the OverviewViewModel ---------------*/
    @Query("SELECT * FROM priceCoinsTable")
    suspend fun getPriceCoins(): PriceCoins?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceCoins(PriceCoins: PriceCoins)

    @Query("DELETE FROM priceCoinsTable")
    suspend fun deletePriceCoins()

    /*---------------------------------------------------------------*/

    @Query("SELECT * FROM marketChartTable WHERE marketChartTable.id = :coinId")
    suspend fun getMarketChartCoin(coinId: String): DataGraph?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketChartCoin(dataGraph: DataGraph)

    @Delete
    suspend fun deleteMarketChartCoin(dataGraph: DataGraph)

    /*---------------------------------------------------------------*/
}