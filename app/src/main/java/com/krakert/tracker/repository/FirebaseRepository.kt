package com.krakert.tracker.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.krakert.tracker.model.Coins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class FirebaseRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var documentCoins = firestore.collection("Tracker").document("is3ksOVRTraukL5sSbgJ")

    suspend fun getListCoins(): Flow<Coins> {
        return try {
            withTimeout(10_000) {
                val result = documentCoins.get().await()
                flow {
                    result.toObject(Coins::class.java)?.let { emit(it) }
                }
            }
        } catch (e: Exception) {
            throw FireBaseExceptionError("Retrieval firebase was unsuccessful")
        }
    }

    inner class FireBaseExceptionError(message: String) : Exception(message)
}