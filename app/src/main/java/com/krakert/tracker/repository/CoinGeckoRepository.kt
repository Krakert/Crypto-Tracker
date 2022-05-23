package com.krakert.tracker.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.krakert.tracker.model.Coins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class CoinGeckoRepository {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var documentCoins = firestore.collection("Coins").document("HfdZkpPl5JbsLru2NzsF")

//    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()

    suspend fun getListCoins(): Flow<Coins> {
        return try {
            withTimeout(10_000) {
                val result = documentCoins.get().await()
                flow {
                    emit(result.toObject(Coins::class.java)!!)
                }
            }
        } catch (e: Exception) {
            throw ListCoinsRetrievalError("Retrieval firebase was unsuccessful")
        }
    }

    inner class ListCoinsRetrievalError(message: String) : Exception(message)

}