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
import com.krakert.tracker.models.Coin
import com.krakert.tracker.repository.CryptoApiRepository
import com.krakert.tracker.repository.CryptoApiRepository.*
import com.krakert.tracker.ui.state.ViewStateDetailsCoins
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class DetailsViewModel(context: Context, coin: String) : ViewModel() {
    private val cryptoApiRepository: CryptoApiRepository = CryptoApiRepository()
    private val sharedPreference = SharedPreference.sharedPreference(context)


    private val _viewStateDetailsCoin = MutableStateFlow<ViewStateDetailsCoins>(ViewStateDetailsCoins.Loading)
    val detailsCoin = _viewStateDetailsCoin.asStateFlow()


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
                _viewStateDetailsCoin.value = ViewStateDetailsCoins.Error(e)
            }
        }
    }

    fun removeCoinFromFavoriteCoins(coin: Coin) {
        try {
            val dataSharedPreferences = sharedPreference.FavoriteCoins.toString()
            val favoriteCoin = sharedPreference.FavoriteCoin
            val typeOfT: Type = object : TypeToken<ArrayList<Coin>>() {}.type
            val listFavoriteCoins: ArrayList<Coin> = Gson().fromJson(dataSharedPreferences, typeOfT)
            listFavoriteCoins.remove(coin)

            sharedPreference.FavoriteCoins = Gson().toJson(listFavoriteCoins)

//            if (favoriteCoin == coin.idCoin){
//                sharedPreference.FavoriteCoin = ""
//            }

        } catch (e: Exception) {
            val errorMsg = "Something went wrong while deleting a coins"

            Log.e(ContentValues.TAG, e.message ?: errorMsg)
        }
    }
}