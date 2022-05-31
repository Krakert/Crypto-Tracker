package com.krakert.tracker.repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.krakert.tracker.R
import com.krakert.tracker.model.FavoriteCoin
import com.krakert.tracker.model.FavoriteCoins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class FirebaseRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var documentCoins = firestore.collection("Tracker").document("is3ksOVRTraukL5sSbgJ")
    private var documentFavorite = firestore.collection("Tracker").document("LhLiwEToSjCp5CfuQ6BB")


    suspend fun getFavoriteCoins(): Flow<FavoriteCoins> {
        return try {
            withTimeout(10_000) {
                val result = documentCoins.get().await()
                flow {
                    result.toObject(FavoriteCoins::class.java)?.let { emit(it) }
                }
            }
        } catch (e: Exception) {
            throw FireBaseExceptionError("Retrieval firebase was unsuccessful")
        }
    }

    suspend fun addCoinToFavoriteCoins(coinId: FavoriteCoin, context: Context) {
        try {
            withTimeout(10_000) {
                documentFavorite.update("Favorite", FieldValue.arrayUnion(coinId)).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(context, context.getString(R.string.txt_coin_added, coinId.id), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            throw FireBaseExceptionError("Could not add the coin to favorites")
        }
    }

    inner class FireBaseExceptionError(message: String) : Exception(message)


}