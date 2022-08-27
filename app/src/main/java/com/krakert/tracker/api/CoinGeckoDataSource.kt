package com.krakert.tracker.api

import com.krakert.tracker.models.responses.ListCoins
import com.krakert.tracker.models.responses.CoinFullData
import com.krakert.tracker.models.responses.MarketChart
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Middleware like class to handle http requests and it's exception
 * @author Pim Meijer, Stefan de Kraker
 */
class CoinGeckoDataSource @Inject constructor(private val retrofit: Retrofit) {
    private val coinGeckoApiService: CoinGeckoApiService =
        retrofit.create(CoinGeckoApiService::class.java);

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
            },
            defaultErrorMessage = "Error fetching data pricing coins"
        )

    }

    //TODO: this should be passed to this function in the form of a RequestBody model
    suspend fun getListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int
    ): Resource<ListCoins> {
        return getResponse(
            request = {
                coinGeckoApiService.getListCoins(currency, ids, order, perPage, page)
            } , defaultErrorMessage = "Error fetching coin market data"
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
            },
            defaultErrorMessage = "Error fetching details coins"
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
            },
            defaultErrorMessage = "Error fetching historical data coin"
        )
    }

    /**
     * You can expand this function with more specific error handling - like no internet
     */
    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return result.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Empty body in retrofit response")
            } else {
                val errorMsg = when (result.code()) {
                    429 -> "Try again in 1 minute"
                    404 -> "Not found"
                    else -> {
                        ErrorUtils.parseError(result, retrofit)?.status_message
                    }
                }
                Resource.Error(errorMsg ?: defaultErrorMessage)
            }
        } catch (e: Throwable) {
            Resource.Error(e.toString())
        }
    }
}