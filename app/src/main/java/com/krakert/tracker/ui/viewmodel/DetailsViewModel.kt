package com.krakert.tracker.ui.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoin
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.models.FavoriteCoin
import com.krakert.tracker.repository.CryptoApiRepository
import com.krakert.tracker.repository.CryptoApiRepository.CoinGeckoExceptionError
import com.krakert.tracker.ui.state.ViewStateDetailsCoins
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class DetailsViewModel(context: Context, coinId: String) : ViewModel() {
    private val cryptoApiRepository: CryptoApiRepository = CryptoApiRepository()
    private val sharedPreference = SharedPreference.sharedPreference(context)


    private val _viewStateDetailsCoin = MutableStateFlow<ViewStateDetailsCoins>(ViewStateDetailsCoins.Loading)
    val detailsCoin = _viewStateDetailsCoin.asStateFlow()

    init {
        getDetailsCoinByCoinId(coinId)
    }


    fun getDetailsCoinByCoinId(coinId: String){
        viewModelScope.launch {
            val response = cryptoApiRepository.getDetailsCoinByCoinId(coinId)
            try {
                if (response.data == null) {
                    _viewStateDetailsCoin.value = ViewStateDetailsCoins.Empty
                } else {
                    _viewStateDetailsCoin.value = ViewStateDetailsCoins.Success(response)
                }
            } catch (e: CoinGeckoExceptionError) {
                Log.e(ContentValues.TAG, e.message ?: "Something went wrong while retrieving data")
                _viewStateDetailsCoin.value = ViewStateDetailsCoins.Error(e.message.toString())
            }
        }
    }

    fun removeCoinFromFavoriteCoins(coinId: String) {
        try {
            val dataSharedPreferences = sharedPreference.FavoriteCoins.toString()
            val favoriteCoin = sharedPreference.FavoriteCoin
            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
            val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(dataSharedPreferences, typeOfT)

            var indexToRemove: Int? = null
                listFavoriteCoins.forEach {
                    if (it.id == coinId.lowercase())
                        indexToRemove = listFavoriteCoins.indexOf(it)
                }

            indexToRemove?.let { listFavoriteCoins.removeAt(it) }

            sharedPreference.FavoriteCoins = Gson().toJson(listFavoriteCoins)

            if (favoriteCoin == coinId){
                sharedPreference.FavoriteCoin = ""
            }

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message ?: "Something went wrong while deleting a coins")
        }
    }
}