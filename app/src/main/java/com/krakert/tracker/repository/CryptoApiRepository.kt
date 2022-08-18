package com.krakert.tracker.repository

import android.util.Log
import com.krakert.tracker.api.CoinGeckoApi
import com.krakert.tracker.api.CoinGeckoApiService
import com.krakert.tracker.api.Util.Resource
import com.krakert.tracker.models.*
import kotlinx.coroutines.withTimeout

class CryptoApiRepository {
    private val coinGeckoApiService: CoinGeckoApiService = CoinGeckoApi.createApi()

    suspend fun getListCoins() : Resource<ListCoins> {
        val response = try {
            withTimeout(10_000){
                coinGeckoApiService.getListCoins()
            }
        } catch(e: Exception) {
            Log.e("CryptoApiRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occured")
        }

        return Resource.Success(response)
    }

    suspend fun getPriceCoins(listCoins: FavoriteCoins) : Resource<Map<String, CoinPrice>> {
        val response = try {
            withTimeout(10_000){
                val idCoins = arrayListOf<String>()
                listCoins.forEach {
                    idCoins.add(it.id)
                }
                coinGeckoApiService.getPriceByListCoinIds(ids = idCoins.joinToString(","))
            }
        } catch(e: Exception) {
            Log.e("CryptoApiRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occured")
        }
        println("______")
        println(response)
        return Resource.Success(response)
    }

    suspend fun getDetailsCoinByCoinId(coinId: String): Resource<CoinFullData> {
        val response = try {
            withTimeout(10_000) {
                coinGeckoApiService.getDetailsCoinByCoinId(coinId)
            }
        } catch (e: Exception) {
            Log.e("CryptoApiRepository", e.message ?: "No exception message available")
            throw CoinGeckoExceptionError("Retrieval of details of the coin was unsuccessful")
        }
        println("________________________")
        println(response.marketData?.currentPrice?.get("usd"))
        println("________________________")
        return Resource.Success(response)
    }

//    suspend fun getDetailsCoinByCoinId(coinId: String): Resource<DataDetailsCoin> {
//        val response = try {
//            withTimeout(10_000) {
//                coinGeckoApiService.getDetailsCoinByCoinId()
//                        DataDetailsCoin(
//                            name = data.name,
//                            priceCurrent = data.marketData?.currentPrice?.get(currencyString) ?: 0.0,
//                            image = data.image,
//                            priceChangePercentage24h = data.marketData?.priceChangePercentage24h ?: 0.0,
//                            priceChangePercentage7d = data.marketData?.priceChangePercentage7d ?: 0.0,
//                            circulatingSupply = data.marketData?.circulatingSupply ?: 0.0,
//                            high24h = data.marketData?.high24h?.get(currencyString) ?: 0.0,
//                            low24h = data.marketData?.low24h?.get(currencyString) ?: 0.0,
//                            marketCap = data.marketData?.marketCap?.get(currencyString) ?: 0.0,
//                            marketCapChangePercentage24h = data.marketData?.marketCapChangePercentage24h ?: 0.0
//                        )
//            }
//        } catch (e: Exception) {
//            throw CoinGeckoExceptionError("Retrieval of details of the coin was unsuccessful")
//        }
//        return Resource.Success(response)
//    }

    class CoinGeckoExceptionError(message: String) : Exception(message)
}