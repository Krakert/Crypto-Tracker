package com.krakert.tracker.api

import com.krakert.tracker.models.responses.CoinFullData
import com.krakert.tracker.models.responses.ListCoins
import com.krakert.tracker.models.responses.MarketChart
import com.krakert.tracker.models.ui.ProblemState
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

/**
 * Middleware like class to handle http requests and it's exception
 * @author Pim Meijer, Stefan de Kraker
 */
class CoinGeckoDataSource @Inject constructor(retrofit: Retrofit) {
    private val coinGeckoApiService: CoinGeckoApiService =
        retrofit.create(CoinGeckoApiService::class.java)

    suspend fun fetchCoinsPriceById(
        idCoins: String,
        currency: String
    ): Resource<Map<String, MutableMap<String, Any>>> {
        return getResponse(
            //TODO: this should be passed to this function in the form of a RequestBody model
            request = {
                coinGeckoApiService.getPriceByListCoinIds(
                    ids = idCoins,
                    currency = currency,
                    marketCap = "",
                    dayVol = "",
                    dayChange = "",
                    lastUpdated = ""
                )
            }
        )

    }

    //TODO: this should be passed to this function in the form of a RequestBody model
    suspend fun fetchListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int
    ): Resource<ListCoins> {
        return getResponse(
            request = {
                coinGeckoApiService.getListCoins(currency, ids, order, perPage, page)
            }
        )
    }

    //TODO: this should be passed to this function in the form of a RequestBody model
    suspend fun fetchDetailsCoinByCoinId(coinId: String): Resource<CoinFullData> {
        return getResponse(
            request = {
                coinGeckoApiService.getDetailsCoinByCoinId(
                    coinId = coinId,
                    localization = "false",
                    tickers = false,
                    markerData = true,
                    communityData = false,
                    developerData = false,
                    sparkline = false
                )
            }
        )

    }

    suspend fun fetchHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String
    ): Resource<MarketChart> {
        return getResponse(
            request = {
                coinGeckoApiService.getHistoryByCoinId(
                    coinId = coinId,
                    currency = currency,
                    days = days
                )
            }
        )
    }

    /**
     * You can expand this function with more specific error handling - like no internet
     */
    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return result.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(ProblemState.UNKNOWN)
            } else {
                val errorMsg = when (result.code()) {
                    429 -> ProblemState.API_LIMIT
                    404 -> ProblemState.COULD_NOT_LOAD
                    else -> {
                       ProblemState.UNKNOWN
                    }
                }
                Resource.Error(errorMsg)
            }
        } catch (e: Throwable) {
            when(e) {
                is SSLHandshakeException -> Resource.Error(ProblemState.SSL)
                else -> Resource.Error(ProblemState.UNKNOWN)
            }
        }
    }
}