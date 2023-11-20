package com.krakert.tracker.data.tracker

import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.AmountDaysTracking
import com.krakert.tracker.data.SharedPreference.Currency
import com.krakert.tracker.data.SharedPreference.TileCoin
import com.krakert.tracker.data.SharedPreference.getListFavoriteCoins
import com.krakert.tracker.data.components.net.KtorRequest
import com.krakert.tracker.data.components.net.mapper.ResponseMapper
import com.krakert.tracker.data.extension.guard
import com.krakert.tracker.data.tracker.api.ApiCalls
import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.data.tracker.mapper.DetailCoinMapper
import com.krakert.tracker.data.tracker.mapper.ListCoinsMapper
import com.krakert.tracker.data.tracker.mapper.OverviewMapper
import com.krakert.tracker.domain.tracker.ApiRepository
import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val ktor: KtorRequest,
    private val sharedPreferences: SharedPreferences,
    private val responseMapper: ResponseMapper,
    private val listCoinsMapper: ListCoinsMapper,
    private val detailCoinMapper: DetailCoinMapper,
    private val overviewMapper: OverviewMapper,
) : ApiRepository {

    override suspend fun fetchListCoins(): Result<ListCoins> {
        val response = Result.runCatching {
            ktor.request(
                ApiCalls.getListCoins(
                    currency = sharedPreferences.Currency.lowercase()
                )
            )
        }.guard { return it }
        val entity = responseMapper.map(response)
        return entity.mapCatching {
            listCoinsMapper.mapApiToDomain(
                it,
                sharedPreferences.getListFavoriteCoins()
            )
        }
    }


    override suspend fun fetchDetailsCoin(coinId: String): Result<CoinDetails> {
        val response = Result.runCatching {
            ktor.request(ApiCalls.getDetailsCoinByCoinId(coinId = coinId))
        }.guard { return it }
        val entity = responseMapper.map(response)
        return entity.mapCatching { detailCoinMapper.mapApiToDomain(it) }
    }

    override suspend fun fetchOverview(): Result<CoinOverview> {
        val favoriteCoins = sharedPreferences.getListFavoriteCoins()
        val responsePriceCoins = Result.runCatching {
            ktor.request(
                ApiCalls.getPriceByListCoinIds(
                    ids = favoriteCoins.mapNotNull { it.id }.joinToString(","),
                    currency = sharedPreferences.Currency.lowercase()
                )
            )
        }.guard { return it }
        val entityPriceCoins = responseMapper.map(responsePriceCoins)
        val listEntityMarketChart = arrayListOf<MarketChartEntity>()
        favoriteCoins.forEach { coin ->
            if (!coin.id.isNullOrBlank()) {
                val responseMarketChart = Result.runCatching {
                    ktor.request(
                        ApiCalls.getHistoryByCoinId(
                            coinId = coin.id,
                            currency = sharedPreferences.Currency.lowercase(),
                            days = sharedPreferences.AmountDaysTracking.toString()
                        )
                    )
                }.guard { return it }
                listEntityMarketChart.add(
                    responseMapper.map(responseMarketChart).guard { return it })
            }
        }
        return Result.runCatching {
            overviewMapper.map(
                favoriteCoins = favoriteCoins,
                tileCoin = sharedPreferences.TileCoin,
                pricesCoinsEntity = entityPriceCoins.guard { return it },
                listMarketChartEntity = listEntityMarketChart,
                currency = sharedPreferences.Currency.lowercase(),
            )
        }
    }


//    override fun isOnline(): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        return capabilities !== null
//    }
}