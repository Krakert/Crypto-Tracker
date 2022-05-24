package com.krakert.tracker.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.krakert.tracker.model.Coins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class CoinGeckoRepository {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var documentCoins = firestore.collection("Tracker").document("is3ksOVRTraukL5sSbgJ")
    private var documentFavorite = firestore.collection("Tracker").document("LhLiwEToSjCp5CfuQ6BB")

//    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()

    suspend fun getListCoins(): Flow<Coins> {
        return try {
            withTimeout(10_000) {
                val result = documentCoins.get().await()
                flow {
                    result.toObject(Coins::class.java)?.let { emit(it) }
                }
            }
        } catch (e: Exception) {
            throw ListCoinsRetrievalError("Retrieval firebase was unsuccessful")
        }
    }

    suspend fun getFavoriteCoins(): Flow<Coins> {
        return try {
            withTimeout(10_000) {
                val result = documentFavorite.get().await()
                flow {
                    result.toObject(Coins::class.java)?.let { emit(it) }
                }
            }

        } catch (e: Exception) {
            throw GetFavoriteCoinsRetrievalError("Retrieval of favorite coins was unsuccessful")
        }
    }

    inner class ListCoinsRetrievalError(message: String) : Exception(message)
    inner class GetFavoriteCoinsRetrievalError(message: String) : Exception(message)

}