package com.krakert.tracker.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.Coins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class FirebaseRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var documentCoins = firestore.collection("Tracker").document("is3ksOVRTraukL5sSbgJ")

    suspend fun getListCoins(): Flow<List<Coin>> {
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
}