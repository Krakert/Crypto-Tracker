package com.krakert.tracker.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.model.Coin
import com.krakert.tracker.repository.CryptoCacheRepository
import com.krakert.tracker.repository.FirebaseRepository
import com.krakert.tracker.state.ViewStateAddCoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

class AddCoinViewModel(context: Context) : ViewModel() {
    private val fireBaseRepo: FirebaseRepository = FirebaseRepository()
    private val cryptoCacheRepository: CryptoCacheRepository = CryptoCacheRepository(context)
    private val sharedPreference = SharedPreference.sharedPreference(context)

    // backing property to avoid state updates from other classes
    private val _viewState = MutableStateFlow<ViewStateAddCoin>(ViewStateAddCoin.Loading)

    // the UI collects from this StateFlow to get it's state update
    val listCoins = _viewState.asStateFlow()

    fun getListCoins() = viewModelScope.launch(Dispatchers.IO) {
        val resultCache = cryptoCacheRepository.getListCoins()
        println(resultCache)
        if (resultCache.isEmpty()) {
            println("Cache empty, AddCoinViewModel")
            getAndSetData()
        } else {
            println("found data in the cache, AddCoinViewModel")
            val oldDate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(resultCache[0].timeStamp),
                TimeZone.getDefault().toZoneId())
            val dateNow = LocalDateTime.now()
            if (ChronoUnit.HOURS.between(oldDate, dateNow) >= 24){
                println("Data is overdue, and needs updating, AddCoinViewModel")
                getAndSetData()
            } else {
                println("Using cached data")
                _viewState.value = ViewStateAddCoin.Success(resultCache)
            }
        }
    }

    private suspend fun getAndSetData() {
        fireBaseRepo.getListCoins().collect {
            try {
                CoroutineScope(Dispatchers.IO).launch{
                    cryptoCacheRepository.setListCoins(it)
                }
                _viewState.value = ViewStateAddCoin.Success(it)
            } catch (e: FirebaseRepository.FireBaseExceptionError) {
                val errorMsg = "Something went wrong while retrieving the list of coins"
                Log.e(TAG, e.message ?: errorMsg)
                _viewState.value = ViewStateAddCoin.Error(e)
            }
        }
    }

    fun addCoinToFavoriteCoins(coin: Coin, context: Context) {
        try {
            var listFavoriteCoins = ArrayList<Coin>()
            var alreadyAdded = false
            val dataSharedPreferences = sharedPreference.FavoriteCoins.toString()
            val typeOfT: Type = object : TypeToken<ArrayList<Coin>>() {}.type

            if (dataSharedPreferences.isNotEmpty()) {
                listFavoriteCoins = Gson().fromJson(dataSharedPreferences, typeOfT)
            }
            listFavoriteCoins.forEach {
                if (it.name == coin.name) {
                    alreadyAdded = true
                    Toast.makeText(context, context.getString(R.string.txt_toast_already_added), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            if (!alreadyAdded) {
                listFavoriteCoins.add(
                    Coin(
                        name = coin.name,
                        id = coin.id,
                        idCoin = coin.idCoin,
                        symbol = coin.symbol,
                        timeStamp = 0
                    )
                )
                sharedPreference.FavoriteCoins = Gson().toJson(listFavoriteCoins)
                Toast.makeText(context, context.getString(R.string.txt_toast_added, coin.name), Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: FirebaseRepository.FireBaseExceptionError) {
            val errorMsg = "Something went wrong while saving the list of favorite coins"

            Log.e(TAG, e.message ?: errorMsg)
            _viewState.value = ViewStateAddCoin.Error(e)
        }
    }
}