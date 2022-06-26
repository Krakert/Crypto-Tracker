package com.krakert.tracker.repository

import android.content.Context
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.model.Currency
import com.krakert.tracker.model.DataDetailsCoin
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.*


class CoinGeckoRepository(context: Context) {

    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()

    private val sharedPreference = SharedPreference.sharedPreference(context = context)
    private val daysOfTracking = sharedPreference.AmountDaysTracking.toDouble()
    private val currencyString = sharedPreference.Currency?.let { Currency.valueOf(it) }.toString().lowercase(Locale.getDefault())

    suspend fun getHistoryByCoinId(coinId: String): List<List<String>> {
        return try {
            withTimeout(10_000) {
                coinGecko.getCoinMarketChartById(coinId, currencyString,  daysOfTracking).prices
            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of history data of the coin was unsuccessful")
        }
    }

    suspend fun getLatestPriceByCoinId(coinId: String): Double {
        return try {
            withTimeout(10_000) {
                coinGecko.getPrice(coinId, currencyString)[coinId]!!.getPrice(currencyString)
            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of latest price of the coin was unsuccessful")

        }
    }

    suspend fun getDetailsCoinByCoinId(coinId: String): Flow<DataDetailsCoin> {
        return try {
            withTimeout(10_000) {
                val data = coinGecko.getCoinById(id= coinId, marketData = true)
                flow{
                    emit(
                        DataDetailsCoin(
                        name = data.name,
                        priceCurrent = data.marketData?.currentPrice?.get(currencyString) ?: 0.0,
                        image = data.image,
                        priceChangePercentage24h = data.marketData?.priceChangePercentage24h ?: 0.0,
                        priceChangePercentage7d = data.marketData?.priceChangePercentage7d ?: 0.0,
                        circulatingSupply = data.marketData?.circulatingSupply ?: 0.0,
                        high24h = data.marketData?.high24h?.get(currencyString) ?: 0.0,
                        low24h = data.marketData?.low24h?.get(currencyString) ?: 0.0,
                        marketCap = data.marketData?.marketCap?.get(currencyString) ?: 0.0,
                        marketCapChangePercentage24h = data.marketData?.marketCapChangePercentage24h ?: 0.0
                    ))
                }

            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of details of the coin was unsuccessful")
        }
    }

    class CoinGeckoExceptionError(message: String) : Exception(message)
}