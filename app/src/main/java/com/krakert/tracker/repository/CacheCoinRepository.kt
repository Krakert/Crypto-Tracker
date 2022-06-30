package com.krakert.tracker.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.Coins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

//TODO: inject DAO and Prefs via Hilt
class CacheCoinRepository : CoinRespository {
    private val cacheRateLimbit = CacheRateLimiter<String>(10, TimeUnit.MINUTES)
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var documentCoins = firestore.collection("Tracker").document("is3ksOVRTraukL5sSbgJ")
    private val cacheKey = "cache_key_coins"

    override suspend fun getCoinsList(): Flow<List<Coin>> {
        // if this.shouldFetch

        // else - get from injected DAO
        return try {
            withTimeout(10_000) {
                val result = documentCoins.get().await()
                val time = System.currentTimeMillis()
                val list = result.toObject(Coins::class.java)?.Coins
                list?.forEach {
                    it.timeStamp = time
                }
                flow {
                    if (list != null) {
                        emit(list)
                    }
                }
            }
        } catch (e: Exception) {
            throw FireBaseExceptionError("Retrieval firebase was unsuccessful: $e")
        }
    }

    inner class FireBaseExceptionError(message: String) : Exception(message)

    override fun shouldFetch(): Boolean {
        //TODO: inject prefs in constructor
//        cacheRateLimit.shouldFetch(cacheKey, prefs)
        return true
    }
}