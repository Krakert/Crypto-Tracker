package com.krakert.tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.withTimeout

class CoinGeckoRepository {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var documentCoins = firestore.collection("Coins").document("HfdZkpPl5JbsLru2NzsF")

    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()

    private val _listCoins: MutableLiveData<List<String>> = MutableLiveData()

    val listCoins: LiveData<List<String>>
        get() = _listCoins


    suspend fun getListCoins(){
        println(coinGecko.ping().geckoSays)
        try {
            withTimeout(5_000){
                documentCoins.get().addOnCompleteListener {
                    if (it.isSuccessful){
                        val result = it.result
                        if (result.exists()){
                            println(result.data)
                        }
                    }
                }
            }
        } catch (e: Exception){
            println(e)
        }
    }
}