package com.krakert.tracker.api

import com.krakert.tracker.models.ListCoins
import com.krakert.tracker.models.responses.CoinFullData
import com.krakert.tracker.models.responses.MarketChart
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Middleware like class to handle http requests and it's exception
 * @author Pim Meijer
 */
class CoinGeckoDataSource @Inject constructor(private val retrofit: Retrofit) {
    private val coinGeckoApiService: CoinGeckoApiService = retrofit.create(CoinGeckoApiService::class.java);

    suspend fun fetchCoinsPriceById(idCoins: String, currency: String): Resource<MutableMap<String, MutableMap<String, Any>>> {
        return getResponse(
            //TODO: this should be passed to this function in the form of a RequestBody model
            request = { coinGeckoApiService.getPriceByListCoinIds(idCoins, currency, "", "", "", "") },
            defaultErrorMessage = "Error fetching Movie list")

    }
    //TODO: this should be passed to this function in the form of a RequestBody model
    suspend fun getListCoins(currency: String, ids: String?, order: String, perPage: Int, page: Int) : ListCoins {
        return coinGeckoApiService.getListCoins(currency, ids, order, perPage, page)
    }

    //TODO: this should be passed to this function in the form of a RequestBody model
    suspend fun getDetailsCoinByCoinId(
        id: String,
        localization: String,
        tickers: Boolean,
        markerData: Boolean,
        communityData: Boolean,
        developerData: Boolean,
        sparkline: Boolean
    ) : CoinFullData {
        return coinGeckoApiService.getDetailsCoinByCoinId(id, localization, tickers, markerData, communityData, developerData, sparkline)
    }

    //TODO: this should be passed to this function in the form of a RequestBody model
    suspend fun getHistoryByCoinId(id: String, currency: String, days: String): MarketChart {
        return coinGeckoApiService.getHistoryByCoinId(id, currency, days)
    }

    /**
     * You can expand this function with more specific error handling - like no internet
     */
    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return result.body()?.let {
                     Resource.Success(it)
                } ?: Resource.Error( "Empty body in retrofit response")
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Resource.Error(errorResponse?.status_message ?: defaultErrorMessage)
            }
        } catch (e: Throwable) {
            Resource.Error(e.toString())
        }
    }
}