package com.krakert.tracker.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoin
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.api.CoinGeckoApi
import com.krakert.tracker.api.CoinGeckoApiService
import com.krakert.tracker.api.Util
import com.krakert.tracker.models.Coin
import com.krakert.tracker.models.CoinFullData
import com.krakert.tracker.repository.CryptoApiRepository
import com.krakert.tracker.state.ViewStateDetailsCoins
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class DetailsViewModel(context: Context, coin: Coin?) : ViewModel() {
    private val cryptoApiRepository: CryptoApiRepository = CryptoApiRepository()
    private val sharedPreference = SharedPreference.sharedPreference(context)


    private val _viewStateDetailsCoin = MutableStateFlow<ViewStateDetailsCoins>(ViewStateDetailsCoins.Loading)
    val detailsCoin = _viewStateDetailsCoin.asStateFlow()

    private val _httpResourceFullData: MutableLiveData<Util.Resource<CoinFullData>> = MutableLiveData(Util.Resource.Loading())

    val httpResourceFullData: LiveData<Util.Resource<CoinFullData>>
        get() = _httpResourceFullData

    fun getDetailsCoinByCoinId(coinId: String){
        viewModelScope.launch {
            _httpResourceFullData.value = cryptoApiRepository.getDetailsCoinByCoinId(coinId)
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